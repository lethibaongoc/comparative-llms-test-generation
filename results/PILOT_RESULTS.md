# Pilot Results — Groq Llama-3.3-70B

Real API runs against the target model, executed manually via the Groq Playground
(console.groq.com) after the sandbox environment's outbound network turned out to
block `api.groq.com` directly. Parameters matched the experimental design
(`temperature=0`, `max_completion_tokens=1024`, `top_p=1`).

6 focal methods × 2 prompt conditions (C1 zero-shot, C2 few-shot k=2) = 12 runs,
all complete. Few-shot exemplars (C2): `Util.stripLeadingHyphens()` (Apache
Commons CLI) and `FieldUtils.safeAdd(int,int)` (Joda-Time), per the project's
data-leakage-safe curation notes.

## Methodology / verification note

An earlier draft of this pilot used focal-method source reconstructed from
memory of the public Apache Commons / Joda-Time codebases. Before finalizing,
every focal method below was re-verified against the **real Defects4J v2.0.0**
bug data: the buggy/fixed commit hashes were looked up in
`framework/projects/{Lang,Math}/commit-db`, and the exact method source was
pulled directly from the real `apache/commons-lang` and `apache/commons-math`
GitHub history via `git fetch` + `git show <sha>:<path>` (not reconstructed
from memory). Two methods (Math-53 `Complex.add`, Lang-22
`Fraction.greatestCommonDivisor`) needed corrections after this check — the
memory-reconstructed versions were missing a null-check/exception clause and a
shortcut branch, respectively, that exist in the real fixed source. Two
methods (Math-63 `MathUtils.equals`, Math-79 `MathUtils.distance`) were newly
added (replacing two originally-picked bugs — Math-75 and Lang-57 — that
turned out to be poor demo candidates: one was a one-line delegate-target
swap with no real logic bug, the other needed extra class context). All 6
methods' focal-method text below is confirmed byte-for-byte against the real
fixed commit.

**Note on `Fraction.greatestCommonDivisor`:** the method is `private` in the
real class; the model generates tests calling it directly, which won't
compile as-is in a real classpath (expected — the model only sees the
signature/body given in the prompt, not classpath visibility). The official
full run will need a package-private test or reflection.

## Results table

| # | Method | Condition | Latency | Tokens/s | Output |
|---|---|---|---|---|---|
| 1 | Lang-8 (`FastDatePrinter.appendTo`) | C1 zero-shot | 1.228 s | 495 | [pilot_llama33_70b_Lang-8_C1.java](./pilot_llama33_70b_Lang-8_C1.java) |
| 2 | Lang-8 (`FastDatePrinter.appendTo`) | C2 few-shot | 1.035 s | 580 | [pilot_llama33_70b_Lang-8_C2.java](./pilot_llama33_70b_Lang-8_C2.java) |
| 3 | Math-53 (`Complex.add`) | C1 zero-shot | 0.603 s | 552 | [pilot_llama33_70b_Math-53_C1.java](./pilot_llama33_70b_Math-53_C1.java) |
| 4 | Math-53 (`Complex.add`) | C2 few-shot | 0.446 s | 540 | [pilot_llama33_70b_Math-53_C2.java](./pilot_llama33_70b_Math-53_C2.java) |
| 5 | Lang-22 (`Fraction.greatestCommonDivisor`) | C1 zero-shot | 1.062 s* | 559 | [pilot_llama33_70b_Lang-22_C1.java](./pilot_llama33_70b_Lang-22_C1.java) |
| 6 | Lang-22 (`Fraction.greatestCommonDivisor`) | C2 few-shot | 0.481 s | 507 | [pilot_llama33_70b_Lang-22_C2.java](./pilot_llama33_70b_Lang-22_C2.java) |
| 7 | Math-63 (`MathUtils.equals(double,double)`) | C1 zero-shot | 0.815 s | 336 | [pilot_llama33_70b_Math-63_C1.java](./pilot_llama33_70b_Math-63_C1.java) |
| 8 | Math-63 (`MathUtils.equals(double,double)`) | C2 few-shot | 0.567 s | 662 | [pilot_llama33_70b_Math-63_C2.java](./pilot_llama33_70b_Math-63_C2.java) |
| 9 | Math-79 (`MathUtils.distance(int[],int[])`) | C1 zero-shot | 0.737 s | 499 | [pilot_llama33_70b_Math-79_C1.java](./pilot_llama33_70b_Math-79_C1.java) |
| 10 | Math-79 (`MathUtils.distance(int[],int[])`) | C2 few-shot | 0.600 s | 518 | [pilot_llama33_70b_Math-79_C2.java](./pilot_llama33_70b_Math-79_C2.java) |
| 11 | Lang-60 (`StrBuilder.contains(char)`) | C1 zero-shot | 0.643 s | 374 | [pilot_llama33_70b_Lang-60_C1.java](./pilot_llama33_70b_Lang-60_C1.java) |
| 12 | Lang-60 (`StrBuilder.contains(char)`) | C2 few-shot | 0.489 s | 589 | [pilot_llama33_70b_Lang-60_C2.java](./pilot_llama33_70b_Lang-60_C2.java) |

