\# Project Notes — G7 SE1944



\## Models

\- meta-llama/llama-4-scout (OpenRouter, free)

\- gemini-3.5-flash (Google AI Studio, free tier 1500 req/day)

\- deepseek-v4-flash (DeepSeek API, 5M free tokens)

\- gpt-4o-mini (OpenAI API)



\## Random Seed

\- Pilot sample seed: 42



\## Prompt Conditions

\- C1: Zero-shot

\- C2: Few-shot k=2



\## Temperature

\- 0 (all models)



\## Statistical Tests

\- Kruskal-Wallis + Dunn/Bonferroni + Cliff's delta (RQ1-3)

\- Wilcoxon signed-rank paired (RQ4)

\- α = 0.05



\## Decisions Log

\- 2026-06-29: Initialized repo, confirmed 4 models

\- 2026-07-13: Reorganized repo structure — unified generated-test layout

  (\`results/pilot\_llama33\_70b\_\*.java\` moved to \`data/generated/llama/\`,

  prefix stripped to match \`data/generated/gemini/\` convention); archived

  dead \`pilot\_groq\_llama33\_70b.json\` (all-failed early attempt) under

  \`results/archive/\`; removed stray \`-p/\` junk folder; stopped tracking

  \`.idea/\`; added \`requirements.txt\` and \`.env.example\`

\- 2026-07-13: Rebuilt \`data/raw/methods/methods.json\` from 10 to the full

  approved 30-method scope, correcting 5 method-ID mismatches (Lang-8,

  Lang-10, Math-2, Math-53, Chart-11) where the old entry pointed to a

  different method than the one verified in \`results/PILOT\_RESULTS.md\`.

  Chose not to fabricate/reconstruct the missing verified Java source from

  memory (repeat of the Math-53/Lang-22 mistake) — unverified \`source\`

  fields are left \`null\` with \`"verified": false\` pending real Defects4J

  commit lookup. Flagged the 10 \`data/generated/gemini/\` files generated

  against the 5 wrong method IDs as stale, pending regeneration.

