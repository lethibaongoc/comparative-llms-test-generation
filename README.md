# Comparative Evaluation of Free LLMs for Automated Java Unit Test Case Generation

**Topic:** RT-SWT-007  
**Môn:** SWT301 – Research-Based Learning  
**Nhóm:** G7 (lớp SE1944) – SU26  
**Thành viên:** Lê Thị Bảo Ngọc (PL), Đặng Trần Phúc, Phùng Thanh Nhân  
**GV hướng dẫn:** L.T.Q.Chi

## Tiến độ

- [x] RBL-1: Tìm paper (Tuần 3–4)
- [x] RBL-2: Phân tích GAP (Tuần 5)
- [x] RBL-3: Proposal (Tuần 5–6)
- [x] RBL-4: Thực nghiệm (Tuần 7–8)
- [ ] RBL-5: Báo cáo & Trình bày (Tuần 9–10)

Empirical study comparing four LLMs on automated Java unit test generation, benchmarked on **Defects4J v2.0.0**.

## Team

| Name | Student ID | Role |
|---|---|---|
| Lê Thị Bảo Ngọc | SE190619 | Project Lead (PL) — toàn bộ thực nghiệm + Report Writer |
| Đặng Trần Phúc | SE196673 | Data & Ground Truth + Report Writer |
| Phùng Thanh Nhân | SE183107 | Metrics & Stats + Report Writer |

## Research Questions

- **RQ1** — Line Coverage across models
- **RQ2** — Branch Coverage across models
- **RQ3** — Test Smell Density across models
- **RQ4** — Few-shot (k=2) vs. Zero-shot prompting

## Models Under Test

| Model | Provider | Access |
|---|---|---|
| `gemini-2.5-flash` | Google AI Studio | Free tier (1500 req/day) |
| `deepseek-v4-flash` | DeepSeek API | Free (5M tokens) |
| `gpt-5.5` (Instant) *(substitutes `gpt-4o-mini`, retired by OpenAI — see `notes.md` 2026-07-14)* | ChatGPT (free, manual, web UI) | Free |
| `llama-3.3-70b-versatile` | Groq | Free |

## Dataset

