"""
build_full_log.py - Build the consolidated deliverables required by
assignment spec 8.2: results/full_llm_output.csv and results/full_api_log.txt.

All 4 models (gemini-2.5-flash, deepseek-v4-flash, llama-3.3-70b-versatile,
gpt-5.5-instant) were generated via manual web UI runs (no billed API calls,
cost = $0), not via scripted API calls. There is therefore no real per-call
telemetry (exact request timestamp, response.model object, token counts) to
report. Instead this script uses each file's most recent git commit
(timestamp + hash) as a reproducible proxy for "when it was generated",
since the project convention is to commit immediately after each file is
generated/regenerated. This is a documented limitation, not fabricated data.

Usage: python scripts/build_full_log.py
"""
import csv
import json
import re
import subprocess
from pathlib import Path

REPO = Path(__file__).resolve().parent.parent
METHODS_JSON = REPO / "data" / "raw" / "methods" / "methods.json"
GENERATED_DIR = REPO / "data" / "generated"
RESULTS_DIR = REPO / "results"

MODEL_LABELS = {
    "gemini": "gemini-2.5-flash",
    "deepseek": "deepseek-v4-flash",
    "llama": "llama-3.3-70b-versatile",
    "gpt-5.5-instant": "gpt-5.5-instant",
}

FILENAME_RE = re.compile(r"^(?P<method_id>.+)_(?P<condition>C[12])\.java$")


def load_methods():
    with open(METHODS_JSON, "r", encoding="utf-8") as f:
        methods = json.load(f)
    return {m["id"]: m for m in methods}


def load_commit_index():
    """Map repo-relative posix path -> (commit_hash, iso_date), most recent commit only."""
    out = subprocess.run(
        ["git", "log", "--name-only", "--format=@@%H|%aI", "--", "data/generated/"],
        cwd=REPO, capture_output=True, text=True, check=True,
    ).stdout
    index = {}
    current_hash, current_date = None, None
    for line in out.splitlines():
        if line.startswith("@@"):
            current_hash, current_date = line[2:].split("|", 1)
        elif line.strip():
            path = line.strip()
            if path not in index:
                index[path] = (current_hash, current_date)
    return index


def main():
    methods = load_methods()
    commit_index = load_commit_index()

    rows = []
    missing_commit = []
    for model_dir, model_label in MODEL_LABELS.items():
        folder = GENERATED_DIR / model_dir
        for java_file in sorted(folder.glob("*.java")):
            m = FILENAME_RE.match(java_file.name)
            if not m:
                continue
            method_id = m.group("method_id")
            condition = m.group("condition")
            project = method_id.split("-")[0]
            meth = methods.get(method_id, {})
            content = java_file.read_text(encoding="utf-8")
            rel_path = java_file.relative_to(REPO).as_posix()
            commit_hash, committed_at = commit_index.get(rel_path, (None, None))
            if commit_hash is None:
                missing_commit.append(rel_path)
            rows.append({
                "method_id": method_id,
                "project": project,
                "class": meth.get("class", ""),
                "method": meth.get("method", ""),
                "condition": condition,
                "model_folder": model_dir,
                "model_label": model_label,
                "status": "success",
                "cost_usd": "0.00",
                "test_method_count": content.count("@Test"),
                "char_count": len(content),
                "git_commit": commit_hash or "",
                "committed_at": committed_at or "",
                "source_path": rel_path,
            })

    rows.sort(key=lambda r: (r["committed_at"] or "", r["model_folder"], r["method_id"], r["condition"]))

    RESULTS_DIR.mkdir(exist_ok=True)

    csv_path = RESULTS_DIR / "full_llm_output.csv"
    with open(csv_path, "w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=list(rows[0].keys()))
        writer.writeheader()
        writer.writerows(rows)

    log_path = RESULTS_DIR / "full_api_log.txt"
    with open(log_path, "w", encoding="utf-8") as f:
        f.write(
            "# full_api_log.txt — consolidated generation log (assignment spec 8.2)\n"
            "#\n"
            "# All 4 models were run manually via free web UI (Google AI Studio /\n"
            "# gemini.google.com, DeepSeek chat, Groq Playground, ChatGPT), NOT via\n"
            "# billed API calls, so cost is $0.00 for every entry and there is no real\n"
            "# response.model object, token count, or exact per-call timestamp to log.\n"
            "# 'timestamp' below is each file's most recent git commit time (the project\n"
            "# convention is to commit immediately after generating/regenerating a file),\n"
            "# used as a reproducible proxy — not a literal API response timestamp.\n"
            "# model = the label used for the output folder (see README.md \"Models Under\n"
            "# Test\"), not a value read from an API response.\n"
            "#\n"
            f"# Total entries: {len(rows)} (30 methods x 2 conditions x 4 models = 240 expected)\n"
        )
        if missing_commit:
            f.write(f"# WARNING: {len(missing_commit)} file(s) had no matching commit found:\n")
            for p in missing_commit:
                f.write(f"#   {p}\n")
        f.write("#\n")
        for r in rows:
            f.write(
                f"[{r['committed_at']}] model={r['model_label']} "
                f"method={r['method_id']} condition={r['condition']} "
                f"status={r['status']} cost=${r['cost_usd']} "
                f"test_methods={r['test_method_count']} commit={r['git_commit'][:8]}\n"
            )

    print(f"Wrote {len(rows)} rows to {csv_path}")
    print(f"Wrote {len(rows)} log lines to {log_path}")
    if missing_commit:
        print(f"WARNING: {len(missing_commit)} files had no commit found — see log header")


if __name__ == "__main__":
    main()
