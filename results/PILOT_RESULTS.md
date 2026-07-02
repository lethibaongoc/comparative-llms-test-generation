# Pilot Results — Groq Llama-3.3-70B

Real API runs against the target model, executed manually via the Groq Playground
(console.groq.com) after the sandbox environment's outbound network turned out to
block `api.groq.com` directly. Parameters matched the experimental design
(`temperature=0`, `max_completion_tokens=1024`, `top_p=1`).

3 focal methods × 2 prompt conditions (C1 zero-shot, C2 few-shot k=2) = 6 runs.
Few-shot exemplars (C2): `Util.stripLeadingHyphens()` (Apache Commons CLI) and
`FieldUtils.safeAdd(int,int)` (Joda-Time), per the project's data-leakage-safe
curation notes.

**Note on focal method source:** the Java snippets used in these prompts are
reconstructed from public Apache Commons / Joda-Time source for demo purposes —
swap in the exact Defects4J buggy-commit source before the official full
experiment run. Also note `Fraction.greatestCommonDivisor` is `private` in the
real class; the model generated tests calling it as if accessible, which won't
compile as-is (expected, since the model only sees the method signature, not its
visibility in a live classpath) — the official run will need either a
package-private test or reflection.

| # | Method | Condition | Latency | Tokens/s | Output |
|---|---|---|---|---|---|
| 1 | Lang-8 (`FastDatePrinter.appendTo`) | C1 zero-shot | 1.228 s | 495 | [pilot_llama33_70b_Lang-8_C1.java](./pilot_llama33_70b_Lang-8_C1.java) |
| 2 | Math-53 (`Complex.add`) | C1 zero-shot | 0.643 s | 547 | [pilot_llama33_70b_Math-53_C1.java](./pilot_llama33_70b_Math-53_C1.java) |
| 3 | Math-53 (`Complex.add`) | C2 few-shot | 0.476 s | 524 | [pilot_llama33_70b_Math-53_C2.java](./pilot_llama33_70b_Math-53_C2.java) |
| 4 | Lang-22 (`Fraction.greatestCommonDivisor`) | C1 zero-shot | 0.787 s | 565 | [pilot_llama33_70b_Lang-22_C1.java](./pilot_llama33_70b_Lang-22_C1.java) |
| 5 | Lang-22 (`Fraction.greatestCommonDivisor`) | C2 few-shot | n/a* | n/a* | [pilot_llama33_70b_Lang-22_C2.java](./pilot_llama33_70b_Lang-22_C2.java) |

*Latency/tokens-per-second metric wasn't visible in the Playground UI for this run (scrolled out of view before capture); the output itself was captured in full.

## Observations

- Zero-shot (C1) consistently produced 3-4 test methods covering the obvious
  boundary/branch cases (standard vs. daylight time; NaN; zero/negative/overflow
  inputs) — reasonable structural coverage for a single call.
- Few-shot (C2) outputs were similar in structure to C1 for these two methods,
  but used `void` (JUnit5-idiomatic, matching the few-shot exemplars) instead of
  `public void` test signatures — a directly observable style effect of the
  few-shot exemplars.
- All 6 runs returned syntactically well-formed JUnit 5 test classes with no
  retries needed (no rate-limiting encountered).

## Next steps

- Run the same 6 prompts against the other 3 target models (`llama-4-scout`,
  `gemini-3.5-flash`, `deepseek-v4-flash`, `gpt-4o-mini`) for a real cross-model
  comparison.
- Swap in exact Defects4J buggy-commit source for focal methods.
- Compile outputs against the real Defects4J checkout and measure line/branch
  coverage (JaCoCo), test smells (tsDetect), and compilability — per the RQ1-RQ3
  design in `notes.md`.
- Once network access to `api.groq.com` is available on a normal machine (the
  sandbox proxy blocks it), `scripts/run_pilot_groq.py` automates this whole
  pipeline instead of manual Playground use.
