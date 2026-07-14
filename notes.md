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

\- 2026-07-14: Filled in the remaining 24 \`null\` \`source\` fields in

  \`methods.json\` with real Defects4J-verified Java source (all 30 methods

  now \`"verified": true\`). Extracted \`defects4j-2.0.0.zip\` (already present

  locally at \`D:\semester5\swt301\research\\`) and ran its

  \`project_repos/get_repos.sh\` to get the official \`commons-lang.git\` /

  \`commons-math.git\` bare repos; used \`git show <fixed-sha>:<path>\` per

  \`commit-db\` for the 16 Lang/Math methods. Chart uses SVN revisions with

  no direct git equivalent in the Defects4J repo bundle, so cloned

  \`jfree/jfreechart\` and \`jfree/jcommon\` fresh from GitHub and used them

  for pickaxe search (\`git log -G\`) plus full-file lookup, cross-checked

  against \`framework/projects/Chart/patches/<id>.src.patch\`. Confirmed

  (via the two already-verified methods, Chart-1/Chart-10) that these

  patch files are BUGGY-relative-to-FIXED in reverse: the \`-\` side is the

  real fixed source, \`+\` is the injected bug — same convention held for

  Lang/Math src.patches (cross-checked against real \`git diff\` between

  the buggy/fixed commit pair for Lang-60).

\- 2026-07-14: **Found and fixed a wrong entry**: \`Chart-10\`

  (\`StandardToolTipTagFragmentGenerator.generateToolTipFragment\`) had a

  non-null \`source\` already, but it was the buggy (unescaped) version, not

  the real fixed one — corrected to use \`ImageMapUtilities.htmlEscape\`.

  Any \`data/generated/*/Chart-10_*.java\` files generated before this date

  were prompted against the wrong source and should be treated as stale.

\- 2026-07-14: Decided to run gpt-4o-mini manually via the free ChatGPT web

  UI (chatgpt.com, logged-in free tier) instead of the paid OpenAI API —

  team has no budget for API billing. Same manual-run precedent as the

  Groq Playground pilot (see \`results/PILOT_RESULTS.md\`).

\- 2026-07-14: **gpt-4o-mini is no longer selectable anywhere in the ChatGPT

  product** (checked on a free-tier ChatGPT account —

  OpenAI has fully retired it from the web UI; the model picker only goes

  back to GPT-5.3/o3 as legacy options). Since the 4th model in the

  comparison can no longer be the literal model named in \`README.md\`,

  substituted **GPT-5.5 (Instant reasoning effort)** run manually through

  ChatGPT (free tier) instead. Output is saved under \`data/generated/gpt-5.5-instant/\`

  (NOT \`data/generated/gpt-4o-mini/\`) to make the substitution obvious and

  avoid mislabeling results as the originally-planned model — same

  transparency precedent as \`llama-4-scout-openrouter/\` not being confused

  with the real Groq Llama-3.3-70B data. \`README.md\`'s "Models Under Test"

  table should be updated to reflect this substitution before the final

  report is written.

\- 2026-07-14: **Found a C2 (few-shot) exemplar mismatch across models.**

  \`scripts/run_pilot_groq.py\`'s \`FEW_SHOT_BLOCK\` (stripLeadingHyphens +

  FieldUtils.safeAdd, k=2) was used for the real Llama-3.3-70B pilot, but

  \`scripts/generate_tests.py\`'s \`FEW_SHOT_EXAMPLE\` (a trivial \`add(a,b)\`)

  was used for gemini/deepseek/gpt-5.5-instant instead — a real confound

  for any RQ1–RQ3 cross-model comparison **under the C2 condition**

  specifically (RQ4's within-model C1-vs-C2 pairing and any C1 cross-model

  comparison are unaffected, since C1 has no exemplar and each model's own

  C2 runs were at least internally consistent). Standardized on llama's

  exact \`FEW_SHOT_BLOCK\` for all models going forward — updated

  \`generate_tests.py\`'s \`FEW_SHOT_EXAMPLE\` to match verbatim. All C2 data

  generated before this date for gemini, deepseek, and gpt-5.5-instant

  used the old \`add()\` exemplar and needs regenerating; gpt-5.5-instant's

  full 30/30 C2 set is being redone, deepseek's C2 set (in progress at the

  time of this fix) is being redone from this point forward, and gemini's

  regen was already pending for the unrelated wrong-source-method issue

  (see the 2026-07-13 entries above) — will pick up the new exemplar when

  that happens.

\- 2026-07-14: **Checked assignment requirement 8.2 ("Chạy LLM toàn bộ") —

  not yet satisfied.** Spec requires \`results/full\_api\_log.txt\` (timestamp,

  \`response.model\`, cost, errors) and \`results/full\_llm\_output.csv\`;

  neither exists yet — data is currently stored as one \`.java\` file per

  method per condition per model (\`data/generated/{model}/{ID}\_{C1|C2}.java\`),

  not as a consolidated CSV/log. Decided: log/CSV will cover all 4 models

  (gemini-2.5-flash, deepseek-v4-flash, Groq Llama-3.3-70B, GPT-5.5

  Instant), cost = \$0 for all four (free tiers / manual web-UI runs, no

  billed API calls); for the 3 manually-run models (llama, deepseek,

  gpt-5.5-instant) there is no real \`response.model\` object, so the CSV's

  "model" field will just be the label used for the output folder, not an

  API response field. Building the actual files is DEFERRED until

  deepseek and gemini are both fully filled in (30/30), to avoid building

  it twice — will do it in one pass once dataset generation is complete.

  Per-file \`git commit\` after each generated \`.java\` file (already the

  standing convention for this project) already satisfies the spec's

  "commit after every large batch, don't lose data" requirement, and all

  data lives in this git repo (no Colab/Kaggle/Drive dependency), so no

  separate sync step is needed once the CSV/log exist.

