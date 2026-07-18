#!/usr/bin/env python3
"""
measure_coverage.py
===================
Stage 3 of the study (RQ1 line coverage, RQ2 branch coverage). Measures how
much of each focal class the LLM-generated tests exercise, using **JaCoCo** on
the real Defects4J v2.0.0 **fixed** checkouts.

Method, per (model, bug, condition) file
----------------------------------------
  * Reuse the exact scaffold / full-compilation-unit and classpath that
    measure_compilability / measure_execution build (imported -- one source of
    truth for file shapes, lenient imports, Mockito), so coverage is measured
    on identical code.
  * Compile the test against the fixed classpath. A test that does not compile
    covers nothing (recorded compiles=False, 0 coverage).
  * Run it under the JaCoCo agent (`-javaagent:jacocoagent.jar=destfile=...`)
    via the JUnit Platform Console (Jupiter engine only, headless AWT, per-file
    timeout). A test that runs but fails assertions still records the coverage
    of whatever it executed before failing -- that is the honest number.
  * Produce a JaCoCo XML report over the project's compiled classes and read
    the **focal class's** LINE and BRANCH counters (missed/covered).

Coverage is of the FOCAL CLASS (the class under test named in methods.json),
which is the standard granularity for RQ1/RQ2. Runs on the fixed program: we
are asking how well the generated test exercises correct code.

OUTPUT (results/coverage/)
  * coverage_results.csv   -- one row per file, all counters + percentages
  * coverage_summary.md    -- per-model mean line/branch coverage over the tests
                              that ran, and a C1-vs-C2 (RQ4) split
  * jacoco_xml/            -- the raw per-file JaCoCo XML reports (audit)

Resumable exactly like measure_execution: per-file CSV checkpoint, bug-major
processing, per-checkout classpath/package caches. See run_coverage.sh.
"""

import argparse
import csv
import json
import os
import re
import shutil
import subprocess
import sys
import xml.etree.ElementTree as ET
from collections import defaultdict
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))
import measure_compilability as mc   # noqa: E402
import measure_execution as me       # noqa: E402


COLS = ["model", "bug", "condition", "file_shape", "compiles", "ran",
        "focal_class", "line_missed", "line_covered", "line_pct",
        "branch_missed", "branch_covered", "branch_pct", "note"]
_INT_COLS = {"line_missed", "line_covered", "branch_missed", "branch_covered"}
_FLOAT_COLS = {"line_pct", "branch_pct"}
_BOOL_COLS = {"compiles", "ran"}


def _dump_csv(out, results):
    with open(out / "coverage_results.csv", "w", newline="") as fh:
        w = csv.DictWriter(fh, fieldnames=COLS)
        w.writeheader()
        w.writerows(results)


def _load_prior(out):
    path = out / "coverage_results.csv"
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
            for c in _FLOAT_COLS:
                try:
                    r[c] = float(r.get(c) or 0)
                except ValueError:
                    r[c] = 0.0
            done[(r["model"], r["bug"], r["condition"])] = r
    return done


def focal_internal_name(focal_class, package):
    """Internal (slash-separated) name JaCoCo uses, e.g.
    org/apache/commons/lang3/time/FastDatePrinter."""
    if not focal_class:
        return None
    simple = focal_class.split(".")[-1]
    if "." in focal_class and package is None:
        pkg = ".".join(focal_class.split(".")[:-1])
    else:
        pkg = package
    return (pkg.replace(".", "/") + "/" + simple) if pkg else simple


def classfile_dirs(classpath):
    """Directory entries of the classpath = the project's compiled output that
    JaCoCo should analyse (the jars are third-party deps)."""
    dirs = []
    for entry in classpath.split(os.pathsep):
        if entry and os.path.isdir(entry):
            dirs.append(entry)
    return dirs


def parse_focal_counters(xml_path, focal_internal):
    """Return (line_missed, line_covered, branch_missed, branch_covered) for the
    focal class, or None if the class is absent from the report."""
    try:
        root = ET.parse(xml_path).getroot()
    except (ET.ParseError, FileNotFoundError):
        return None
    for cls in root.iter("class"):
        if cls.get("name") == focal_internal:
            got = {"LINE": (0, 0), "BRANCH": (0, 0)}
            for ctr in cls.findall("counter"):
                t = ctr.get("type")
                if t in got:
                    got[t] = (int(ctr.get("missed", 0)),
                              int(ctr.get("covered", 0)))
            lm, lc = got["LINE"]
            bm, bc = got["BRANCH"]
            return lm, lc, bm, bc
    return None


