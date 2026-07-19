#!/usr/bin/env python3
"""
measure_smells.py
=================
Stage 4 of the study (RQ3 test-smell density). Runs **tsDetect**
(TestSmellDetector) over all 240 generated tests and reports how many test
smells each model's tests carry.

Unlike the earlier stages, tsDetect only needs the test file to *parse* -- it
does not compile or run anything -- so smells are measured on the FULL 240-file
output, including tests that never compiled. It detects 21 smell types
(Assertion Roulette, Eager Test, Mystery Guest, Magic Number Test, ...).

Method
------
  * Materialise each generated file as a parseable Java compilation unit using
    the same shape handling as the other stages (imported from
    measure_compilability / measure_execution): full_class files as-is,
    fragments wrapped in a minimal scaffold class. Imports do not matter here
    (tsDetect parses, it does not resolve), so a strict/minimal scaffold is
    used -- no Defects4J classpath needed for the test itself.
  * Point tsDetect at the real **focal class source** as the "production file"
    (found in the cached fixed checkout), which enables the production-aware
    smells (Eager Test, Mystery Guest, Sensitive Equality, ...).
  * Build one tsDetect input CSV (appName,testPath,productionPath) and run the
    detector ONCE over all files (numerical granularity = smell counts).
  * Parse tsDetect's output and aggregate per model, per smell, and per
    condition (C1 vs C2, RQ4).

OUTPUT (results/smells/)
  * smells_results.csv   -- one row per file: NumberOfMethods, every smell
                            count, total smells, distinct smell types
  * smells_summary.md    -- per-model mean smells/test + mean distinct types,
                            per-smell prevalence, and a C1-vs-C2 split
  * tsdetect_raw.csv     -- tsDetect's own output (audit)
  * tsdetect_input.csv   -- the generated input list (audit)

Run via run_smells.sh (builds/points to the tsDetect jar).
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

sys.path.insert(0, str(Path(__file__).resolve().parent))
import measure_compilability as mc   # noqa: E402
import measure_execution as me       # noqa: E402

# tsDetect output columns before the smell columns start.
META_COLS = ["App", "TestClass", "TestFilePath", "ProductionFilePath",
             "RelativeTestFilePath", "RelativeProductionFilePath",
             "NumberOfMethods"]
APP_SEP = "__"   # appName = model__bug__condition (no model name contains it)

# tsDetect's IgnoredTest rule flags any @Test method that is not `public` as an
# ignored/disabled test -- correct for JUnit 4, but WRONG for the JUnit 5
# (Jupiter) tests here, where package-private `void test...()` methods run
# normally. Verified: IgnoredTest>0 in 153/154 files that use bare `void` and
# 0/86 files that use `public void`, i.e. it tracks the JUnit-5 bare-void style
# (which few-shot encourages), not a real smell. Kept as a column for
# transparency but EXCLUDED from the smell totals/density so RQ3 is not skewed
# by a JUnit-4 tooling artifact.
EXCLUDED_SMELLS = {"IgnoredTest"}


def find_focal_source(work, proj, num, focal_class):
    """(package, path-to-focal-.java) from the cached fixed checkout, or
    (None, None) if unavailable."""
    if not focal_class:
        return None, None
    project = mc.PROJECT_OF.get(proj, proj)
    wdir = work / f"{project}_{num}f"
    if not wdir.is_dir():
        return None, None
    return mc.find_package_for_class(wdir, focal_class.split(".")[-1])


def materialise(t, focal_pkg, work):
    """Write a parseable test compilation unit; return its path."""
    info = {"package": focal_pkg, "project_pkgs": []}
    package, class_name, source, _shape = me.build_test_source(t, info,
                                                               strict=True)
    d = work / "_smells" / t["model"] / f"{t['bug']}_{t['condition']}" \
        / package.replace(".", os.sep)
    d.mkdir(parents=True, exist_ok=True)
    path = d / f"{class_name}.java"
    path.write_text(source, encoding="utf-8")
    return path


def main():
    ap = argparse.ArgumentParser(
        description=__doc__,
        formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--repo", required=True, type=Path)
    ap.add_argument("--tsdetect", required=True, type=Path, help="tsDetect jar")
    ap.add_argument("--java", default="java")
    ap.add_argument("--work", type=Path, default=Path("./_d4j_work"))
    ap.add_argument("--out", type=Path, default=Path("./results/smells"))
    ap.add_argument("--models", nargs="*", default=mc.MODELS_DEFAULT)
    ap.add_argument("--bugs", nargs="*", default=None)
    ap.add_argument("--granularity", default="numerical",
                    choices=["numerical", "boolean"])
    args = ap.parse_args()

    gen_root = args.repo / "data" / "generated"
    if not gen_root.is_dir():
        sys.exit(f"ERROR: {gen_root} not found")
    args.out.mkdir(parents=True, exist_ok=True)

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
    print(f"Found {len(tasks)} test files across {len(bugs)} bugs.")

    methods = {}
    mj = args.repo / "data" / "raw" / "methods" / "methods.json"
    if mj.is_file():
        for e in json.loads(mj.read_text()):
            methods[e["id"]] = e

    # Resolve the focal source (production file) once per bug.
    focal = {}
    for (proj, num) in sorted(bugs, key=lambda t: (t[0], int(t[1]))):
        fc = methods.get(f"{proj}-{num}", {}).get("class")
        pkg, path = find_focal_source(args.work, proj, num, fc)
        focal[(proj, num)] = (pkg, path)
    missing = [b for b, (_, p) in focal.items() if p is None]
    if missing:
        print(f"  WARNING: no production file for {len(missing)} bug(s); "
              f"production-aware smells skipped there.")

    # Materialise every test and build the tsDetect input list.
    smells_work = args.work / "_smells"
    shutil.rmtree(smells_work, ignore_errors=True)
    input_rows, app_meta = [], {}
    for t in tasks:
        pkg, prod = focal[(t["project"], t["num"])]
        test_path = materialise(t, pkg, args.work)
        app = f"{t['model']}{APP_SEP}{t['bug']}{APP_SEP}{t['condition']}"
        app_meta[app] = t
        input_rows.append((app, str(test_path), str(prod) if prod else ""))

    input_csv = args.out / "tsdetect_input.csv"
    with open(input_csv, "w", newline="") as fh:
        for app, tp, pp in input_rows:
            fh.write(f"{app},{tp},{pp}\n")

    # Run tsDetect ONE FILE AT A TIME. Its Main does not catch per-file parse
    # errors -- a single lexical error (e.g. an illegal string escape) throws an
    # uncaught JavaParser exception and aborts the whole batch, silently
    # dropping every file after it. Per-file runs isolate the failures: a
    # genuinely unparseable test is recorded parsed=False, the rest are fine.
    tmp = args.work / "_smells_run"
    shutil.rmtree(tmp, ignore_errors=True)
    tmp.mkdir(parents=True, exist_ok=True)
    header, parsed = None, {}
    raw_rows = []
    print(f"Running tsDetect over {len(input_rows)} files (one at a time) ...")
    for i, (app, tp, pp) in enumerate(input_rows, 1):
        one_in = tmp / "in.csv"
        one_in.write_text(f"{app},{tp},{pp}\n")
        one_out = tmp / "out.csv"
        if one_out.exists():
            one_out.unlink()
        subprocess.run(
            [args.java, "-jar", str(args.tsdetect), "-f", str(one_in),
             "-g", args.granularity, "-o", str(one_out)],
            capture_output=True, text=True)
        if not one_out.is_file():
            continue   # unparseable -> stays parsed=False
        with open(one_out, newline="") as fh:
            rows = list(csv.reader(fh))
        if len(rows) < 2:
            continue
        if header is None:
            header = rows[0]
        idx = {c: j for j, c in enumerate(rows[0])}
        data = rows[1]
        raw_rows.append(data)
        smell_cols_local = [c for c in rows[0] if c not in META_COLS]
        counts = {}
        for c in smell_cols_local:
            try:
                counts[c] = int(data[idx[c]])
            except (ValueError, IndexError):
                counts[c] = 0
        try:
            nmeth = int(data[idx["NumberOfMethods"]])
        except (ValueError, IndexError):
            nmeth = 0
        parsed[app] = (counts, nmeth)
        if i % 40 == 0:
            print(f"  ... {i}/{len(input_rows)} ({len(parsed)} parsed)")

    if header is None:
        sys.exit("tsDetect parsed no files at all -- check the jar/input.")
    smell_cols = [c for c in header if c not in META_COLS]

    # Combined raw output for audit.
    with open(args.out / "tsdetect_raw.csv", "w", newline="") as fh:
        w = csv.writer(fh)
        w.writerow(header)
        w.writerows(raw_rows)

    # Per-file result rows (every task, even if tsDetect dropped it).
    results = []
    for t in tasks:
        app = f"{t['model']}{APP_SEP}{t['bug']}{APP_SEP}{t['condition']}"
        row = dict(model=t["model"], bug=t["bug"], condition=t["condition"])
        if app in parsed:
            counts, nmeth = parsed[app]
            total = sum(v for c, v in counts.items()
                        if c not in EXCLUDED_SMELLS)
            distinct = sum(1 for c, v in counts.items()
                           if v > 0 and c not in EXCLUDED_SMELLS)
            row.update(parsed=True, num_methods=nmeth, total_smells=total,
                       distinct_smells=distinct, **counts)
        else:
            row.update(parsed=False, num_methods=0, total_smells=0,
                       distinct_smells=0, **{c: 0 for c in smell_cols})
        results.append(row)

    _write_outputs(args, results, smell_cols)


def _write_outputs(args, results, smell_cols):
    cols = (["model", "bug", "condition", "parsed", "num_methods",
             "total_smells", "distinct_smells"] + smell_cols)
    with open(args.out / "smells_results.csv", "w", newline="") as fh:
        w = csv.DictWriter(fh, fieldnames=cols)
        w.writeheader()
        w.writerows(results)

    models = sorted({r["model"] for r in results})

    def _mean(rows, key):
        rows = [r for r in rows if r["parsed"]]
        return round(sum(r[key] for r in rows) / len(rows), 2) if rows else 0.0

    excluded_note = (f" (excludes {', '.join(sorted(EXCLUDED_SMELLS))} — a "
                     "JUnit-4 tooling artifact on JUnit-5 non-public tests)"
                     if EXCLUDED_SMELLS else "")
    L = ["# Test-Smell Results (tsDetect, RQ3)\n",
         f"- granularity: **{args.granularity}** (smell instance counts)",
         f"- total files: **{len(results)}**",
         "- measured on ALL generated tests that parse (does not require "
         "compilation)\n",
         f"21 smell types detected. **total_smells** = sum of all smell "
         f"instances in a test class{excluded_note}; **distinct** = number of "
         "different smell types present.\n",
         "## Per model\n",
         "| Model | Files parsed | Mean smells/test | Mean distinct types/test |",
         "|---|---|---|---|"]
    for m in models:
        rows = [r for r in results if r["model"] == m]
        parsed_n = sum(1 for r in rows if r["parsed"])
        L.append(f"| {m} | {parsed_n}/{len(rows)} | "
                 f"{_mean(rows, 'total_smells')} | "
                 f"{_mean(rows, 'distinct_smells')} |")

    L += ["\n## By condition (C1 zero-shot vs C2 few-shot) — RQ4\n",
          "| Model | C1 smells/test | C2 smells/test |", "|---|---|---|"]
    for m in models:
        c1 = [r for r in results if r["model"] == m and r["condition"] == "C1"]
        c2 = [r for r in results if r["model"] == m and r["condition"] == "C2"]
        L.append(f"| {m} | {_mean(c1, 'total_smells')} | "
                 f"{_mean(c2, 'total_smells')} |")

    # Per-smell prevalence: in how many parsed tests each smell appears.
    L += ["\n## Smell prevalence (parsed tests containing each smell)\n",
          "| Smell | Files | Total instances |", "|---|---|---|"]
    prevalence = []
    for c in smell_cols:
        files = sum(1 for r in results if r["parsed"] and r.get(c, 0) > 0)
        inst = sum(r.get(c, 0) for r in results if r["parsed"])
        prevalence.append((c, files, inst))
    for c, files, inst in sorted(prevalence, key=lambda x: -x[1]):
        mark = " *(excluded, JUnit-4 artifact)*" if c in EXCLUDED_SMELLS else ""
        L.append(f"| {c}{mark} | {files} | {inst} |")

    (args.out / "smells_summary.md").write_text("\n".join(L))
    print(f"\nWrote:\n  {args.out/'smells_results.csv'}"
          f"\n  {args.out/'smells_summary.md'}\n  {args.out/'tsdetect_raw.csv'}"
          f"\nDone.")


if __name__ == "__main__":
    main()
