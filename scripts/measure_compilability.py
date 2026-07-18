#!/usr/bin/env python3
"""
measure_compilability.py
========================
Measure the *compilability rate* of LLM-generated JUnit tests against a real
Defects4J v2.0.0 checkout, and auto-classify every compile failure into the
known bug-pattern buckets (protected constructor, abstract class, invented
constructor signature, missing symbol, Mockito-not-on-classpath, ...).

Designed for the SE1944 / G7 study:
  data/generated/{model}/{Bug}-{id}_{C1|C2}.java   (240 files, two shapes)

FILE SHAPES (do not assume one!)
--------------------------------
The models did not all answer in the same form, so each file is classified by
`detect_shape()` and handled accordingly:
  * fragment (177 files: all gemini + all gpt-5.5-instant + 57 deepseek)
      bare method fragments -- no `package`, no `import`, no class declaration.
      These cannot compile as-is, so they get wrapped in a scaffold test class
      inside the focal class's own package.
  * full_class (63 files: all 60 llama + deepseek Lang-8_C1/C2, Math-2_C2)
      complete compilation units the model wrote itself, with `package` (or
      not), `import`s and a top-level `public class ...Test`. These are
      compiled AS-IS. Wrapping one in the scaffold would nest a class inside a
      class and fail all 63 on syntax alone -- reported as a 0% model score
      when it is purely a harness artifact.
The `file_shape` column in compilability_results.csv records which path each
file took, so the split stays auditable.

CLASSPATH NOTE (Mockito)
------------------------
16 files use Mockito, which is NOT a dependency of Lang/Math/Chart. Without
the jars on the classpath they fail on a missing dependency rather than on
test quality, so run_compile.sh puts mockito-core + byte-buddy + objenesis on
--extra-cp by default. Report that this was done.

IMPORT STRATEGY (methodological choice -- documented, switchable):
  * default (lenient): auto-add `import <pkg>.*;` for EVERY package found in
    the checked-out project source tree, plus JUnit 5 + java.util/io/time.
    -> isolates structural/API compile errors from missing-import noise.
  * --strict: add ONLY JUnit + java.* wildcards, NOT project packages.
    -> a missing import counts as a compile failure (harsher, closer to
       "would this paste-and-run as the model wrote it").

Both numbers are worth reporting; lenient is the fair "did the model use the
API correctly" measure, strict is the "raw usability" measure.

REQUIREMENTS (run on YOUR machine, not the sandbox):
  * Defects4J v2.0.0 installed and `defects4j` on PATH
      (or pass --d4j <path-to-defects4j-bin>)
  * A JDK with `javac` (the study targets javac 21; see --javac)
  * A JUnit 5 standalone jar for compiling `@Test`/`assertThrows`/`@DisplayName`
      (junit-platform-console-standalone-*.jar). Pass --junit5 <jar>.
  * (optional) mockito-core + byte-buddy + objenesis jars if you want the 15
      Mockito-using tests to have a chance; pass them via --extra-cp.

See scripts/run_compile.sh for a one-command wrapper, and
COMPILABILITY_SETUP.md for install steps.
"""

import argparse
import csv
import os
import re
import shutil
import subprocess
import sys
from collections import defaultdict, OrderedDict
from pathlib import Path

PROJECT_OF = {"Lang": "Lang", "Math": "Math", "Chart": "Chart", "Time": "Time",
              "Cli": "Cli", "Closure": "Closure"}

MODELS_DEFAULT = ["llama", "gemini", "deepseek", "gpt-5.5-instant"]
CONDITIONS = ["C1", "C2"]

