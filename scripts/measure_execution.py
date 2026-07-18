#!/usr/bin/env python3
"""
measure_execution.py
====================
Measure *test execution* and *bug-detection* power of the LLM-generated JUnit
tests against real Defects4J v2.0.0 checkouts.

This is the second stage of the study, after measure_compilability.py. Whereas
compilability only asked "does javac accept the file", this stage runs the
tests and asks the two questions that actually matter for a test-generation
tool:

  1. EXECUTION (validity): does the test *pass* on the FIXED program?
     A test that compiles but crashes or asserts wrongly on correct code is a
     false alarm -- it over-fitted or invented behaviour. We call a test
     `pass_f` (valid) only when it compiles on `f`, runs, finds >=1 test, and
     zero methods fail.

  2. BUG DETECTION (fault revelation): does that same valid test *fail* on the
     BUGGY program? A test detects the injected Defects4J bug iff

         pass_f  AND  compiles_b  AND  (>=1 method fails on b)

     This is the standard Defects4J fault-revealing definition: pass on fixed,
     fail on buggy. A test that also fails on fixed proves nothing (it fails
     everywhere), and a test that can't be built on the buggy tree can't be
     judged, so both are excluded from detection -- conservatively counted as
     "not detected".

METHOD, per (model, bug, condition) file
----------------------------------------
  * Check out BOTH the fixed (`f`) and buggy (`b`) trees and `defects4j
    compile` each once (cached across the 8 files that share a bug).
  * Build the exact same scaffold / full-compilation-unit that
    measure_compilability produced (imported from that module -- one source of
    truth for file-shape handling, lenient imports, Mockito, scaffolding), so
    execution is measured on identical code.
  * javac the test against the fixed classpath -> run with the JUnit Platform
    Console; then repeat against the buggy classpath.
  * Runs are `-Djava.awt.headless=true` (JFreeChart touches AWT) and have a
    per-file wall-clock timeout so a buggy infinite loop can't wedge the sweep.

OUTPUT (results/execution/)
  * execution_results.csv   -- one row per file, every intermediate flag
  * execution_summary.md    -- per-model validity + detection, C1 vs C2,
                               and a per-bug detection tally

Reuses the SAME jars and defects4j install as run_compile.sh; see
scripts/run_execution.sh for the one-command wrapper.
"""

import argparse
import csv
import json
import os
import re
import shutil
import subprocess
import sys
from collections import defaultdict
from pathlib import Path

# One source of truth for file-shape handling and scaffolding: reuse the exact
# builders the compilability stage used, so we run identical code.
sys.path.insert(0, str(Path(__file__).resolve().parent))
import measure_compilability as mc  # noqa: E402


# --- JUnit Platform Console summary parsing --------------------------------
# With --details=summary the console launcher prints a tail block of the form
#   [   3 tests found      ]
#   [   3 tests successful ]
#   [   0 tests failed     ]
# (note: --details=none suppresses this block too, so we must NOT use none). We
# read the counts from there rather than trust the process exit code alone, so
# we can tell "0 found" from "all passed".
_COUNT_RE = {
    "found":      re.compile(r"(\d+)\s+tests?\s+found", re.I),
    "successful": re.compile(r"(\d+)\s+tests?\s+successful", re.I),
    "failed":     re.compile(r"(\d+)\s+tests?\s+failed", re.I),
    "skipped":    re.compile(r"(\d+)\s+tests?\s+skipped", re.I),
    "aborted":    re.compile(r"(\d+)\s+tests?\s+aborted", re.I),
    "cfailed":    re.compile(r"(\d+)\s+containers?\s+failed", re.I),
}


def _count(rx, text):
    m = rx.search(text)
    return int(m.group(1)) if m else 0


