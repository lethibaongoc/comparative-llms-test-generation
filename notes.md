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

