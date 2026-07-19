# Statistical Analysis (RQ1-RQ4, alpha = 0.05)

Per-file data: 240 files (4 models x 30 bugs x 2 conditions). Coverage/smells analysed over all 60 files/model (0 for tests that did not run).

## Descriptive statistics (per model, n=60 files)

Coverage 'all' averages over all 60 files (0 when the test did not run); 'ran' averages only over tests that executed.

| Model | Compile% | Valid% | Detect% | Line(all) | Line(ran) | Branch(all) | Branch(ran) | Smells/test |
|---|---|---|---|---|---|---|---|---|
| gpt-5.5-instant | 76.7 | 58.3 | 35.0 | 21.11 | 27.53 | 12.78 | 16.66 | 10.32 |
| gemini | 63.3 | 46.7 | 35.0 | 15.96 | 25.2 | 9.4 | 14.84 | 0.0 |
| llama | 58.3 | 25.0 | 18.3 | 15.45 | 26.49 | 8.04 | 13.78 | 7.97 |
| deepseek | 41.7 | 25.0 | 11.7 | 9.21 | 22.11 | 4.04 | 9.69 | 1.65 |

## RQ1-RQ3 — differences across models (Kruskal-Wallis + Dunn + Cliff's delta)

### RQ1 line coverage (`line_cov`)

- Kruskal-Wallis: H = 17.072, p = 0.0006832 → **significant** (alpha 0.05)
- group means: gpt-5.5-instant 21.11, gemini 15.96, llama 15.45, deepseek 9.21

  Dunn post-hoc (Bonferroni-adjusted p), pairs with p<0.05:
  - gpt-5.5-instant vs deepseek: p = 0.0002327

  Cliff's delta (effect size), all model pairs:
  - gpt-5.5-instant vs gemini: δ = +0.182 (small)
  - gpt-5.5-instant vs llama: δ = +0.193 (small)
  - gpt-5.5-instant vs deepseek: δ = +0.420 (medium)
  - gemini vs llama: δ = +0.021 (negligible)
  - gemini vs deepseek: δ = +0.250 (small)
  - llama vs deepseek: δ = +0.216 (small)

### RQ2 branch coverage (`branch_cov`)

- Kruskal-Wallis: H = 16.308, p = 0.0009803 → **significant** (alpha 0.05)
- group means: gpt-5.5-instant 12.78, gemini 9.40, llama 8.04, deepseek 4.04

  Dunn post-hoc (Bonferroni-adjusted p), pairs with p<0.05:
  - gpt-5.5-instant vs deepseek: p = 0.0003525

  Cliff's delta (effect size), all model pairs:
  - gpt-5.5-instant vs gemini: δ = +0.167 (small)
  - gpt-5.5-instant vs llama: δ = +0.207 (small)
  - gpt-5.5-instant vs deepseek: δ = +0.401 (medium)
  - gemini vs llama: δ = +0.036 (negligible)
  - gemini vs deepseek: δ = +0.231 (small)
  - llama vs deepseek: δ = +0.200 (small)

### RQ3 test-smell density (`smells`)

- Kruskal-Wallis: H = 57.439, p = 2.071e-12 → **significant** (alpha 0.05)
- group means: gpt-5.5-instant 10.32, gemini 0.00, llama 7.97, deepseek 1.65

  Dunn post-hoc (Bonferroni-adjusted p), pairs with p<0.05:
  - gpt-5.5-instant vs gemini: p = 4.731e-09
  - gpt-5.5-instant vs deepseek: p = 1.62e-06
  - gemini vs llama: p = 3.342e-07
  - llama vs deepseek: p = 5.701e-05

  Cliff's delta (effect size), all model pairs:
  - gpt-5.5-instant vs gemini: δ = +0.483 (large)
  - gpt-5.5-instant vs llama: δ = +0.078 (negligible)
  - gpt-5.5-instant vs deepseek: δ = +0.408 (medium)
  - gemini vs llama: δ = -0.450 (medium)
  - gemini vs deepseek: δ = -0.083 (negligible)
  - llama vs deepseek: δ = +0.367 (medium)

## RQ4 — few-shot (C2) vs zero-shot (C1): Wilcoxon signed-rank

Paired by bug (30 pairs/model). Positive median shift = C2 higher. p<0.05 marked **.


### RQ1 line coverage (`line_cov`)

| Scope | median C1 | median C2 | W | p | |
|---|---|---|---|---|---|
| gpt-5.5-instant | 12.20 | 13.75 | 52.0 | 0.408 |  |
| gemini | 5.20 | 7.45 | 27.0 | 0.5937 |  |
| llama | 1.85 | 2.05 | 5.5 | 0.5879 |  |
| deepseek | 0.00 | 0.00 | 0.0 | 0.01172 | ** |
| ALL | 2.20 | 4.90 | 324.5 | 0.2504 |  |

### RQ2 branch coverage (`branch_cov`)

| Scope | median C1 | median C2 | W | p | |
|---|---|---|---|---|---|
| gpt-5.5-instant | 3.90 | 4.35 | 42.0 | 0.3066 |  |
| gemini | 1.15 | 1.55 | 21.0 | 0.859 |  |
| llama | 0.85 | 0.30 | 1.0 | 0.285 |  |
| deepseek | 0.00 | 0.00 | 1.0 | 0.02799 | ** |
| ALL | 0.60 | 1.35 | 297.0 | 0.9932 |  |

### RQ3 test-smell density (`smells`)

| Scope | median C1 | median C2 | W | p | |
|---|---|---|---|---|---|
| gpt-5.5-instant | 18.50 | 0.00 | 0.0 | 2.546e-06 | ** |
| gemini | 0.00 | 0.00 | - | n/a (identical) |  |
| llama | 15.50 | 0.00 | 0.0 | 8.222e-06 | ** |
| deepseek | 0.00 | 0.00 | 7.0 | 0.8927 |  |
| ALL | 0.00 | 0.00 | 91.0 | 1.294e-09 | ** |