def run_with_jacoco(java, run_cp, fqcn, agent, exec_file, timeout):
    if exec_file.exists():
        exec_file.unlink()
    cmd = [java, "-Djava.awt.headless=true",
           f"-javaagent:{agent}=destfile={exec_file}",
           "-cp", run_cp,
           "org.junit.platform.console.ConsoleLauncher", "execute",
           "--include-engine=junit-jupiter",
           "--disable-banner", "--details=summary",
           "--select-class", fqcn]
    try:
        p = subprocess.run(cmd, capture_output=True, text=True, timeout=timeout)
        return True, (p.stdout + p.stderr)
    except subprocess.TimeoutExpired as e:
        return False, ((e.stdout or "") + (e.stderr or "") or "TIMEOUT")


def jacoco_report(java, cli_jar, exec_file, classfiles, xml_out):
    cmd = [java, "-jar", str(cli_jar), "report", str(exec_file)]
    for d in classfiles:
        cmd += ["--classfiles", d]
    cmd += ["--xml", str(xml_out)]
    return subprocess.run(cmd, capture_output=True, text=True)


def _pct(missed, covered):
    tot = missed + covered
    return round(covered / tot * 100, 1) if tot else 0.0


def process_task(t, info, focal_class, args):
    row = dict(model=t["model"], bug=t["bug"], condition=t["condition"],
               file_shape="", compiles=False, ran=False, focal_class="",
               line_missed=0, line_covered=0, line_pct=0.0,
               branch_missed=0, branch_covered=0, branch_pct=0.0, note="")
    if not info:
        row["note"] = "fixed_checkout_failed"
        return row

    package, class_name, source, shape = me.build_test_source(t, info,
                                                              args.strict)
    row["file_shape"] = shape
    fqcn = f"{package}.{class_name}" if package else class_name

    tmp = args.work / "_cov" / t["model"] / f"{t['bug']}_{t['condition']}"
    if tmp.exists():
        shutil.rmtree(tmp, ignore_errors=True)
    pkg_dir = tmp / package.replace(".", os.sep)
    pkg_dir.mkdir(parents=True, exist_ok=True)
    (pkg_dir / f"{class_name}.java").write_text(source, encoding="utf-8")
    out_dir = tmp / "_out"
    out_dir.mkdir(parents=True, exist_ok=True)

    jc = subprocess.run(
        [args.javac, "-encoding", "UTF-8", "-cp", info["classpath"],
         "-d", str(out_dir), str(pkg_dir / f"{class_name}.java")],
        capture_output=True, text=True)
    if jc.returncode != 0:
        row["note"] = f"nocompile:{mc.classify_error(jc.stderr)}"
        return row
    row["compiles"] = True

    exec_file = tmp / "jacoco.exec"
    run_cp = os.pathsep.join([str(out_dir), info["classpath"]])
    ran, raw = run_with_jacoco(args.java, run_cp, fqcn, args.jacoco_agent,
                               exec_file, args.timeout)
    row["ran"] = ran and exec_file.exists()
    if not exec_file.exists():
        row["note"] = row["note"] or ("timeout" if not ran else "no_exec")
        return row

    focal_internal = focal_internal_name(focal_class, info["package"])
    row["focal_class"] = focal_internal or ""
    xml_out = args.out / "jacoco_xml" / \
        f"{t['model']}_{t['bug']}_{t['condition']}.xml"
    rep = jacoco_report(args.java, args.jacoco_cli, exec_file,
                        classfile_dirs(info["classpath"]), xml_out)
    if rep.returncode != 0:
        row["note"] = "jacoco_report_failed"
        return row

    counters = parse_focal_counters(xml_out, focal_internal)
    if counters is None:
        # Test ran but never loaded the focal class -> 0 coverage of it.
        row["note"] = row["note"] or "focal_class_not_in_report"
        return row
    lm, lc, bm, bc = counters
    row.update(line_missed=lm, line_covered=lc, line_pct=_pct(lm, lc),
               branch_missed=bm, branch_covered=bc, branch_pct=_pct(bm, bc))
    return row


def _print_row(row):
    tag = "cover " if row["ran"] else "nocomp" if not row["compiles"] else "norun "
    print(f"  [{tag}] {row['model']:16} {row['bug']:9} {row['condition']}"
          f"  line={row['line_pct']:5}% branch={row['branch_pct']:5}%"
          f"  {row['note']}")


