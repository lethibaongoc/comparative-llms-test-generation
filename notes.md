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

\- 2026-07-15: **gemini regenerated to full 30/30 (60/60 files).** Ran

  manually via gemini.google.com web UI (no \`GEMINI_API_KEY\` configured),

  using the same prompt template and the standardized C2 exemplar from the

  2026-07-14 fix. This also resolved the 5 wrong-source-method files

  (Lang-8, Lang-10, Math-2, Math-53, Chart-11) and the 5

  provisional/unverified files (Math-9, Math-27, Math-55, Chart-1,

  Chart-10) flagged earlier — all 30 methods now sourced from

  \`methods.json\`'s fully verified set. \`data/generated/gemini/README.md\`

  updated to drop the stale wrong-source/provisional notes.

\- 2026-07-15: **Audited which deepseek/gpt-5.5-instant C2 files still use

  the pre-2026-07-14 stale exemplar**, using each file's git commit

  timestamp relative to \`d58fdc1\` (the exemplar-standardization commit,

  2026-07-14 10:23:43 +07) as a proxy — there is no per-call prompt log to

  check directly (this is exactly the \`full_api_log.txt\` gap noted above).

  Findings: **all 30/30 gpt-5.5-instant C2 files** were committed before

  \`d58fdc1\`, so — contrary to what this log optimistically said above

  ("gpt-5.5-instant's full 30/30 C2 set is being redone") — none were

  actually redone; and **10/30 deepseek C2 files** (the original batch —

  Lang-8, Lang-10, Math-2, Math-9, Math-27, Math-53, Math-55, Chart-1,

  Chart-10, Chart-11, commit \`6f80a8b\`) also predate the fix, while the

  other 20 deepseek C2 files were generated/redone after it and are fine.

  **Decision: NOT regenerating these 40 files for now** — treating them as

  correct/accepted as-is rather than spending another manual-paste round

  (both models are manual web-UI runs with no reproducible prompt trail).

  This is a known, documented limitation: any RQ1–RQ3 cross-model

  comparison under the **C2 condition specifically** involving

  gpt-5.5-instant, or deepseek's original-10-method subset, should note

  this exemplar inconsistency as a threat to validity in the final report.

  C1 and RQ4 (within-model C1-vs-C2 pairing) are unaffected, same reasoning

  as the 2026-07-14 entry above.

- 2026-07-15: **Reversed the "not regenerating" decision above for

  gpt-5.5-instant.** Regenerated all 30/30 gpt-5.5-instant C2 files

  (manual web UI, chatgpt.com) using the standardized exemplar — commits

  \`b1ae0ff\` (Lang), \`7514a46\` (Math), \`aed8b52\` (Chart). The

  gpt-5.5-instant portion of the C2-exemplar limitation is now closed;

  **only the 10 original deepseek C2 files remain a known limitation**

  (Lang-8, Lang-10, Math-2, Math-9, Math-27, Math-53, Math-55, Chart-1,

  Chart-10, Chart-11 — see 2026-07-15 entry above for why those weren't

  regenerated too). Note: ChatGPT's paste output repeatedly split code

  into markdown fences mid-method (an artifact of the web UI, not the

  model's actual output) — each file was manually reformatted back to

  plain Java before saving; content/logic was not altered.

\- 2026-07-15: **Built the assignment 8.2 deliverables.** Added

  \`scripts/build_full_log.py\`, which produces \`results/full_llm_output.csv\`

  (240 rows = 30 methods × 2 conditions × 4 models, one row per generated

  \`.java\` file, with method/class/condition/model/test-method-count/

  char-count/git-commit) and \`results/full_api_log.txt\` (same 240 entries

  as a chronological log). Since all 4 models were run manually via free

  web UI (no billed API calls, cost = \$0.00 for every entry), there is no

  real per-call timestamp, \`response.model\` object, or token count to

  report — the log uses each file's most recent git commit time as a

  documented, reproducible proxy for "when it was generated" (the project's

  standing convention is to commit immediately after generating/

  regenerating a file), and \`model\` is the output-folder label rather than

  an API response field. Both files' header text says this explicitly so

  it isn't mistaken for real API telemetry.

- 2026-07-17: **Installed Defects4J v2.0.0 for real and fixed two bugs in

  `scripts/measure_compilability.py` that made its output meaningless.**

  Setup: `results/COMPILABILITY_SETUP.md` claimed Defects4J was already

  extracted under `D:\semester5\swt301\research\` per this log — **that was

  false**; it was not installed anywhere, on Windows or in WSL. Now cloned

  at tag `v2.0.0` to `~/defects4j` inside WSL's native filesystem (not

  `/mnt/d` — checkouts across drvfs are slow and hit permission/symlink

  trouble). Verified end-to-end: `defects4j checkout/compile/export` on

  Lang-8f all OK. `run_compile.sh` only needs `defects4j` on PATH, so the

  install location is free.

  Bug 1 (**would have been published as a model result**): the script

  assumed all 240 files are bare method fragments and wrapped every one in

  a scaffold class. But 63 files are complete compilation units the model

  wrote itself — all 60 llama, plus deepseek Lang-8_C1/C2 and Math-2_C2.

  Wrapping those nests a class inside a class, failing all 63 on syntax

  alone: **llama would have scored 0% compilable**, purely as a harness

  artifact. Added `detect_shape()` (a top-level type declaration sits at

  column 0; fragments' own nested/anonymous classes are always indented —

  verified: 0 false positives across all 240) and a `build_full_unit()`

  path that compiles such files as-is. The `file_shape` CSV column records

  which path each file took so the 177/63 split stays auditable.

  Bug 2: `javac -d <dir>` requires the output dir to exist and the script

  never created it, so **every file failed with `error_category=unknown`**

  — meaning this script had never once produced a valid number. Any

  compilability figure predating this entry is invalid and must not be

  reused.

  Method decisions: (a) **javac 8**, not the javac 21 the setup doc named —

  the classpath is Java-8-compiled project code and one compiler avoids

  cross-version noise; override via `JAVAC=`. (b) **Mockito jars

  (mockito-core 4.11.0 + byte-buddy + objenesis) are on `--extra-cp` by

  default** — 16 files use Mockito, which is not a Lang/Math/Chart

  dependency, so without them those fail on a missing jar rather than on

  test quality. Report that this was done. Also fixed the scaffold to add

  `import org.mockito.*` alongside the static import, without which the 5

  files using qualified `Mockito.mock(...)` failed on the harness, not on

  their own merit. (c) In lenient mode, `full_class` files also get the

  project wildcard imports injected, so they are treated the same as

  fragments; Java resolves single-type imports ahead of wildcards, so a

  model's own explicit imports still win. Added an `ambiguous_reference`

  error bucket to catch any case where that assumption breaks — it is a

  harness artifact, not a model error, and should read 0.

  Finding worth reporting (not a bug): `methods.json` labels Lang-8 as

  `FastDatePrinter.appendTo`, but `FastDatePrinter` declares no top-level

  `appendTo` — the method lives in `private static class TimeZoneNameRule`

  (line 1095), and the `source` snippet stored for it is that inner class's

  body. The label is imprecise rather than wrong for harness purposes (the

  `class` field is only used to resolve the package, and `FastDatePrinter`

  resolves fine), but it makes Lang-8 unusually hard: the focal method is

  unreachable through the public API. Only deepseek compiles, by using

  reflection (`Constructor.setAccessible(true)` + `invoke`); llama fails on

  private access and gemini/gpt-5.5 hallucinate `appendTo` onto

  `FastDatePrinter`. Worth a line in the report rather than a fix.


- 2026-07-19: **Stage 2 — ran the 240 tests for execution validity and

  Defects4J bug detection.** New harness `scripts/measure_execution.py` +

  `run_execution.sh` (sibling of the compile harness; imports it for the

  scaffold/full-unit builders so tests run on identical code). Checks out both

  the fixed `f` and buggy `b` trees, compiles+runs each file on both, and

  scores two things: **valid** = compiles on f, runs >=1 test, 0 failures;

  **detected** = valid AND compiles on b AND >=1 failure on b (the standard

  Defects4J fault-revealing criterion: pass on fixed, fail on buggy). Runs in

  both lenient and strict import modes; 90s/file timeout; headless AWT for the

  JFreeChart subjects. Results in `results/execution/` and

  `results/execution-strict/` (per-file CSV + summary + per-run logs).

  Headline (lenient) bug-detection rate: gemini 35.0%, gpt-5.5-instant 35.0%,

  llama 18.3%, deepseek 11.7%. Compiles(f) reproduces the Stage-1 lenient

  compile counts exactly (25/38/46/35), which is the key cross-check. Few-shot

  (C2) did not consistently beat zero-shot (C1) on detection.

  Three harness traps, all fixed and worth a line in the report: (1) the

  console-standalone Vintage engine aborts the whole run because Defects4J

  ships junit 4.11 (<4.12) on cp.test -> restrict to `--include-engine=

  junit-jupiter`; (2) `--details=none` suppresses the very summary block we

  parse -> use `--details=summary`; (3) project packages for lenient import

  injection MUST be scanned per checkout, never cached per project (the

  compiled-test packages differ per bug and between f/b; a shared list injects

  imports for absent packages and fails files on `package_does_not_exist` --

  this briefly collapsed compiles from 144 to 23 before being caught against

  the Stage-1 numbers). The script checkpoints its CSV per file and resumes,

  so the long sweep survives the environment killing background runs.

- 2026-07-19: **Stage 3 — JaCoCo line (RQ1) and branch (RQ2) coverage** of the

  focal class, on the Defects4J fixed trees. New harness

  `scripts/measure_coverage.py` + `run_coverage.sh` reuses the compile/exec

  builders, checkouts, classpath/package caches and resume/checkpoint logic;

  per file it compiles the test on the fixed tree, runs it under

  `-javaagent:jacocoagent.jar` (JaCoCo 0.8.11, Jupiter engine, headless, 90s

  timeout), builds a JaCoCo XML report over the project classes, and reads the

  focal class LINE and BRANCH counters. Coverage is averaged over the tests

  that actually ran (a test that fails assertions still counts the code it

  exercised before failing).

  Lenient means: gpt-5.5-instant line 27.5% / branch 16.7% (46 ran), llama

  26.5% / 13.8% (35), gemini 25.2% / 14.8% (38), deepseek 22.1% / 9.7% (25).

  The "ran" counts equal the Stage-1/2 compile counts exactly (25/38/46/35),

  the key cross-check. Coverage is modest because the focal classes are often

  large and a single generated test touches a small slice. Unlike bug

  detection, few-shot (C2) generally *raised* coverage (deepseek line

  18.3%->25.1%, branch 5.4%->13.1%; gemini and llama up; gpt roughly flat).

  Results in `results/coverage/` and `results/coverage-strict/` (per-file CSV +

  summary + raw JaCoCo XML). Next: RQ3 test smells (tsDetect) and the RQ1-4

  statistics + figures.

- 2026-07-19: **Stage 4 — RQ3 test-smell density via tsDetect**

  (TestSmellDetector). New harness `scripts/measure_smells.py` +

  `run_smells.sh`. tsDetect only needs the test to *parse*, not compile, so

  smells are measured on all 240 files: each is materialised as a parseable

  unit (full_class as-is, fragments wrapped in a minimal scaffold — imports do

  not matter for parsing) with the real focal class pointed to as the

  production file. tsDetect (numerical granularity = smell counts) is run

  **one file at a time**: its Main does not catch per-file JavaParser errors,

  so one lexical error (llama Lang-10 uses an illegal `\?` escape) aborts a

  whole batch — per-file runs isolate the 2 unparseable files (llama Lang-10

  C1/C2) and analyse the other 238.

  IMPORTANT correction: tsDetect flags every non-`public` `@Test` method as

  IgnoredTest, a JUnit-4 rule that misfires on these JUnit-5 tests (fires on

  153/154 bare-`void` files vs 0/86 `public void` files — it tracks style, not

  a smell). IgnoredTest is kept as a column but EXCLUDED from totals/density.

  Results (excl. IgnoredTest), mean smells/test: gpt-5.5-instant 10.3, llama

  8.2, deepseek 1.7, gemini 0.0. Top real smells: Lazy Test (553), Magic

  Number Test (334), Eager Test (108), Assertion Roulette (80). Few-shot (C2)

  sharply cut smells (gpt 20.6->0.0, llama 16.0->0.5) — cleaner idiomatic

  tests. gemini writes minimal tests that trip no smell thresholds. Results in

  `results/smells/`. Remaining: RQ1-4 statistics + figures.
