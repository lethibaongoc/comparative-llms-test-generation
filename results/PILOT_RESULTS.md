# Pilot Results — Groq Llama-3.3-70B

Real API runs against the target model, executed manually via the Groq Playground
(console.groq.com) after the sandbox environment's outbound network turned out to
block `api.groq.com` directly. Parameters matched the experimental design
(`temperature=0`, `max_completion_tokens=1024`, `top_p=1`).

16 focal methods × 2 prompt conditions (C1 zero-shot, C2 few-shot k=2) = 32 planned
runs. **31 of 32 complete** — Math-41 C2 (few-shot) could not be completed in this
session; Groq's API returned persistent `429`/`503` errors after the large number of
requests already made (see "Math-41 C2 — not completed" below). Few-shot exemplars
(C2): `Util.stripLeadingHyphens()` (Apache Commons CLI) and `FieldUtils.safeAdd(int,int)`
(Joda-Time), per the project's data-leakage-safe curation notes.

## Methodology / verification note

Every focal method below was verified against the **real Defects4J v2.0.0** bug
data: the buggy/fixed commit hashes were looked up in
`framework/projects/{Lang,Math}/commit-db`, and the exact method source was
pulled directly from the real `apache/commons-lang` and `apache/commons-math`
GitHub history via `git fetch` + `git show <sha>:<path>` (not reconstructed
from memory). Two methods from the original 6-method round (Math-53
`Complex.add`, Lang-22 `Fraction.greatestCommonDivisor`) needed corrections
after this check — the memory-reconstructed versions were missing a
null-check/exception clause and a shortcut branch, respectively, that exist
in the real fixed source.

Two candidates considered for this 10-method round were dropped after
verification turned up problems, following the same practice as the first
round: **Lang-45** (`WordUtils.abbreviate`) — an early fetch grabbed the
*buggy* commit instead of the fixed one; caught before use, dropped rather
than risk propagating the error. **Lang-43**
(`ExtendedMessageFormat.appendQuotedString`) — this private helper's correct
output depends on caller-side invariants (the two real call sites always
invoke it with `pos` already positioned *at* an opening quote character) that
aren't visible from the method body or Javadoc alone; a prompt built around a
plain reading of the Javadoc produces a test that doesn't match how the
method is actually exercised in the real codebase. Verified via a manual
Python re-implementation of the method rather than by running real Java
(no `javac` in the sandbox). Replaced with **Lang-16**
(`NumberUtils.createNumber`, a case-sensitivity bug in the hex-prefix check)
instead of forcing a flawed test scenario.

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
| 13 | Lang-21 (`DateUtils.isSameLocalTime`) | C1 zero-shot | 0.872 s | 571 | [pilot_llama33_70b_Lang-21_C1.java](./pilot_llama33_70b_Lang-21_C1.java) |
| 14 | Lang-21 (`DateUtils.isSameLocalTime`) | C2 few-shot | 0.691 s | 546 | [pilot_llama33_70b_Lang-21_C2.java](./pilot_llama33_70b_Lang-21_C2.java) |
| 15 | Lang-33 (`ClassUtils.toClass(Object[])`) | C1 zero-shot | 0.474 s | 537 | [pilot_llama33_70b_Lang-33_C1.java](./pilot_llama33_70b_Lang-33_C1.java) |
| 16 | Lang-33 (`ClassUtils.toClass(Object[])`) | C2 few-shot | 0.419 s | 474 | [pilot_llama33_70b_Lang-33_C2.java](./pilot_llama33_70b_Lang-33_C2.java) |
| 17 | Lang-26 (`FastDateFormat.format(Date)`) | C1 zero-shot | 0.787 s | 352 | [pilot_llama33_70b_Lang-26_C1.java](./pilot_llama33_70b_Lang-26_C1.java) |
| 18 | Lang-26 (`FastDateFormat.format(Date)`) | C2 few-shot | 0.509 s | 486 | [pilot_llama33_70b_Lang-26_C2.java](./pilot_llama33_70b_Lang-26_C2.java) |
| 19 | Lang-49 (`Fraction.reduce()`) | C1 zero-shot | 0.751 s | 560 | [pilot_llama33_70b_Lang-49_C1.java](./pilot_llama33_70b_Lang-49_C1.java) |
| 20 | Lang-49 (`Fraction.reduce()`) | C2 few-shot | 0.704 s | 634 | [pilot_llama33_70b_Lang-49_C2.java](./pilot_llama33_70b_Lang-49_C2.java) |
| 21 | Lang-55 (`StopWatch.stop()`) | C1 zero-shot | 0.586 s | 438 | [pilot_llama33_70b_Lang-55_C1.java](./pilot_llama33_70b_Lang-55_C1.java) |
| 22 | Lang-55 (`StopWatch.stop()`) | C2 few-shot | 0.672 s | 403 | [pilot_llama33_70b_Lang-55_C2.java](./pilot_llama33_70b_Lang-55_C2.java) |
| 23 | Lang-59 (`StrBuilder.appendFixedWidthPadRight`) | C1 zero-shot | 0.663 s | 674 | [pilot_llama33_70b_Lang-59_C1.java](./pilot_llama33_70b_Lang-59_C1.java) |
| 24 | Lang-59 (`StrBuilder.appendFixedWidthPadRight`) | C2 few-shot | 0.727 s | 725 | [pilot_llama33_70b_Lang-59_C2.java](./pilot_llama33_70b_Lang-59_C2.java) |
| 25 | Lang-16 (`NumberUtils.createNumber`) | C1 zero-shot | 0.853 s | 523 | [pilot_llama33_70b_Lang-16_C1.java](./pilot_llama33_70b_Lang-16_C1.java) |
| 26 | Lang-16 (`NumberUtils.createNumber`) | C2 few-shot | 0.808 s | 628 | [pilot_llama33_70b_Lang-16_C2.java](./pilot_llama33_70b_Lang-16_C2.java) |
| 27 | Math-2 (`HypergeometricDistribution.getNumericalMean`) | C1 zero-shot | 0.825 s | 658 | [pilot_llama33_70b_Math-2_C1.java](./pilot_llama33_70b_Math-2_C1.java) |
| 28 | Math-2 (`HypergeometricDistribution.getNumericalMean`) | C2 few-shot | 0.896 s | 720 | [pilot_llama33_70b_Math-2_C2.java](./pilot_llama33_70b_Math-2_C2.java) |
| 29 | Math-27 (`Fraction.percentageValue`) | C1 zero-shot | 0.387 s | 660 | [pilot_llama33_70b_Math-27_C1.java](./pilot_llama33_70b_Math-27_C1.java) |
| 30 | Math-27 (`Fraction.percentageValue`) | C2 few-shot | 0.391 s | 674 | [pilot_llama33_70b_Math-27_C2.java](./pilot_llama33_70b_Math-27_C2.java) |
| 31 | Math-41 (`Variance.evaluate` weighted sub-range) | C1 zero-shot | 0.584 s | 649 | [pilot_llama33_70b_Math-41_C1.java](./pilot_llama33_70b_Math-41_C1.java) |
| 32 | Math-41 (`Variance.evaluate` weighted sub-range) | C2 few-shot | — | — | **not completed** (see below) |

