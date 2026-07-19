#!/usr/bin/env python3
"""
analyze_stats.py
================
Stage 5: pull the four measured stages into one per-file table and run the
study's statistics (the RQ1-RQ4 design in the README / notes).

Metrics per generated file (model x bug x condition, 240 rows):
  * compiles     - compilability (lenient)
  * valid        - pass on the fixed program (execution stage)
  * detected     - bug-revealing: pass on fixed, fail on buggy
  * line_cov     - JaCoCo line coverage of the focal class (0 if it never ran)
  * branch_cov   - JaCoCo branch coverage (0 if it never ran)
  * smells       - tsDetect total smell instances (IgnoredTest excluded)

Tests (alpha = 0.05):
  * RQ1 line, RQ2 branch, RQ3 smells -- across the 4 models:
      Kruskal-Wallis H (are the model distributions different?), then, if
      significant, Dunn's post-hoc with Bonferroni correction, plus Cliff's
      delta effect sizes for every model pair.
  * RQ4 few-shot (C2) vs zero-shot (C1) -- Wilcoxon signed-rank on the 30
      bug-paired differences, per model and pooled, for each metric.

Coverage/smells are analysed per file over ALL 60 files per model (0 for tests
that did not run), i.e. the real-world effectiveness view -- a model that fails
to produce a runnable test genuinely covers nothing. Ran-only means are also
reported descriptively.

OUTPUT (results/stats/)
  * unified.csv        - the merged per-file table (fed to make_figures.py)
  * descriptive.md/csv - per-model rates and means
  * stats_summary.md   - Kruskal-Wallis, Dunn, Cliff's delta, Wilcoxon
"""

import argparse
from itertools import combinations
from pathlib import Path

import numpy as np
import pandas as pd
from scipy import stats
import scikit_posthocs as sp

MODELS = ["gpt-5.5-instant", "gemini", "llama", "deepseek"]
METRICS = [("line_cov", "RQ1 line coverage"),
           ("branch_cov", "RQ2 branch coverage"),
           ("smells", "RQ3 test-smell density")]


def load_unified(repo: Path) -> pd.DataFrame:
    key = ["model", "bug", "condition"]

    comp = pd.read_csv(repo / "results/compilability/compilability_results.csv")
    comp = comp[key + ["compiles"]].rename(columns={"compiles": "compiles"})

    ex = pd.read_csv(repo / "results/execution/execution_results.csv")
    ex = ex[key + ["pass_f", "bug_detected"]].rename(
        columns={"pass_f": "valid", "bug_detected": "detected"})

    cov = pd.read_csv(repo / "results/coverage/coverage_results.csv")
    cov = cov[key + ["ran", "line_pct", "branch_pct"]].rename(
        columns={"line_pct": "line_cov", "branch_pct": "branch_cov"})

    sm = pd.read_csv(repo / "results/smells/smells_results.csv")
    sm = sm[key + ["parsed", "total_smells"]].rename(
        columns={"total_smells": "smells"})

    df = comp.merge(ex, on=key).merge(cov, on=key).merge(sm, on=key)
    for c in ["compiles", "valid", "detected", "ran", "parsed"]:
        df[c] = df[c].astype(bool)
    return df


def cliffs_delta(a, b):
    """Cliff's delta of a vs b, and its magnitude label."""
    a, b = np.asarray(a, float), np.asarray(b, float)
    # (#a>b - #a<b) / (na*nb), computed via the rank of the pooled sample.
    gt = sum((a[:, None] > b[None, :]).sum(axis=1))
    lt = sum((a[:, None] < b[None, :]).sum(axis=1))
    d = (gt - lt) / (len(a) * len(b))
    ad = abs(d)
    mag = ("negligible" if ad < 0.147 else "small" if ad < 0.33
           else "medium" if ad < 0.474 else "large")
    return d, mag


def kruskal_block(df, metric, label, L):
    groups = [df[df.model == m][metric].values for m in MODELS]
    H, p = stats.kruskal(*groups)
    sig = "significant" if p < 0.05 else "not significant"
    L.append(f"\n### {label} (`{metric}`)\n")
    L.append(f"- Kruskal-Wallis: H = {H:.3f}, p = {p:.4g} → **{sig}** "
             f"(alpha 0.05)")
    means = ", ".join(f"{m} {df[df.model==m][metric].mean():.2f}" for m in MODELS)
    L.append(f"- group means: {means}")

    if p < 0.05:
        dunn = sp.posthoc_dunn(df, val_col=metric, group_col="model",
                               p_adjust="bonferroni")
        L.append("\n  Dunn post-hoc (Bonferroni-adjusted p), pairs with p<0.05:")
        any_pair = False
        for a, b in combinations(MODELS, 2):
            pv = dunn.loc[a, b]
            if pv < 0.05:
                any_pair = True
                L.append(f"  - {a} vs {b}: p = {pv:.4g}")
        if not any_pair:
            L.append("  - (no pair survived Bonferroni correction)")

    L.append("\n  Cliff's delta (effect size), all model pairs:")
    for a, b in combinations(MODELS, 2):
        d, mag = cliffs_delta(df[df.model == a][metric].values,
                              df[df.model == b][metric].values)
        L.append(f"  - {a} vs {b}: δ = {d:+.3f} ({mag})")