def run_junit(java, run_cp, fqcn, junit_jar, timeout):
    """Run one test class; return dict(found, successful, failed, cfailed,
    timeout, launch_error, raw)."""
    # Restrict discovery to the Jupiter engine. Every generated test is JUnit 5,
    # but Defects4J's cp.test ships junit 4.11, and the standalone jar's Vintage
    # engine scans the classpath at discovery, finds 4.11 (< 4.12), and aborts
    # the WHOLE launcher run -- taking the Jupiter tests down with it. Excluding
    # Vintage sidesteps the version check entirely.
    cmd = [java, "-Djava.awt.headless=true",
           "-cp", run_cp,
           "org.junit.platform.console.ConsoleLauncher", "execute",
           "--include-engine=junit-jupiter",
           "--disable-banner", "--details=summary",
           "--select-class", fqcn]
    try:
        p = subprocess.run(cmd, capture_output=True, text=True, timeout=timeout)
    except subprocess.TimeoutExpired as e:
        raw = (e.stdout or "") + (e.stderr or "")
        return dict(found=0, successful=0, failed=0, cfailed=0,
                    timeout=True, launch_error=False, raw=raw or "TIMEOUT")
    raw = p.stdout + p.stderr
    res = {k: _count(rx, raw) for k, rx in _COUNT_RE.items()}
    # A container failure (e.g. a static-initializer or @BeforeAll throwing)
    # means the tests never ran; treat it as a failure signal, not silence.
    launch_error = res["found"] == 0 and res["cfailed"] == 0 and p.returncode != 0
    return dict(found=res["found"], successful=res["successful"],
                failed=res["failed"], cfailed=res["cfailed"],
                timeout=False, launch_error=launch_error, raw=raw)


def build_test_source(t, info, strict):
    """Return (package, class_name, source_text) -- identical to the
    compilability stage's handling of the two file shapes."""
    source = t["path"].read_text(encoding="utf-8", errors="ignore")
    is_full, declared = mc.detect_shape(source)
    if is_full:
        package, scaffold = mc.build_full_unit(source, info["package"],
                                               info["project_pkgs"], strict)
        class_name = declared
        shape = "full_class"
    else:
        package = info["package"] or "d4jtest"
        class_name = (f"{t['model'].replace('-', '_').replace('.', '_')}_"
                      f"{t['project']}_{t['num']}_{t['condition']}_Test")
        scaffold = mc.build_scaffold(source, package, class_name,
                                     info["project_pkgs"], strict)
        shape = "fragment"
    return package, class_name, scaffold, shape


def compile_and_run(t, info, version_tag, work, javac, java, junit_jar,
                    strict, timeout):
    """javac the test against `info`'s classpath, then run it. Returns
    dict(compiles, run) where run is None if it did not compile."""
    package, class_name, source, shape = build_test_source(t, info, strict)
    fqcn = f"{package}.{class_name}" if package else class_name

    tmp = (work / "_exec" / version_tag / t["model"]
           / f"{t['bug']}_{t['condition']}")
    if tmp.exists():
        shutil.rmtree(tmp, ignore_errors=True)
    pkg_dir = tmp / package.replace(".", os.sep)
    pkg_dir.mkdir(parents=True, exist_ok=True)
    (pkg_dir / f"{class_name}.java").write_text(source, encoding="utf-8")
    out_dir = tmp / "_out"
    out_dir.mkdir(parents=True, exist_ok=True)

    jc = subprocess.run(
        [javac, "-encoding", "UTF-8", "-cp", info["classpath"],
         "-d", str(out_dir), str(pkg_dir / f"{class_name}.java")],
        capture_output=True, text=True)
    if jc.returncode != 0:
        return dict(shape=shape, compiles=False,
                    compile_err=mc.classify_error(jc.stderr),
                    compile_stderr=jc.stderr, run=None)

    run_cp = os.pathsep.join([str(out_dir), info["classpath"]])
    run = run_junit(java, run_cp, fqcn, junit_jar, timeout)
    return dict(shape=shape, compiles=True, compile_err="",
                compile_stderr="", run=run)


