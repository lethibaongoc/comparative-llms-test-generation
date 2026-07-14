# Status note (2026-07-13)

**Model:** `llama-3.3-70b-versatile` via Groq API, run manually through the Groq
Playground (see `results/PILOT_RESULTS.md` for methodology and latency/token
stats).

All 60 files (30 methods × C1/C2) were moved here from `results/` during the
directory reorg — filenames had their `pilot_llama33_70b_` prefix stripped
since the model is now indicated by the folder name, matching the `gemini/`
convention.

**Naming note:** `scripts/generate_tests.py` also has a `generate_llama()`
function, but it targets a *different* model — `meta-llama/llama-4-scout` via
OpenRouter — which is not part of the team's actual 4-model lineup (real
models in use: `gemini-2.5-flash`, `deepseek-v4-flash`, `gpt-5.5` Instant via
ChatGPT (free tier, substitutes `gpt-4o-mini`, retired by OpenAI — see
`notes.md` 2026-07-14), and `llama-3.3-70b-versatile` via Groq). That function's output path was changed
to `data/generated/llama-4-scout-openrouter/` so a rerun of
`generate_tests.py` cannot silently overwrite/mix into this folder's Groq
Llama-3.3-70B data.
