#!/usr/bin/env python3
"""
make_figures.py
===============
Render the RQ1-RQ4 figures for the report from results/stats/unified.csv.

Design follows the project's dataviz guidance: bar charts (the data's job is
magnitude comparison across a few models), a fixed categorical hue order from a
colourblind-validated palette, one y-axis per chart, recessive grid, direct
value labels, and a legend whenever >1 series is shown. PNGs land in figures/.
"""

import argparse
from pathlib import Path

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt   # noqa: E402
import numpy as np                # noqa: E402
import pandas as pd               # noqa: E402

# Fixed model order and validated categorical palette (light surface):
# blue / green / magenta / yellow.
MODELS = ["gpt-5.5-instant", "gemini", "llama", "deepseek"]
CAT = ["#2a78d6", "#008300", "#e87ba4", "#eda100"]
INK, MUTED, GRID = "#0b0b0b", "#52514e", "#e6e5e2"


def _style(ax, ylabel, title):
    ax.set_ylabel(ylabel, color=MUTED, fontsize=10)
    ax.set_title(title, color=INK, fontsize=12, fontweight="bold", pad=12,
                 loc="left")
    ax.spines["top"].set_visible(False)
    ax.spines["right"].set_visible(False)
    ax.spines["left"].set_color(GRID)
    ax.spines["bottom"].set_color(GRID)
    ax.tick_params(colors=MUTED, labelsize=9)
    ax.yaxis.grid(True, color=GRID, linewidth=0.8)
    ax.set_axisbelow(True)


def _labels(ax, bars, fmt="{:.0f}"):
    for b in bars:
        h = b.get_height()
        ax.annotate(fmt.format(h), (b.get_x() + b.get_width() / 2, h),
                    ha="center", va="bottom", fontsize=8, color=INK,
                    xytext=(0, 2), textcoords="offset points")


def grouped(ax, series, fmt="{:.0f}"):
    """series: list of (label, values-per-model, color)."""
    n, g = len(MODELS), len(series)
    width = 0.8 / g
    x = np.arange(n)
    for i, (label, vals, color) in enumerate(series):
        off = (i - (g - 1) / 2) * width
        bars = ax.bar(x + off, vals, width * 0.92, label=label, color=color)
        _labels(ax, bars, fmt)
    ax.set_xticks(x)
    ax.set_xticklabels(MODELS, rotation=0, fontsize=9, color=INK)
    if g > 1:
        ax.legend(frameon=False, fontsize=9, loc="upper right", ncol=g)


def agg(df):
    """Per-model aggregates in MODELS order."""
    a = {}
    for m in MODELS:
        s = df[df.model == m]
        a[m] = dict(
            compile=100 * s.compiles.mean(),
            valid=100 * s.valid.mean(),
            detect=100 * s.detected.mean(),
            line=s.line_cov.mean(),
            branch=s.branch_cov.mean(),
            smells=s.smells.mean(),
            smells_c1=s[s.condition == "C1"].smells.mean(),
            smells_c2=s[s.condition == "C2"].smells.mean(),
            line_c1=s[s.condition == "C1"].line_cov.mean(),
            line_c2=s[s.condition == "C2"].line_cov.mean(),
        )
    return a


def col(a, key):
    return [a[m][key] for m in MODELS]


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--repo", type=Path, default=Path("."))
    args = ap.parse_args()
    figdir = args.repo / "figures"
    figdir.mkdir(parents=True, exist_ok=True)
    plt.rcParams["font.family"] = "DejaVu Sans"
    plt.rcParams["figure.facecolor"] = "white"
    plt.rcParams["axes.facecolor"] = "white"

    df = pd.read_csv(args.repo / "results/stats/unified.csv")
    a = agg(df)

    # Fig 1 — effectiveness: compile / valid / detect rate by model.
    fig, ax = plt.subplots(figsize=(7.2, 4.5))
    grouped(ax, [("Compiles", col(a, "compile"), CAT[0]),
                 ("Valid (pass on fixed)", col(a, "valid"), CAT[1]),
                 ("Detects bug", col(a, "detect"), CAT[3])], "{:.0f}")
    ax.set_ylim(0, 100)
    _style(ax, "% of 60 tests", "Effectiveness by model")
    fig.tight_layout(); fig.savefig(figdir / "fig1_effectiveness.png", dpi=150)
    plt.close(fig)

    # Fig 2 — coverage (RQ1/RQ2): line & branch, mean over all 60.
    fig, ax = plt.subplots(figsize=(7.2, 4.5))
    grouped(ax, [("Line (RQ1)", col(a, "line"), CAT[0]),
                 ("Branch (RQ2)", col(a, "branch"), CAT[1])], "{:.1f}")
    _style(ax, "Mean coverage of focal class (%)",
           "RQ1/RQ2 — coverage by model (all 60 tests, 0 if not run)")
    fig.tight_layout(); fig.savefig(figdir / "fig2_coverage.png", dpi=150)
    plt.close(fig)

    # Fig 3 — test-smell density (RQ3), coloured by model identity.
    fig, ax = plt.subplots(figsize=(7.2, 4.5))
    bars = ax.bar(MODELS, col(a, "smells"), 0.6, color=CAT)
    _labels(ax, bars, "{:.1f}")
    _style(ax, "Mean smell instances / test (excl. IgnoredTest)",
           "RQ3 — test-smell density by model")
    fig.tight_layout(); fig.savefig(figdir / "fig3_smells.png", dpi=150)
    plt.close(fig)

    # Fig 4 — RQ4 few-shot effect on smells: C1 vs C2 per model.
    fig, ax = plt.subplots(figsize=(7.2, 4.5))
    grouped(ax, [("C1 zero-shot", col(a, "smells_c1"), CAT[0]),
                 ("C2 few-shot", col(a, "smells_c2"), CAT[3])], "{:.1f}")
    _style(ax, "Mean smell instances / test",
           "RQ4 — few-shot sharply cuts test smells")
    fig.tight_layout(); fig.savefig(figdir / "fig4_fewshot_smells.png", dpi=150)
    plt.close(fig)

    print(f"Wrote 4 figures to {figdir}/")


if __name__ == "__main__":
    main()