def checkout(d4j_bin, project, num, version, wdir, reuse):
    # Each `defects4j export` spawns ant (seconds); across 30 bugs x 2 versions
    # that dominates a resumed pass. Cache the two classpaths in the work tree
    # the first time and read them back instantly on later passes.
    cache_c, cache_t = wdir / ".cp_compile.cache", wdir / ".cp_test.cache"
    if reuse and wdir.is_dir():
        print(f"    reuse {wdir.name}")
        if cache_c.is_file() and cache_t.is_file():
            return cache_c.read_text().strip(), cache_t.read_text().strip()
    else:
        if wdir.exists():
            shutil.rmtree(wdir, ignore_errors=True)
        co = mc.d4j(d4j_bin, "checkout", "-p", project, "-v",
                    f"{num}{version}", "-w", str(wdir))
        if co.returncode != 0:
            print(f"    checkout FAILED {project}-{num}{version}: "
                  f"{co.stderr[:200]}")
            return None
        mc.d4j(d4j_bin, "compile", "-w", str(wdir))
    cp = mc.d4j(d4j_bin, "export", "-p", "cp.compile", "-w", str(wdir))
    cp_test = mc.d4j(d4j_bin, "export", "-p", "cp.test", "-w", str(wdir))
    cpc, cpt = cp.stdout.strip(), cp_test.stdout.strip()
    try:
        cache_c.write_text(cpc)
        cache_t.write_text(cpt)
    except OSError:
        pass
    return cpc, cpt


def build_info(d4j_bin, project, num, version, wdir, junit5, extra_cp,
               focal_class, reuse):
    got = checkout(d4j_bin, project, num, version, wdir, reuse)
    if got is None:
        return None
    cp_compile, cp_test = got
    classpath = os.pathsep.join(
        x for x in [cp_compile, cp_test, str(junit5), extra_cp] if x)
    pkg = None
    if focal_class:
        pkg, _ = mc.find_package_for_class(wdir, focal_class)
    # Project packages MUST be derived from THIS checkout's own classpath: the
    # compiled-test packages differ from bug to bug and between the fixed and
    # buggy trees, so a shared/project-wide list injects `import <pkg>.*` for
    # packages absent from this classpath and fails the file on the harness's
    # own import. Scanning is an rglob over thousands of .class files on a slow
    # drvfs mount, so cache the result per checkout (one file per wdir) -- that
    # keeps it correct AND lets a resumed pass read it back instantly.
    pkg_cache_file = wdir / ".pkgs.cache"
    if pkg_cache_file.is_file():
        project_pkgs = pkg_cache_file.read_text().split()
    else:
        project_pkgs = mc.collect_project_packages(classpath)
        try:
            pkg_cache_file.write_text("\n".join(project_pkgs))
        except OSError:
            pass
    return dict(workdir=wdir, package=pkg, classpath=classpath,
                project_pkgs=project_pkgs)


