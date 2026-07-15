# Status note (2026-07-15)

**Model:** `gemini-2.5-flash` via manual web UI (gemini.google.com) — no
`GEMINI_API_KEY` configured, so `scripts/generate_tests.py::generate_gemini()`
was not used for this batch.

All 30/30 methods × 2 conditions (C1 zero-shot, C2 few-shot k=2) = 60 files
are now present, regenerated in full from the corrected, fully verified
`data/raw/methods/methods.json` (all 30 entries `"verified": true`). This
replaces the previous partial set (10/30 methods, 5 of which were generated
against the wrong Defects4J source and 5 of which were unverified/provisional
— see git history prior to 2026-07-15 for details).

Prompts used the same template and standardized C2 few-shot exemplar
(`stripLeadingHyphens` / `safeAdd`) as `scripts/generate_tests.py`, to stay
consistent with the deepseek, llama, and gpt-5.5-instant batches.