ERROR_PATTERNS = OrderedDict([
    # Harness artifact: a simple name matched two of lenient mode's injected
    # wildcard imports (typically java.util.Vector vs a project Vector). Must
    # stay ahead of the method-overload case and be reported separately -- it
    # is not a model error. Should read 0; re-check any under --strict.
    ("ambiguous_type_from_injected_import",
        re.compile(r"reference to \w+ is ambiguous(?:.|\n)*?both (?:class|interface) "
                   r"[\w.]+ in [\w.]+ and (?:class|interface)", re.I)),
    # Real model error: e.g. StringUtils.containsAny("abc", null) -- a bare
    # null against overloads is ambiguous in Java whatever the imports are.
    ("ambiguous_method_overload",
        re.compile(r"reference to \w+ is ambiguous(?:.|\n)*?both method \w+\(", re.I)),
    ("unreported_checked_exception",
        re.compile(r"unreported exception \w+; must be caught or declared", re.I)),
    ("illegal_escape_character",
        re.compile(r"illegal escape character", re.I)),
    ("keyword_package_import",
        re.compile(r"is a keyword, and may not be used as an identifier", re.I)),
    ("protected_or_private_ctor",
        re.compile(r"(constructor .*? (has private access|is not public|has protected access)"
                   r"|\bhas protected access in\b.*\bconstructor)", re.I)),
    ("abstract_instantiation",
        re.compile(r"(is abstract; cannot be instantiated|abstract class .*? cannot be instantiated)", re.I)),
    ("no_suitable_constructor",
        re.compile(r"(constructor .*? cannot be applied to given types"
                   r"|no suitable constructor found"
                   r"|required:.*found:.*\n.*constructor)", re.I)),
    ("private_method_access",
        re.compile(r"(has private access in|method .*? is not public)", re.I)),
    ("mockito_missing",
        re.compile(r"(package org\.mockito|cannot find symbol.*\bmock\b"
                   r"|cannot find symbol.*\bwhen\b)", re.I)),
    ("cannot_find_symbol",
        re.compile(r"cannot find symbol", re.I)),
    ("incompatible_types",
        re.compile(r"(incompatible types|method .*? cannot be applied to given types)", re.I)),
    ("package_does_not_exist",
        re.compile(r"package .*? does not exist", re.I)),
])


def classify_error(stderr: str) -> str:
    for name, rx in ERROR_PATTERNS.items():
        if rx.search(stderr):
            return name
    if "error:" in stderr:
        return "other_compile_error"
    return "unknown"


def run(cmd, **kw):
    return subprocess.run(cmd, capture_output=True, text=True, **kw)


def d4j(d4j_bin, *args, cwd=None):
    return run([d4j_bin, *args], cwd=cwd)


def find_package_for_class(src_root: Path, class_name: str):
    pkg_re = re.compile(r"^\s*package\s+([\w.]+)\s*;", re.M)
    cls_re = re.compile(r"\b(class|interface|enum)\s+" + re.escape(class_name) + r"\b")
    for path in src_root.rglob(class_name + ".java"):
        try:
            text = path.read_text(errors="ignore")
        except Exception:
            continue
        if cls_re.search(text):
            m = pkg_re.search(text)
            if m:
                return m.group(1), path
    return None, None


# Package names predating the keyword that made them illegal: commons-lang 2.x
# ships a package literally called `enum`, so injecting `import
# org.apache.commons.lang.enum.*;` is a syntax error on any javac >= 5 and
# fails the file on the harness's own import.
JAVA_KEYWORDS = {
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
    "class", "const", "continue", "default", "do", "double", "else", "enum",
    "extends", "final", "finally", "float", "for", "goto", "if", "implements",
    "import", "instanceof", "int", "interface", "long", "native", "new",
    "package", "private", "protected", "public", "return", "short", "static",
    "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
    "transient", "try", "void", "volatile", "while", "_",
}


def _importable(pkg: str) -> bool:
    return not any(seg in JAVA_KEYWORDS for seg in pkg.split("."))


def collect_project_packages(classpath: str):
    """Packages that actually exist on the compile classpath.

    Must not be derived from the source tree: a project ships source it does
    not compile (JFreeChart's experimental/ and swt demo trees), so a
    source-derived list makes lenient mode inject `import <pkg>.*` for
    packages that are not on the classpath, and the file fails on the
    harness's own import. Only classpath *directories* are scanned -- those
    hold the project's own compiled output; the jars are third-party deps
    that lenient mode has no business wildcard-importing.
    """
    pkgs = set()
    for entry in classpath.split(os.pathsep):
        root = Path(entry) if entry else None
        if not root or not root.is_dir():
            continue
        for cls in root.rglob("*.class"):
            rel = cls.parent.relative_to(root)
            if str(rel) != ".":
                pkgs.add(str(rel).replace(os.sep, "."))
    return sorted(p for p in pkgs if _importable(p))