def main():
    ap = argparse.ArgumentParser(
        description=__doc__,
        formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--repo", required=True, type=Path)
    ap.add_argument("--d4j", default="defects4j")
    ap.add_argument("--javac", default="javac")
    ap.add_argument("--java", default="java")
    ap.add_argument("--junit5", type=Path, required=True)
    ap.add_argument("--extra-cp", default="")
    ap.add_argument("--work", type=Path, default=Path("./_d4j_work"))
    ap.add_argument("--out", type=Path, default=Path("./results/execution"))
    ap.add_argument("--models", nargs="*", default=mc.MODELS_DEFAULT)
    ap.add_argument("--bugs", nargs="*", default=None,
                    help="limit to these bug ids (e.g. Lang-8 Math-2)")
    ap.add_argument("--strict", action="store_true",
                    help="match measure_compilability --strict import policy")
    ap.add_argument("--reuse-checkouts", action="store_true")
    ap.add_argument("--fresh", action="store_true",
                    help="ignore any existing execution_results.csv and rerun "
                         "every file (default: resume, skipping files already "
                         "recorded so an interrupted sweep can continue)")
    ap.add_argument("--timeout", type=int, default=90,
                    help="per-file run wall-clock seconds (default 90)")
    args = ap.parse_args()

    gen_root = args.repo / "data" / "generated"
    if not gen_root.is_dir():
        sys.exit(f"ERROR: {gen_root} not found")

    args.work.mkdir(parents=True, exist_ok=True)
    args.out.mkdir(parents=True, exist_ok=True)
    # On a fresh run, wipe stale logs; on a resume, keep them so an interrupted
    # sweep can continue where it left off.
    if args.fresh:
        shutil.rmtree(args.out / "run_logs", ignore_errors=True)
    (args.out / "run_logs").mkdir(parents=True, exist_ok=True)

    file_re = re.compile(r"^([A-Za-z]+)-(\d+)_(C[12])\.java$")
    tasks, bugs = [], set()
    for model in args.models:
        mdir = gen_root / model
        if not mdir.is_dir():
            print(f"  (skip: no folder for model '{model}')")
            continue
        for f in sorted(mdir.glob("*.java")):
            m = file_re.match(f.name)
            if not m:
                continue
            proj, num, cond = m.group(1), m.group(2), m.group(3)
            if args.bugs and f"{proj}-{num}" not in args.bugs:
                continue
            bugs.add((proj, num))
            tasks.append(dict(model=model, project=proj, num=num,
                              bug=f"{proj}-{num}", condition=cond, path=f))

    print(f"Found {len(tasks)} test files across {len(bugs)} bugs "
          f"and {len(args.models)} models.")

    methods = {}
    mj = args.repo / "data" / "raw" / "methods" / "methods.json"
    if mj.is_file():
        for e in json.loads(mj.read_text()):
            methods[e["id"]] = e
    else:
        print("  WARNING: methods.json not found; will infer class from source.")

    prior = {} if args.fresh else _load_prior(args.out)
    if prior:
        print(f"  resuming: {len(prior)} rows already in "
              f"{args.out.name}/execution_results.csv will be reused")

    skipped = sum(
        1 for (p, n) in bugs
        if all((t["model"], t["bug"], t["condition"]) in prior
               for t in tasks if (t["project"], t["num"]) == (p, n)))
    if skipped:
        print(f"  skipping checkout of {skipped} fully-recorded bug(s)")

    # Process bug-by-bug (not model-by-model): check out a bug only when it has
    # pending files, run those 8 files, checkpoint, then move on. So a completed
    # bug is skipped wholesale on the next resumed pass (no re-checkout, no
    # re-export), and task progress starts immediately instead of after all 30
    # checkouts. Combined with the classpath + package caches, resumed passes
    # spend their time running tests, not rebuilding classpaths.
    by_bug = defaultdict(list)
    for t in tasks:
        by_bug[(t["project"], t["num"])].append(t)

    results = []
    for (proj, num) in sorted(by_bug, key=lambda t: (t[0], int(t[1]))):
        bug_tasks = by_bug[(proj, num)]
        need = [t for t in bug_tasks
                if (t["model"], t["bug"], t["condition"]) not in prior]
        fi = bi = None
        if need:
            bug = f"{proj}-{num}"
            project = mc.PROJECT_OF.get(proj, proj)
            focal_class = methods.get(bug, {}).get("class")
            print(f"\n=== {bug}: checkout fixed + buggy "
                  f"({len(need)} pending) ===")
            fi = build_info(
                args.d4j, project, num, "f", args.work / f"{project}_{num}f",
                args.junit5, args.extra_cp, focal_class, args.reuse_checkouts)
            bi = build_info(
                args.d4j, project, num, "b", args.work / f"{project}_{num}b",
                args.junit5, args.extra_cp, focal_class, args.reuse_checkouts)
            print(f"    fixed pkg={fi['package'] if fi else 'CHECKOUT-FAILED'}")
        for t in bug_tasks:
            key = (t["model"], t["bug"], t["condition"])
            if key in prior:
                results.append(prior[key])
                continue
            row = process_task(t, fi, bi, args)
            results.append(row)
            _print_row(row)
            # Checkpoint after every real run so a kill/sleep loses <=1 file.
            _dump_csv(args.out, results)

    _write_outputs(args, results)


def process_task(t, fi, bi, args):
    """Compile+run one file on fixed and buggy; return its result row."""
    row = dict(model=t["model"], bug=t["bug"], condition=t["condition"],
               file_shape="", compiles_f=False, pass_f=False,
               found_f=0, failed_f=0, compiles_b=False, pass_b=False,
               found_b=0, failed_b=0, bug_detected=False, note="")
    if not fi:
        row["note"] = "fixed_checkout_failed"
        return row

    rf = compile_and_run(t, fi, "f", args.work, args.javac, args.java,
                         args.junit5, args.strict, args.timeout)
    row["file_shape"] = rf["shape"]
    row["compiles_f"] = rf["compiles"]
    if not rf["compiles"]:
        row["note"] = f"nocompile_f:{rf['compile_err']}"
        _log(args, t, "f", rf)
        return row

    run_f = rf["run"]
    row["found_f"] = run_f["found"]
    row["failed_f"] = run_f["failed"] + run_f["cfailed"]
    # Valid = built on fixed, actually ran a test, nothing failed, no hang.
    row["pass_f"] = (run_f["found"] > 0 and run_f["failed"] == 0
                     and run_f["cfailed"] == 0 and not run_f["timeout"]
                     and not run_f["launch_error"])
    if run_f["timeout"]:
        row["note"] = "timeout_f"
    _log(args, t, "f", rf)

    # Only a valid test can meaningfully be checked for detection, but we still
    # record buggy-run numbers for every compiling file for audit.
    if bi:
        rb = compile_and_run(t, bi, "b", args.work, args.javac, args.java,
                             args.junit5, args.strict, args.timeout)
        row["compiles_b"] = rb["compiles"]
        if rb["compiles"]:
            run_b = rb["run"]
            row["found_b"] = run_b["found"]
            row["failed_b"] = run_b["failed"] + run_b["cfailed"]
            _log(args, t, "b", rb)
            if (row["pass_f"] and run_b["found"] > 0
                    and (run_b["failed"] > 0 or run_b["cfailed"] > 0)
                    and not run_b["timeout"]):
                row["bug_detected"] = True
        else:
            if not row["note"]:
                row["note"] = f"nocompile_b:{rb['compile_err']}"
    else:
        row["note"] = (row["note"] + ";buggy_checkout_failed").strip(";")
    return row


def _log(args, t, ver, r):
    if r["compiles"] and r["run"] is not None:
        raw = r["run"]["raw"]
    else:
        raw = r["compile_stderr"]
    if raw and raw.strip():
        p = (args.out / "run_logs"
             / f"{t['model']}_{t['bug']}_{t['condition']}_{ver}.txt")
        p.write_text(raw)


COLS = ["model", "bug", "condition", "file_shape", "compiles_f", "pass_f",
        "found_f", "failed_f", "compiles_b", "pass_b", "found_b",
        "failed_b", "bug_detected", "note"]
_BOOL_COLS = {"compiles_f", "pass_f", "compiles_b", "pass_b", "bug_detected"}
_INT_COLS = {"found_f", "failed_f", "found_b", "failed_b"}


def _dump_csv(out, results):
    with open(out / "execution_results.csv", "w", newline="") as fh:
        w = csv.DictWriter(fh, fieldnames=COLS)
        w.writeheader()
        w.writerows(results)


def _load_prior(out):
    """Prior rows keyed by (model,bug,condition) so an interrupted sweep can
    resume instead of recomputing. Bool/int columns are coerced back from the
    CSV's string representation."""
    path = out / "execution_results.csv"
    done = {}
    if not path.is_file():
        return done
    with open(path, newline="") as fh:
        for r in csv.DictReader(fh):
            for c in _BOOL_COLS:
                r[c] = str(r.get(c)).strip().lower() == "true"
            for c in _INT_COLS:
                try:
                    r[c] = int(r.get(c) or 0)
                except ValueError:
                    r[c] = 0
            done[(r["model"], r["bug"], r["condition"])] = r
    return done


def _print_row(row):
    tag = ("DETECT" if row["bug_detected"]
           else "valid " if row["pass_f"]
           else "invalid" if row["compiles_f"]
           else "nocomp ")
    print(f"  [{tag:7}] {row['model']:16} {row['bug']:9} {row['condition']}"
          f"  f(found={row['found_f']},fail={row['failed_f']}) "
          f"b(found={row['found_b']},fail={row['failed_b']}) {row['note']}")


def _write_outputs(args, results):
    _dump_csv(args.out, results)
    csv_path = args.out / "execution_results.csv"

    models = sorted({r["model"] for r in results})
    # per-model tallies
    comp = defaultdict(int)      # compiles on fixed
    valid = defaultdict(int)     # pass on fixed
    detect = defaultdict(int)    # bug detected
    total = defaultdict(int)
    cond_detect = defaultdict(lambda: defaultdict(int))
    cond_total = defaultdict(lambda: defaultdict(int))
    for r in results:
        m = r["model"]
        total[m] += 1
        comp[m] += int(r["compiles_f"])
        valid[m] += int(r["pass_f"])
        detect[m] += int(r["bug_detected"])
        cond_total[m][r["condition"]] += 1
        cond_detect[m][r["condition"]] += int(r["bug_detected"])

    L = ["# Execution & Bug-Detection Results (Defects4J v2.0.0)\n",
         f"- import mode: **{'strict' if args.strict else 'lenient'}**",
         f"- per-file run timeout: **{args.timeout}s**, headless AWT",
         f"- total files: **{len(results)}**\n",
         "Definitions: **valid** = compiles on fixed, runs >=1 test, 0 failures.",
         "**detected** = valid AND compiles on buggy AND >=1 failure on buggy",
         "(the Defects4J fault-revealing criterion: pass on fixed, fail on buggy).\n",
         "## Per model\n",
         "| Model | Files | Compiles(f) | Valid (pass f) | Bugs detected | "
         "Detection rate |",
         "|---|---|---|---|---|---|"]
    for m in models:
        tot = total[m]
        rate = f"{detect[m]/tot*100:.1f}%" if tot else "-"
        L.append(f"| {m} | {tot} | {comp[m]} | {valid[m]} | {detect[m]} | {rate} |")

    L += ["\n## Detection by condition (C1 zero-shot vs C2 few-shot)\n",
          "| Model | C1 detected | C2 detected |", "|---|---|---|"]
    for m in models:
        c1t, c2t = cond_total[m].get("C1", 0), cond_total[m].get("C2", 0)
        c1 = (f"{cond_detect[m].get('C1',0)}/{c1t}" if c1t else "-")
        c2 = (f"{cond_detect[m].get('C2',0)}/{c2t}" if c2t else "-")
        L.append(f"| {m} | {c1} | {c2} |")

    # per-bug: how many models/conditions detected each bug
    bugs = sorted({r["bug"] for r in results},
                  key=lambda b: (b.split("-")[0], int(b.split("-")[1])))
    L += ["\n## Bugs ever detected (any model/condition)\n",
          "| Bug | #files detecting | detecting models |", "|---|---|---|"]
    for b in bugs:
        hits = [r for r in results if r["bug"] == b and r["bug_detected"]]
        who = sorted({f"{r['model']}/{r['condition']}" for r in hits})
        L.append(f"| {b} | {len(hits)} | {', '.join(who) if who else '-'} |")

    (args.out / "execution_summary.md").write_text("\n".join(L))
    print(f"\nWrote:\n  {csv_path}\n  {args.out/'execution_summary.md'}")
    print("Done.")


if __name__ == "__main__":
    main()
