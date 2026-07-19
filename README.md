# Comparative Evaluation of Free LLMs for Automated Java Unit Test Case Generation

Empirical study comparing four LLMs on automated Java unit test generation, benchmarked on **Defects4J v2.0.0**.

**Course:** SE1944 — Group 7 (G7)

## Team

| Name | Student ID | Role |
|---|---|---|
| Lê Thị Bảo Ngọc | SE190619 | Project Lead + Report Writer |
| Đặng Trần Phúc | SE196673 | Data & Ground Truth + Report Writer |
| Lý Minh Khôi | SE196632 | LLM Runner |
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
- ⏳ RQ1–4 statistics (Kruskal-Wallis, Wilcoxon, Cliff's delta) and figures — next

## Results so far

All 240 generated tests were compiled and run against the **real Defects4J
v2.0.0** checkouts (javac/JUnit 8, headless). Two stages are complete;
coverage and test-smell metrics (RQ1–RQ3) are still to come.

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

## Running the Demo

```bash
pip install openai
export GROQ_API_KEY=your_key_here
python scripts/test_api.py
```

This exercises the full call → retry-on-rate-limit → structured-result pipeline that the full experiment reuses for all four models.

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
│   ├── PILOT_RESULTS.md          # Llama pilot report (per-method oracle notes)
│   └── archive/                  # superseded/failed run artifacts
├── figures/                      # RQ1-4 charts (populated during analysis)
├── scripts/
│   ├── generate_tests.py         # Gemini test generation (+ experimental OpenRouter Llama path)
│   ├── run_pilot_groq.py         # Groq Llama-3.3-70B pilot run
│   ├── measure_compilability.py  # Stage 1: compile against Defects4J, classify failures
│   ├── measure_execution.py      # Stage 2: run on fixed+buggy, score valid/detected
│   ├── measure_coverage.py       # Stage 3: JaCoCo line/branch of the focal class
│   ├── measure_smells.py         # Stage 4: tsDetect test-smell counts (RQ3)
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