def _uses_mockito(fragment: str) -> bool:
    return bool(re.search(r"\bmock\s*\(|\bwhen\s*\(|Mockito", fragment))


# A top-level type declaration starts at column 0; a fragment's own nested or
# anonymous classes are always indented. That is what separates the two file
# shapes the models produced (see FILE SHAPES in the module docstring).
PACKAGE_RE = re.compile(r"^\s*package\s+([\w.]+)\s*;", re.M)
PUBLIC_TYPE_RE = re.compile(
    r"^public\s+(?:(?:final|abstract|strictfp)\s+)*(?:class|interface|enum)\s+(\w+)", re.M)
ANY_TYPE_RE = re.compile(
    r"^(?:(?:public|final|abstract|strictfp)\s+)*(?:class|interface|enum)\s+(\w+)", re.M)


def detect_shape(text: str):
    """(is_full_compilation_unit, declared_type_name_or_None).

    Java requires a public type's name to match its filename, so the public
    type wins when a file declares several top-level types.
    """
    m = PUBLIC_TYPE_RE.search(text) or ANY_TYPE_RE.search(text)
    return (True, m.group(1)) if m else (False, None)


def _mockito_imports():
    # The type `Mockito` itself is needed for qualified `Mockito.mock(...)`;
    # a static-only import would not bring the type name into scope.
    return ["import org.mockito.*;", "import static org.mockito.Mockito.*;"]


def _lenient_imports(text: str, project_pkgs):
    imports = [f"import {p}.*;" for p in project_pkgs]
    if _uses_mockito(text):
        imports += _mockito_imports()
    return imports


def build_scaffold(fragment: str, package: str, class_name: str,
                   project_pkgs, strict: bool) -> str:
    imports = [
        "import org.junit.jupiter.api.*;",
        "import static org.junit.jupiter.api.Assertions.*;",
        "import java.util.*;",
        "import java.util.stream.*;",
        "import java.io.*;",
        "import java.time.*;",
        "import java.text.*;",
        "import java.math.*;",
    ]
    if _uses_mockito(fragment):
        imports += _mockito_imports()
    if not strict:
        for p in project_pkgs:
            imports.append(f"import {p}.*;")

    header = f"package {package};\n" if package else ""
    return (header + "\n" + "\n".join(imports)
            + f"\n\npublic class {class_name} {{\n" + fragment + "\n}\n")


def build_full_unit(text: str, focal_package, project_pkgs, strict: bool):
    """Compile a model-written compilation unit as-is; never wrap it.

    Wrapping one in a scaffold nests a class inside a class and fails every
    such file on syntax alone, which would read as a model result.
    """
    m = PACKAGE_RE.search(text)
    if m:
        package, body = m.group(1), text
    else:
        package = focal_package or "d4jtest"
        body = f"package {package};\n\n{text}"

    if not strict:
        extra = _lenient_imports(text, project_pkgs)
        if extra:
            pm = PACKAGE_RE.search(body)
            at = pm.end() if pm else 0
            body = body[:at] + "\n" + "\n".join(extra) + "\n" + body[at:]
    return package, body


