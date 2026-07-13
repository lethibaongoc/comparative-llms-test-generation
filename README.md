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
| `gpt-5.5` (Instant) *(substitutes `gpt-4o-mini`, retired by OpenAI — see `notes.md` 2026-07-14)* | ChatGPT Plus (manual, web UI) | Plus subscription |
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
- ⏳ Pilot run and full experiment (240 calls: 30 methods × 2 prompts × 4 LLMs) — in progress

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
│   └── generated/
│       ├── gemini/                     # gemini-2.5-flash outputs, {Project}-{Bug}_{C1|C2}.java
│       └── llama/                      # Groq llama-3.3-70b-versatile outputs, {Project}-{Bug}_{C1|C2}.java
│       # (llama-4-scout-openrouter/ may also appear if scripts/generate_tests.py's
│       #  experimental OpenRouter path is ever run — not part of the 4-model lineup)
├── results/
│   ├── PILOT_RESULTS.md          # pilot run report
│   └── archive/                  # superseded/failed run artifacts
├── figures/                      # RQ1-4 charts (populated during analysis)
├── scripts/
│   ├── generate_tests.py         # Gemini test generation (+ experimental OpenRouter Llama path)
│   ├── run_pilot_groq.py         # Groq Llama-3.3-70B pilot run
│   └── test_api.py               # Gate E3 API connectivity check
├── requirements.txt
├── .env.example
├── notes.md                      # model config, seeds, prompt conditions, decisions log
└── README.md
```

## License

MIT