- Source: [Defects4J v2.0.0](https://github.com/rjust/defects4j)
- 30 focal methods across 3 projects:
  - **Lang** — bugs 8, 10, 19, 22, 26, 30, 41, 42, 57, 60
  - **Math** — bugs 2, 9, 27, 53, 55, 59, 63, 70, 75, 79
  - **Chart** — bugs 1, 10, 11, 15, 17, 19, 21, 22, 23, 24

## Experimental Design

- Conditions: zero-shot (C1), few-shot k=2 (C2)
- Temperature = 0 for all models
- Pilot sample random seed: 42
- Statistical tests: Kruskal-Wallis + Dunn/Bonferroni + Cliff's delta (RQ1–3); Wilcoxon signed-rank paired (RQ4); α = 0.05

## Current Status

- ✅ Repo initialized, dataset README and project notes committed
- ✅ **Gate E3 passed** — API connectivity verified end-to-end (`scripts/test_api.py`, tested via Groq-hosted Llama-3.3-70B as a pipeline smoke test before wiring in the four target models)
- ✅ **Generation complete** — all 240 tests (30 methods × 2 prompts × 4 LLMs) in `data/generated/`
- ✅ **Stage 1 — compilability** measured against real Defects4J v2.0.0 (`results/compilability/`)
- ✅ **Stage 2 — execution + bug detection** measured on fixed and buggy trees (`results/execution/`, `results/execution-strict/`)
- ✅ **Stage 3 — coverage (JaCoCo)** — RQ1 line + RQ2 branch coverage of the focal class (`results/coverage/`, `results/coverage-strict/`)
- ✅ **Stage 4 — test smells (tsDetect)** — RQ3 test-smell density over all 240 tests (`results/smells/`)
- ✅ **Stage 5 — statistics + figures** — Kruskal-Wallis/Dunn/Cliff's delta (RQ1–3), Wilcoxon (RQ4); `results/stats/`, `figures/`

## Results so far

All 240 generated tests were compiled and run against the **real Defects4J
v2.0.0** checkouts (javac/JUnit 8, headless). All five stages are complete —
compilability, execution/bug detection, coverage (RQ1–RQ2), test smells (RQ3)
and the statistics/figures (RQ1–RQ4).

### Stage 1 — Compilability (lenient, auto project imports)

| Model | Compilable | Rate |
|---|---|---|
| gpt-5.5-instant | 46/60 | **76.7%** |
| gemini | 38/60 | 63.3% |
| llama | 35/60 | 58.3% |
| deepseek | 25/60 | 41.7% |

Detail: `results/compilability/compilability_summary.md` (+ per-file CSV and
per-failure logs). Failures are auto-classified into bug-pattern buckets
(protected/invented constructor, abstract instantiation, missing symbol, …).

### Stage 2 — Execution + bug detection

A test is **valid** if it compiles on the fixed program, runs ≥1 test, and has
0 failures; it **detects** the bug if it is valid *and* fails on the buggy
program (the standard Defects4J fault-revealing criterion: pass on fixed, fail
on buggy). Lenient import mode shown as headline; strict in parentheses.

| Model | Valid (pass on fixed) | Bugs detected /60 | Detection rate |
|---|---|---|---|
| gpt-5.5-instant | 35 | 21 | **35.0%** (strict 26.7%) |
| gemini | 28 | 21 | **35.0%** (strict 26.7%) |
| llama | 15 | 11 | 18.3% (strict 18.3%) |
| deepseek | 15 | 7 | 11.7% (strict 6.7%) |

Detail: `results/execution/` and `results/execution-strict/`
(`execution_summary.md` + per-file CSV + per-run logs).

### Stage 3 — Coverage of the focal class (RQ1 line, RQ2 branch)

JaCoCo, on the fixed program, mean over the tests that actually ran
(compiled + executed); a test that fails assertions still counts the code it
exercised. Lenient headline (strict in parentheses).

| Model | Tests run | Mean line cov (RQ1) | Mean branch cov (RQ2) |
|---|---|---|---|
| gpt-5.5-instant | 46 | **27.5%** (28.8%) | **16.7%** (15.5%) |
| llama | 35 | 26.5% (26.5%) | 13.8% (13.8%) |
| gemini | 38 | 25.2% (28.4%) | 14.8% (15.7%) |
| deepseek | 25 | 22.1% (23.5%) | 9.7% (10.3%) |

Coverage is modest across the board — the tests exercise a small slice of
often-large focal classes (e.g. `FastDatePrinter`), and the denominators
differ because weaker models compile fewer runnable tests. For coverage, few-shot
(C2) *did* help most models (e.g. deepseek line 18.3%→25.1%, branch 5.4%→13.1%),
unlike its mixed effect on bug detection. Detail: `results/coverage/` and
`results/coverage-strict/`.

### Stage 4 — Test-smell density (RQ3)

tsDetect over all 240 generated tests (test smells only need the file to parse,
not compile). Mean smell instances per test class, **excluding IgnoredTest** —
tsDetect flags any non-`public` `@Test` method as ignored, which is a JUnit-4
rule that misfires on these JUnit-5 tests (verified: it fires on 153/154
bare-`void` files and 0/86 `public void` files, i.e. it tracks style, not a
real smell).

| Model | Mean smells/test | Mean distinct smell types |
|---|---|---|
| gpt-5.5-instant | 10.3 | 1.6 |
| llama | 8.2 | 1.6 |
| deepseek | 1.7 | 0.3 |
| gemini | 0.0 | 0.0 |

The most common real smells are Lazy Test (553 instances), Magic Number Test
(334), Eager Test (108) and Assertion Roulette (80). gemini's minimal tests trip
no smell thresholds at all; gpt and llama are the smelliest. **Few-shot (C2)
sharply reduces smells**: gpt 20.6→0.0 and llama 16.0→0.5 smells/test from C1 to
C2 — the exemplars push the models toward cleaner, more idiomatic tests. Detail:
`results/smells/` (`smells_summary.md` + per-file CSV + raw tsDetect output).

### RQ4 preview — few-shot (C2) vs zero-shot (C1)

Bugs detected per condition (out of 30 each), lenient:

| Model | C1 zero-shot | C2 few-shot |
|---|---|---|
| gemini | 12/30 | 9/30 |
| gpt-5.5-instant | 11/30 | 10/30 |
| llama | 5/30 | 6/30 |
| deepseek | 4/30 | 3/30 |

Few-shot (k=2) did **not** consistently beat zero-shot on fault detection — it
helped llama slightly but lowered gemini/gpt/deepseek. A recurring failure
mode across models is inventing null-argument exceptions (NPE/CCE) the real
methods don't throw; see `results/PILOT_RESULTS.md` for per-method oracle notes.

### Statistical tests (α = 0.05) — `results/stats/`

Per-file data (n = 60/model), coverage/smells scored over all 60 files (0 when a
test didn't run). Kruskal-Wallis across the four models, Dunn's post-hoc
(Bonferroni) and Cliff's delta for RQ1–3; Wilcoxon signed-rank (30 bug-paired)
for RQ4.

- **RQ1 line coverage** — models differ (H = 17.1, p = 0.0007). Only
  gpt-5.5-instant > deepseek survives Bonferroni (p = 0.0002, Cliff's δ = 0.42,
  medium); other pairs are small/negligible.
- **RQ2 branch coverage** — models differ (H = 16.3, p = 0.001); again
  gpt-5.5-instant > deepseek is the one significant pair (δ = 0.40, medium).
- **RQ3 test smells** — strongest effect (H = 57.4, p = 2×10⁻¹²). gpt and llama
  are significantly smellier than gemini and deepseek (e.g. gpt vs gemini
  δ = 0.48, large).
- **RQ4 few-shot vs zero-shot** — Wilcoxon: few-shot **significantly reduces
  smells** for gpt (p = 3×10⁻⁶) and llama (p = 8×10⁻⁶), pooled p = 1×10⁻⁹; its
  effect on coverage is not significant except a small gain for deepseek.

Figures for the report are in `figures/` (`fig1_effectiveness`,
`fig2_coverage`, `fig3_smells`, `fig4_fewshot_smells`).

## Reproduction

### Prerequisites

| Requirement | Notes |
|---|---|
| Python 3.9+ | `pip install -r requirements.txt` (generation) and `pip install -r scripts/requirements-analysis.txt` (Stage 5 stats/figures) |
| **Java 8 JDK** | Defects4J v2.0.0 requires it; `javac`/`java` must be Java 8 |
| Maven | only for Stage 4 — `run_smells.sh` builds tsDetect from source once |
| [Defects4J v2.0.0](https://github.com/rjust/defects4j) | `defects4j` must be on `PATH` (`export PATH=$PATH:$HOME/defects4j/framework/bin`) |
| Bash | Stages 1–4 are driven by the `scripts/run_*.sh` wrappers; on Windows run them from WSL |

The wrappers download JUnit 5 + Mockito into `tools/` and materialize the
Defects4J checkouts under `_d4j_work/` on first run. Both directories are
git-ignored and regenerable (several GB).

### API keys

Copy `.env.example` to `.env` and fill in the keys you need — `.env` is
git-ignored and must never be committed.

```bash
cp .env.example .env    # then edit: GEMINI_API_KEY, DEEPSEEK_API_KEY, GROQ_API_KEY, ...
```

### Smoke test (Gate E3)

```bash
pip install openai
export GROQ_API_KEY=your_key_here
python scripts/test_api.py
```

This exercises the full call → retry-on-rate-limit → structured-result pipeline that the full experiment reuses for all four models.

### Full pipeline

Generation is already committed under `data/generated/` (240 files), so the
measurement stages can be re-run without spending API quota. Run in order —
each stage reuses the checkouts the previous one created:

```bash
bash scripts/run_compile.sh              # Stage 1 -> results/compilability/
bash scripts/run_execution.sh            # Stage 2 -> results/execution/
bash scripts/run_coverage.sh             # Stage 3 -> results/coverage/   (RQ1, RQ2)
bash scripts/run_smells.sh               # Stage 4 -> results/smells/     (RQ3)

python scripts/analyze_stats.py --repo .  # Stage 5 -> results/stats/     (RQ1-RQ4)
python scripts/make_figures.py  --repo .  # Stage 5 -> figures/
```

Stages 1–3 also accept `--strict` to re-run in strict import mode, writing to
the parallel `results/*-strict/` directories (the lenient run is the headline
number; strict is reported in parentheses). To regenerate the tests themselves
instead of reusing the committed ones, use `scripts/generate_tests.py` (Gemini)
and `scripts/run_pilot_groq.py` (Groq Llama).

## Quy tắc commit

| Khi nào | Format message |
|---|---|
| Xong một bước trong RBL | `[RBL-1A] add evidence-table - le-thi-bao-ngoc` |
| Cuối mỗi tuần | `[Week 4] merge evidence tables + gap assignment` |
| Sau pilot | `[RBL-4] pilot done - N=50, IAA=0.82` |
| Sau full experiment | `[RBL-4] full experiment - N=500, p=0.003` |
| Sửa lỗi nhỏ | `fix: correct typo in proposal §4` |

Commit thường xuyên — không đợi "xong hẳn" mới commit. Lịch sử commit là bằng
chứng ai làm gì khi nào. Quy tắc này áp dụng từ nay; lịch sử trước đó có từ
trước khi chốt convention nên giữ nguyên.

## Nguyên tắc không được vi phạm

| # | Nguyên tắc | Áp dụng trong repo này |
|---|---|---|
| 1 | **Evidence-based** — mọi quyết định trỏ vào cột cụ thể trong evidence table | Số liệu RQ1–RQ4 đều truy được về `results/*/`+ CSV per-file |
| 2 | **No HARKing** — RQ/metric/threshold chốt trong proposal, không đổi sau khi có data | RQ1–RQ4, α = 0.05, seed = 42 chốt từ đầu (`notes.md`) |
| 3 | **Reproducibility** — ghi model version, hyperparameter, prompt nguyên văn | Model string + temperature = 0 + prompt C1/C2 trong `notes.md`; pipeline ở mục Reproduction |
| 4 | **Pilot bắt buộc** — chạy thử 10–20% trước khi scale | Llama pilot trước full run — `results/PILOT_RESULTS.md` |
| 5 | **Empty response = invalid** — không tự điền kết quả khi API lỗi | File lỗi/rỗng bị đánh invalid, không suy đoán; log ở `results/*/run_logs/` |

## Project Structure

```
├── data/
│   ├── raw/
│   │   ├── README.md            # dataset provenance and focal method list
│   │   └── methods/methods.json # focal method source + metadata
│   └── generated/                # {model}/{Project}-{Bug}_{C1|C2}.java, 4 models × 60 files
│       ├── gemini/  deepseek/  gpt-5.5-instant/  llama/
├── results/
│   ├── compilability/            # Stage 1: summary + per-file CSV + per-failure logs
│   ├── execution/                # Stage 2 (lenient): summary + CSV + per-run logs
│   ├── execution-strict/         # Stage 2 (strict import mode)
│   ├── coverage/                 # Stage 3 (lenient): JaCoCo line/branch + XML
│   ├── coverage-strict/          # Stage 3 (strict import mode)
│   ├── smells/                   # Stage 4: tsDetect test-smell counts (RQ3)
│   ├── stats/                    # Stage 5: unified table + KW/Dunn/Cliff/Wilcoxon
│   ├── PILOT_RESULTS.md          # Llama pilot report (per-method oracle notes)
│   └── archive/                  # superseded/failed run artifacts
├── figures/                      # RQ1-4 charts (populated during analysis)
├── report/
│   └── results-discussion.md     # draft Results + Discussion + Threats to Validity
├── scripts/
│   ├── generate_tests.py         # Gemini test generation (+ experimental OpenRouter Llama path)
│   ├── run_pilot_groq.py         # Groq Llama-3.3-70B pilot run
│   ├── measure_compilability.py  # Stage 1: compile against Defects4J, classify failures
│   ├── measure_execution.py      # Stage 2: run on fixed+buggy, score valid/detected
│   ├── measure_coverage.py       # Stage 3: JaCoCo line/branch of the focal class
│   ├── measure_smells.py         # Stage 4: tsDetect test-smell counts (RQ3)
│   ├── analyze_stats.py          # Stage 5: KW/Dunn/Cliff/Wilcoxon over the merged data
│   ├── make_figures.py           # Stage 5: RQ1-4 report figures
│   ├── requirements-analysis.txt # pandas/scipy/matplotlib/scikit-posthocs
│   ├── run_compile.sh            # one-command wrapper for Stage 1 (WSL)
│   ├── run_execution.sh          # one-command wrapper for Stage 2 (WSL)
│   ├── run_coverage.sh           # one-command wrapper for Stage 3 (WSL)
│   ├── run_smells.sh             # one-command wrapper for Stage 4 (WSL)
│   └── test_api.py               # Gate E3 API connectivity check
├── requirements.txt
├── .env.example
├── notes.md                      # model config, seeds, prompt conditions, decisions log
└── README.md
```

## License

MIT