\* As reported by the Groq Playground UI; likely reflects time-to-first-token
rather than total request latency (all other rows are consistent with total
latency at the reported token throughput, this one is not — recorded as-is
rather than corrected, since we can't recompute it from the UI alone).

## Correctness observations (test-oracle accuracy, not just compilability)

Since every focal method is now the verified real Defects4J fixed source, we
could also sanity-check whether each generated test's *assertions* are
actually correct for that source — a step beyond "does it look reasonable"
that the earlier draft skipped. Two clear, reproducible issues turned up:

- **Math-79 C1** asserts `Math.sqrt(27 * 3)` for `distance({1,2,3},{4,5,6})`
  (should be `Math.sqrt(27)` — extraneous `* 3`), and includes exception
  expectations for a couple of dimension-mismatch/empty-array cases that
  don't actually throw given the real (fixed) loop bound `i < p1.length`.
  **Math-79 C2** (few-shot) has neither issue — correct formula, and its one
  dimension-mismatch case (`{1,2,3}` vs `{4,5}`) is a case that genuinely
  throws `ArrayIndexOutOfBoundsException`.
- **Math-63**: the two conditions disagree with each other on
  `MathUtils.equals(NaN, NaN)` — C1 asserts `true`, C2 asserts `false`. Per
  the real `equals(double,double,int)` implementation (canonical
  `Double.doubleToLongBits(NaN)` bit pattern, so `NaN` compares equal to
  itself under the ULP check), `true` is the behavior actually exhibited by
  the fixed code — so C1 is right and C2 is wrong here.
- **Lang-60 C1**'s `testContains_NullBuffer` asserts
  `assertThrows(NullPointerException.class, ...)` for `buffer=null,size=0`,
  but the real fixed loop (`for (int i = 0; i < this.size; i++)`) never
  dereferences `buffer` when `size == 0`, so no exception is thrown — this
  assertion is wrong. **Lang-60 C2** correctly uses
  `assertDoesNotThrow(...)` followed by `assertFalse(...)` for the same case.
- Lang-8, Math-53, and Lang-22 outputs had no similar oracle-correctness
  issues found on inspection.

This is a useful, unplanned early data point for RQ2/RQ3: on this small
sample, the few-shot condition (C2) produced fewer incorrect assertions than
zero-shot (C1) in 2 of 3 cases where the two conditions disagreed, but it's
not a clean sweep (Math-63's NaN case flips the other way) — worth deliberately
measuring at full scale rather than assuming a direction.

## Other observations

- Zero-shot (C1) consistently produced 3-5 test methods covering the obvious
  boundary/branch cases (standard vs. daylight time; NaN; zero/negative/overflow
  inputs; nulls) — reasonable structural coverage for a single call.
- Few-shot (C2) outputs mostly switched from `public void testX()` to bare
  `void testX()` test signatures — a directly observable style effect of the
  few-shot exemplars, both of which use bare `void`.
- Few-shot (C2) was faster than zero-shot (C1) on 5 of 6 methods (Math-63 is
  the exception: 0.567 s vs 0.815 s C1, but note C1 also had the lowest
  throughput of the whole set at 336 tok/s) despite the much longer prompt —
  consistent with the model needing less "figuring out" of output format when
  examples are given.
- All 12 runs returned syntactically well-formed JUnit 5 test classes with no
  retries needed (no rate-limiting encountered).

## Next steps

- Run the same 12 prompts against the other 3 target models (`llama-4-scout`,
  `gemini-3.5-flash`, `deepseek-v4-flash`, `gpt-4o-mini`) for a real cross-model
  comparison.
- Compile outputs against the real Defects4J checkout and measure line/branch
  coverage (JaCoCo), test smells (tsDetect), and compilability — per the RQ1-RQ3
  design in `notes.md`. This pilot's manual oracle-correctness spot-check
  (above) should be replaced by that automated compile-and-run step at scale.
- Once network access to `api.groq.com` is available on a normal machine (the
  sandbox proxy blocks it), `scripts/run_pilot_groq.py` automates this whole
  pipeline instead of manual Playground use — the script will need updating
  with the corrected/verified focal-method sources used here.