def main():
    ap = argparse.ArgumentParser(
        description=__doc__,
        formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--repo", required=True, type=Path)
    ap.add_argument("--d4j", default="defects4j")
    ap.add_argument("--javac", default="javac")
    ap.add_argument("--java", default="java")
    ap.add_argument("--junit5", type=Path, required=True)
    ap.add_argument("--jacoco-agent", type=Path, required=True)
    ap.add_argument("--jacoco-cli", type=Path, required=True)
    ap.add_argument("--extra-cp", default="")
    ap.add_argument("--work", type=Path, default=Path("./_d4j_work"))
    ap.add_argument("--out", type=Path, default=Path("./results/coverage"))
    ap.add_argument("--models", nargs="*", default=mc.MODELS_DEFAULT)
    ap.add_argument("--bugs", nargs="*", default=None)
    ap.add_argument("--strict", action="store_true")
    ap.add_argument("--reuse-checkouts", action="store_true")
    ap.add_argument("--fresh", action="store_true",
                    help="ignore any existing coverage_results.csv and rerun")
    ap.add_argument("--timeout", type=int, default=90)
    args = ap.parse_args()

    gen_root = args.repo / "data" / "generated"
    if not gen_root.is_dir():
        sys.exit(f"ERROR: {gen_root} not found")
    args.out.mkdir(parents=True, exist_ok=True)
    if args.fresh:
        shutil.rmtree(args.out / "jacoco_xml", ignore_errors=True)
    (args.out / "jacoco_xml").mkdir(parents=True, exist_ok=True)

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
        print("  WARNING: methods.json not found; focal class unknown.")

    prior = {} if args.fresh else _load_prior(args.out)
    if prior:
        print(f"  resuming: {len(prior)} rows already recorded, reusing")

    by_bug = defaultdict(list)
    for t in tasks:
        by_bug[(t["project"], t["num"])].append(t)

    results = []
    for (proj, num) in sorted(by_bug, key=lambda t: (t[0], int(t[1]))):
        bug_tasks = by_bug[(proj, num)]
        need = [t for t in bug_tasks
                if (t["model"], t["bug"], t["condition"]) not in prior]
        info = None
        focal_class = methods.get(f"{proj}-{num}", {}).get("class")
        if need:
            project = mc.PROJECT_OF.get(proj, proj)
            print(f"\n=== {proj}-{num}: checkout fixed ({len(need)} pending) ===")
            info = me.build_info(
                args.d4j, project, num, "f", args.work / f"{project}_{num}f",
                args.junit5, args.extra_cp, focal_class, args.reuse_checkouts)
            print(f"    focal={focal_class} pkg="
                  f"{info['package'] if info else 'CHECKOUT-FAILED'}")
        for t in bug_tasks:
            key = (t["model"], t["bug"], t["condition"])
            if key in prior:
                results.append(prior[key])
                continue
            row = process_task(t, info, focal_class, args)
            results.append(row)
            _print_row(row)
            _dump_csv(args.out, results)

    _write_outputs(args, results)


def _write_outputs(args, results):
    _dump_csv(args.out, results)
    models = sorted({r["model"] for r in results})

    # Mean coverage is taken over the tests that actually ran (a test that did
    # not compile has no coverage to average and would just dilute the signal).
    def _agg(rows):
        ran = [r for r in rows if r["ran"]]
        n = len(ran)
        line = round(sum(float(r["line_pct"]) for r in ran) / n, 1) if n else 0.0
        branch = round(sum(float(r["branch_pct"]) for r in ran) / n, 1) \
            if n else 0.0
        return n, line, branch

    L = ["# Coverage Results (JaCoCo on Defects4J v2.0.0 fixed trees)\n",
         f"- import mode: **{'strict' if args.strict else 'lenient'}**",
         f"- per-file run timeout: **{args.timeout}s**, headless AWT",
         f"- total files: **{len(results)}**\n",
         "Coverage is of the **focal class**, on the fixed program, averaged "
         "over the tests that ran (compiled + executed). A test that fails "
         "assertions still counts the code it exercised before failing.\n",
         "## Per model (mean over tests that ran)\n",
         "| Model | Ran | Mean line cov | Mean branch cov |",
         "|---|---|---|---|"]
    for m in models:
        n, line, branch = _agg([r for r in results if r["model"] == m])
        L.append(f"| {m} | {n} | {line}% | {branch}% |")

    L += ["\n## By condition (C1 zero-shot vs C2 few-shot) — RQ4\n",
          "| Model | C1 line | C1 branch | C2 line | C2 branch |",
          "|---|---|---|---|---|"]
    for m in models:
        n1, l1, b1 = _agg([r for r in results
                           if r["model"] == m and r["condition"] == "C1"])
        n2, l2, b2 = _agg([r for r in results
                           if r["model"] == m and r["condition"] == "C2"])
        L.append(f"| {m} | {l1}% | {b1}% | {l2}% | {b2}% |")

    (args.out / "coverage_summary.md").write_text("\n".join(L))
    print(f"\nWrote:\n  {args.out/'coverage_results.csv'}"
          f"\n  {args.out/'coverage_summary.md'}\nDone.")


if __name__ == "__main__":
    main()