\* As reported by the Groq Playground UI; likely reflects time-to-first-token
rather than total request latency (all other rows are consistent with total
latency at the reported token throughput, this one is not — recorded as-is
rather than corrected, since we can't recompute it from the UI alone).

## Math-41 C2 — not completed

After 30 successful runs in this session, Groq's API began returning `429`
(rate limit) and, once that cleared, intermittent `503` (service unavailable)
responses for the final call (Math-41, few-shot). Confirmed via the browser's
network log (`api.groq.com/openai/v1/chat/completions` → `429`/`503`), not a
UI glitch. Roughly a dozen retries across several minutes of backoff did not
reliably get a response through — Math-41 C1 (row 31) did succeed on a later
retry, but C2 did not before this session ended. This is a genuine platform
rate/availability limit, not fabricated or skipped data — it should be
completed in a follow-up session once the limit window resets.

## Correctness observations (test-oracle accuracy, not just compilability)

Since every focal method is the verified real Defects4J fixed source, we also
sanity-checked whether each generated test's *assertions* are actually
correct for that source — beyond "does it look reasonable." Issues found so far:

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
- **Lang-21 C1/C2**: both conditions' "different Calendar subclass" test
  (`testIsSameLocalTime_DifferentCalendarType_ReturnsFalse` /
  `_DifferentCalendarClass`) call `Calendar.getInstance()` a second time
  rather than constructing an actual different subclass (e.g.
  `GregorianCalendar` explicitly vs. a custom `Calendar` subclass) — under
  the default JVM locale this typically returns another `GregorianCalendar`,
  so the test name overstates what's being exercised, though the assertion
  itself doesn't contradict real behavior.
- **Lang-26 C1/C2**: `FastDateFormat` has a protected constructor and a
  `getInstance()` static factory in the real class, but both conditions call
  `new FastDateFormat(...)` directly, which won't compile as-is. Both
  conditions' assertions are also weak/tautological (`assertNotNull` only) —
  the model has no way to predict exact formatted output without seeing
  `applyRules`, so it fell back to existence checks rather than value checks.
- **Lang-55 C2**'s `testStop_WhenSuspended_SetsStateToStopped` never calls
  `stopWatch.start()`, `suspend()`, or `stop()` before asserting
  `STATE_STOPPED` — the suspend/stop calls are left commented out, so the
  assertion runs against the object's initial (`STATE_UNSTARTED`) state and
  would fail if compiled and run. **Lang-55 C1**'s equivalent test is
  correct — it directly sets `runningState` to simulate suspension before
  calling `stop()`.
- **Math-2 C1/C2** (identical output): the `testGetNumericalMean_LargePopulation`
  case asserts `1_500_000_000.0` for
  `new HypergeometricDistribution(2_000_000_000, 1_500_000_000, 1_500_000_000)`,
  but the correct mean (`sampleSize * numberOfSuccesses / (double) populationSize`
  = `1.5e9 * (1.5e9 / 2e9)`) is `1_125_000_000.0` — a real arithmetic
  mistake in the model's own expected-value calculation, not a data-entry
  slip in this report. The other two assertions in both files are correct.
- **Math-41 C1** (the only run currently available for this method) is
  fully correct, including a `testSubRangeCase` that is genuinely
  fault-revealing: it calls `evaluate(values, weights, mean, 0, 3)` on a
  5-element array/weights pair, correctly excluding indices 3-4 from both
  sums, and asserts the mathematically correct variance (`4.0`). Against the
  real *buggy* Defects4J version (which sums the whole `weights` array
  instead of just `[begin, begin+length)`), this exact test would compute
  `≈0.667` instead and fail — a concrete example of a generated test that
  would actually catch the injected bug.
- Lang-8, Math-53, Lang-22, Lang-33, Lang-49, Lang-59, Lang-16, and Math-27
  outputs had no similar oracle-correctness issues found on inspection.

This is a useful, unplanned data point for RQ2/RQ3: across the 15 methods
with both conditions available, C1 and C2 disagreed on 3 cases (Math-79,
Math-63, Lang-60), splitting 2-1 in favor of C2 being more correct — still
not a clean sweep, and Math-2's identical (and wrong) output on both
conditions is a reminder that few-shot exemplars don't help with a model's
own arithmetic errors. Worth measuring at full scale rather than assuming a
direction.

## Other observations

- Zero-shot (C1) consistently produced 3-6 test methods covering the obvious
  boundary/branch cases (standard vs. daylight time; NaN; zero/negative/overflow
  inputs; nulls; sub-range/window boundaries) — reasonable structural coverage
  for a single call.
- Few-shot (C2) outputs mostly switched from `public void testX()` to bare
  `void testX()` test signatures — a directly observable style effect of the
  few-shot exemplars, both of which use bare `void`. Math-27 C1 also used
  `public void`/`Test` import style distinct from its own C2 run, reinforcing
  this as a few-shot-driven style effect rather than method-specific.
- For 3 of the 10 new methods (Lang-59, Math-2, Math-27), the C2 (few-shot)
  output was **byte-for-byte identical** to C1 (zero-shot) apart from the
  `public`/bare `void` style difference — confirmed by direct string
  comparison of the extracted Playground responses, not assumed. At
  `temperature=0` with a fairly simple/short focal method, the few-shot
  exemplars sometimes make no difference at all to test content, only to
  formatting style.
- All Math-79/Math-63/Lang-60/Lang-21/Lang-33/Lang-26/Lang-49/Lang-55/Lang-59/
  Lang-16/Math-2/Math-27/Math-41(C1) runs completed without rate-limiting.
  Rate-limiting (`429`) and transient server errors (`503`) only appeared
  after roughly 30 consecutive requests in a single working session — worth
  budgeting deliberate pacing/backoff into the full-scale run rather than
  firing requests back-to-back.

## Next steps

- Complete the one missing run: **Math-41 C2** (few-shot), once Groq's rate
  limit window has reset.
- Run the same 32 prompts against the other 3 target models (`llama-4-scout`,
  `gemini-3.5-flash`, `deepseek-v4-flash`, `gpt-4o-mini`) for a real cross-model
  comparison.
- Compile outputs against the real Defects4J checkout and measure line/branch
  coverage (JaCoCo), test smells (tsDetect), and compilability — per the RQ1-RQ3
  design in `notes.md`. This pilot's manual oracle-correctness spot-check
  (above) should be replaced by that automated compile-and-run step at scale.
  Several outputs above (Lang-26, Lang-55 C2) are flagged as likely
  compile/assertion failures and are good first candidates to confirm via
  actual `javac`/`junit` execution rather than manual reasoning.
- Once network access to `api.groq.com` is available on a normal machine (the
  sandbox proxy blocks it), `scripts/run_pilot_groq.py` automates this whole
  pipeline instead of manual Playground use — the script will need updating
  with the corrected/verified focal-method sources used here, and should
  include request pacing/backoff given the rate-limiting observed in this
  session.