def main():
    ap = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--repo", required=True, type=Path)
    ap.add_argument("--d4j", default="defects4j")
    ap.add_argument("--javac", default="javac")
    ap.add_argument("--junit5", type=Path, required=True)
    ap.add_argument("--extra-cp", default="")
    ap.add_argument("--work", type=Path, default=Path("./_d4j_work"))
    ap.add_argument("--out", type=Path, default=Path("./compilability_out"))
    ap.add_argument("--models", nargs="*", default=MODELS_DEFAULT)
    ap.add_argument("--bugs", nargs="*", default=None,
                    help="limit to these bug ids (e.g. Lang-8 Math-2); default all")
    ap.add_argument("--strict", action="store_true")
    ap.add_argument("--reuse-checkouts", action="store_true",
                    help="skip checkout+compile where the work dir already exists; "
                         "the checkouts are read-only here, so a --strict re-run "
                         "over the same subjects can reuse them")
    ap.add_argument("--version", choices=["f", "b"], default="f")
    args = ap.parse_args()

    gen_root = args.repo / "data" / "generated"
    if not gen_root.is_dir():
        sys.exit(f"ERROR: {gen_root} not found")

    args.work.mkdir(parents=True, exist_ok=True)
    # Wipe stale stderr: a file that passes on this run leaves its previous
    # failure behind, and mixing runs makes the evidence unreadable.
    shutil.rmtree(args.out / "errors", ignore_errors=True)
    (args.out / "errors").mkdir(parents=True, exist_ok=True)

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

    import json
    methods = {}
    mj = args.repo / "data" / "raw" / "methods" / "methods.json"
    if mj.is_file():
        for e in json.loads(mj.read_text()):
            methods[e["id"]] = e
    else:
        print("  WARNING: methods.json not found; will infer class from source.")

    results = []
    checkout_cache = {}

    for (proj, num) in sorted(bugs, key=lambda t: (t[0], int(t[1]))):
        bug = f"{proj}-{num}"
        project = PROJECT_OF.get(proj, proj)
        wdir = args.work / f"{project}_{num}{args.version}"

        if args.reuse_checkouts and wdir.is_dir():
            print(f"\n=== {bug}: reusing existing checkout ===")
        else:
            print(f"\n=== {bug}: checkout {project} {num}{args.version} ===")
            if wdir.exists():
                shutil.rmtree(wdir, ignore_errors=True)
            co = d4j(args.d4j, "checkout", "-p", project, "-v", f"{num}{args.version}",
                     "-w", str(wdir))
            if co.returncode != 0:
                print(f"  checkout FAILED:\n{co.stderr[:500]}")
                checkout_cache[bug] = None
                continue

            d4j(args.d4j, "compile", "-w", str(wdir))
        cp = d4j(args.d4j, "export", "-p", "cp.compile", "-w", str(wdir))
        cp_test = d4j(args.d4j, "export", "-p", "cp.test", "-w", str(wdir))
        classpath = os.pathsep.join(
            x for x in [cp.stdout.strip(), cp_test.stdout.strip(),
                        str(args.junit5), args.extra_cp] if x)

        focal_class = methods.get(bug, {}).get("class")
        pkg = None
        if focal_class:
            pkg, _ = find_package_for_class(wdir, focal_class)
        project_pkgs = collect_project_packages(classpath)

        checkout_cache[bug] = dict(workdir=wdir, package=pkg,
                                   classpath=classpath, project_pkgs=project_pkgs)
        print(f"  package={pkg}  #project_pkgs={len(project_pkgs)}  "
              f"cp_len={len(classpath)}")

    for t in tasks:
        info = checkout_cache.get(t["bug"])
        row = dict(model=t["model"], bug=t["bug"], condition=t["condition"],
                   compiles=False, error_category="", detail="", file_shape="")
        if not info:
            row["error_category"] = "checkout_failed"
            results.append(row)
            continue

        source = t["path"].read_text(encoding="utf-8", errors="ignore")
        is_full, declared = detect_shape(source)
        if is_full:
            package, scaffold = build_full_unit(source, info["package"],
                                                info["project_pkgs"], args.strict)
            class_name = declared
        else:
            package = info["package"] or "d4jtest"
            class_name = f"{t['model'].replace('-', '_').replace('.', '_')}_" \
                         f"{t['project']}_{t['num']}_{t['condition']}_Test"
            scaffold = build_scaffold(source, package, class_name,
                                      info["project_pkgs"], args.strict)
        row["file_shape"] = "full_class" if is_full else "fragment"

        tmp_src = args.work / "_compile" / t["model"] / f"{t['bug']}_{t['condition']}"
        if tmp_src.exists():
            shutil.rmtree(tmp_src, ignore_errors=True)
        pkg_dir = tmp_src / package.replace(".", os.sep)
        pkg_dir.mkdir(parents=True, exist_ok=True)
        java_file = pkg_dir / f"{class_name}.java"
        java_file.write_text(scaffold, encoding="utf-8")

        out_dir = tmp_src / "_out"          # javac -d requires an existing dir
        out_dir.mkdir(parents=True, exist_ok=True)
        jc = run([args.javac, "-encoding", "UTF-8", "-cp", info["classpath"],
                  "-d", str(out_dir), str(java_file)])
        if jc.returncode == 0:
            row["compiles"] = True
        else:
            row["error_category"] = classify_error(jc.stderr)
            row["detail"] = jc.stderr.strip().splitlines()[0] if jc.stderr.strip() else ""
            errf = args.out / "errors" / f"{t['model']}_{t['bug']}_{t['condition']}.txt"
            errf.write_text(jc.stderr)
        results.append(row)
        status = "OK " if row["compiles"] else "FAIL"
        print(f"  [{status}] {t['model']:16} {t['bug']:9} {t['condition']}"
              f"  {row['error_category']}")

    csv_path = args.out / "compilability_results.csv"
    with open(csv_path, "w", newline="") as fh:
        w = csv.DictWriter(fh, fieldnames=["model", "bug", "condition",
                                           "compiles", "error_category", "detail",
                                           "file_shape"])
        w.writeheader()
        w.writerows(results)

    per_model = defaultdict(lambda: [0, 0])
    per_model_cond = defaultdict(lambda: [0, 0])
    pattern_by_model = defaultdict(lambda: defaultdict(int))
    for r in results:
        per_model[r["model"]][1] += 1
        per_model[r["model"]][0] += int(r["compiles"])
        key = (r["model"], r["condition"])
        per_model_cond[key][1] += 1
        per_model_cond[key][0] += int(r["compiles"])
        if not r["compiles"]:
            pattern_by_model[r["model"]][r["error_category"]] += 1

    lines = ["# Compilability Results (Defects4J v2.0.0, javac)\n"]
    lines.append(f"- import mode: **{'strict' if args.strict else 'lenient (auto project imports)'}**")
    lines.append(f"- checkout version: **{args.version}** (f=fixed, b=buggy)")
    lines.append(f"- total files: **{len(results)}**\n")
    lines.append("## Compilability rate per model\n")
    lines.append("| Model | Compiled | Total | Rate |")
    lines.append("|---|---|---|---|")
    for m in args.models:
        ok, tot = per_model[m]
        if tot:
            lines.append(f"| {m} | {ok} | {tot} | {ok/tot*100:.1f}% |")
    lines.append("\n## Per condition (C1 zero-shot vs C2 few-shot)\n")
    lines.append("| Model | C1 rate | C2 rate |")
    lines.append("|---|---|---|")
    for m in args.models:
        c1, c2 = per_model_cond[(m, "C1")], per_model_cond[(m, "C2")]
        r1 = f"{c1[0]/c1[1]*100:.1f}%" if c1[1] else "-"
        r2 = f"{c2[0]/c2[1]*100:.1f}%" if c2[1] else "-"
        lines.append(f"| {m} | {r1} | {r2} |")
    lines.append("\n## File shape handling (fragment = wrapped in scaffold, "
                 "full_class = compiled as-is)\n")
    lines.append("| Model | fragment | full_class |")
    lines.append("|---|---|---|")
    for m in args.models:
        shapes = defaultdict(int)
        for r in results:
            if r["model"] == m:
                shapes[r["file_shape"]] += 1
        lines.append(f"| {m} | {shapes['fragment']} | {shapes['full_class']} |")
    lines.append("\n## Failure patterns per model\n")
    all_pats = sorted({p for m in pattern_by_model for p in pattern_by_model[m]})
    lines.append("| Model | " + " | ".join(all_pats) + " |")
    lines.append("|---|" + "|".join(["---"] * len(all_pats)) + "|")
    for m in args.models:
        row = [str(pattern_by_model[m].get(p, 0)) for p in all_pats]
        lines.append(f"| {m} | " + " | ".join(row) + " |")

    (args.out / "compilability_summary.md").write_text("\n".join(lines))
    print(f"\nWrote:\n  {csv_path}\n  {args.out/'compilability_summary.md'}")
    print("Done.")


if __name__ == "__main__":
    main()