def wilcoxon_block(df, L):
    L.append("\n## RQ4 — few-shot (C2) vs zero-shot (C1): Wilcoxon signed-rank\n")
    L.append("Paired by bug (30 pairs/model). Positive median shift = C2 higher."
             " p<0.05 marked **.\n")
    for metric, label in METRICS:
        L.append(f"\n### {label} (`{metric}`)\n")
        L.append("| Scope | median C1 | median C2 | W | p | |")
        L.append("|---|---|---|---|---|---|")
        wide_all_c1, wide_all_c2 = [], []
        for m in MODELS + ["ALL"]:
            sub = df if m == "ALL" else df[df.model == m]
            piv = sub.pivot_table(index=["model", "bug"], columns="condition",
                                  values=metric)
            piv = piv.dropna(subset=["C1", "C2"])
            c1, c2 = piv["C1"].values, piv["C2"].values
            med1, med2 = np.median(c1), np.median(c2)
            if np.allclose(c1, c2):
                star, pcell, wcell = "", "n/a (identical)", "-"
            else:
                try:
                    W, p = stats.wilcoxon(c1, c2, zero_method="wilcox")
                    if np.isnan(p):
                        star, pcell, wcell = "", "n/a (no differences)", "-"
                    else:
                        star = "**" if p < 0.05 else ""
                        pcell, wcell = f"{p:.4g}", f"{W:.1f}"
                except ValueError:
                    star, pcell, wcell = "", "n/a (no differences)", "-"
            L.append(f"| {m} | {med1:.2f} | {med2:.2f} | {wcell} | {pcell} "
                     f"| {star} |")


def descriptive(df, repo, L):
    rows = []
    for m in MODELS:
        s = df[df.model == m]
        ran = s[s.ran]
        rows.append(dict(
            model=m,
            compile_rate=round(100 * s.compiles.mean(), 1),
            valid_rate=round(100 * s.valid.mean(), 1),
            detection_rate=round(100 * s.detected.mean(), 1),
            line_cov_all=round(s.line_cov.mean(), 2),
            branch_cov_all=round(s.branch_cov.mean(), 2),
            line_cov_ran=round(ran.line_cov.mean(), 2) if len(ran) else 0.0,
            branch_cov_ran=round(ran.branch_cov.mean(), 2) if len(ran) else 0.0,
            smells_mean=round(s.smells.mean(), 2),
        ))
    dd = pd.DataFrame(rows)
    dd.to_csv(repo / "results/stats/descriptive.csv", index=False)
    L.append("## Descriptive statistics (per model, n=60 files)\n")
    L.append("Coverage 'all' averages over all 60 files (0 when the test did "
             "not run); 'ran' averages only over tests that executed.\n")
    L.append("| Model | Compile% | Valid% | Detect% | Line(all) | Line(ran) | "
             "Branch(all) | Branch(ran) | Smells/test |")
    L.append("|---|---|---|---|---|---|---|---|---|")
    for r in rows:
        L.append(f"| {r['model']} | {r['compile_rate']} | {r['valid_rate']} | "
                 f"{r['detection_rate']} | {r['line_cov_all']} | "
                 f"{r['line_cov_ran']} | {r['branch_cov_all']} | "
                 f"{r['branch_cov_ran']} | {r['smells_mean']} |")


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--repo", type=Path, default=Path("."))
    args = ap.parse_args()
    out = args.repo / "results/stats"
    out.mkdir(parents=True, exist_ok=True)

    df = load_unified(args.repo)
    df.to_csv(out / "unified.csv", index=False)
    print(f"unified table: {len(df)} rows")

    L = ["# Statistical Analysis (RQ1-RQ4, alpha = 0.05)\n",
         f"Per-file data: {len(df)} files (4 models x 30 bugs x 2 conditions). "
         "Coverage/smells analysed over all 60 files/model (0 for tests that "
         "did not run).\n"]
    descriptive(df, args.repo, L)

    L.append("\n## RQ1-RQ3 — differences across models "
             "(Kruskal-Wallis + Dunn + Cliff's delta)")
    for metric, label in METRICS:
        kruskal_block(df, metric, label, L)

    wilcoxon_block(df, L)

    (out / "stats_summary.md").write_text("\n".join(L))
    print(f"wrote {out/'stats_summary.md'}\nwrote {out/'descriptive.csv'}"
          f"\nwrote {out/'unified.csv'}\nDone.")


if __name__ == "__main__":
    main()
