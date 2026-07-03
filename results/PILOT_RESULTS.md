# Pilot Results — Groq Llama-3.3-70B

Real API runs against the target model, executed manually via the Groq Playground
(console.groq.com) after the sandbox environment's outbound network turned out to
block `api.groq.com` directly. Parameters matched the experimental design
(`temperature=0`, `max_completion_tokens=1024`, `top_p=1`).

## Scope

This pilot targets the finalized 30-method benchmark set (10 Apache Commons Lang +
10 Apache Commons Math + 10 JFreeChart bugs, listed below). **9 of the 30 methods are
complete (18 of 60 planned runs)** — all from the Lang and Math lists; no Chart bugs
have been run yet. Everything outside this 30-method list has been removed from
`results/` to keep the repo aligned with the approved scope.

| Project | Bug IDs (approved scope) | Done |
|---|---|---|
| Lang | 8, 10, 19, 22, 26, 30, 41, 42, 57, 60 | 8, 22, 26, 60 (4/10) |
| Math | 2, 9, 27, 53, 55, 59, 63, 70, 75, 79 | 2, 27, 53, 63, 79 (5/10) |
| Chart | 1, 10, 11, 15, 17, 19, 21, 22, 23, 24 | none yet (0/10) |

Few-shot exemplars (C2): `Util.stripLeadingHyphens()` (Apache Commons CLI) and
`FieldUtils.safeAdd(int,int)` (Joda-Time), per the project's data-leakage-safe
curation notes.

## Methodology / verification note

Every focal method below was verified against the **real Defects4J v2.0.0** bug
data: the buggy/fixed commit hashes were looked up in
`framework/projects/{Lang,Math}/commit-db`, and the exact method source was
pulled directly from the real `apache/commons-lang` and `apache/commons-math`
GitHub history via `git fetch` + `git show <sha>:<path>` (not reconstructed
from memory). Math-53 (`Complex.add`) and Lang-22
(`Fraction.greatestCommonDivisor`) needed corrections after this check — the
memory-reconstructed versions were missing a null-check/exception clause and
a shortcut branch, respectively, that exist in the real fixed source.

## Results table

| # | Method | Condition | Latency | Tokens/s | Output |
|---|---|---|---|---|---|
| 1 | Lang-8 (`FastDatePrinter.appendTo`) | C1 zero-shot | 1.228 s | 495 | [pilot_llama33_70b_Lang-8_C1.java](./pilot_llama33_70b_Lang-8_C1.java) |
| 2 | Lang-8 (`FastDatePrinter.appendTo`) | C2 few-shot | 1.035 s | 580 | [pilot_llama33_70b_Lang-8_C2.java](./pilot_llama33_70b_Lang-8_C2.java) |
| 3 | Lang-22 (`Fraction.greatestCommonDivisor`) | C1 zero-shot | 1.062 s* | 559 | [pilot_llama33_70b_Lang-22_C1.java](./pilot_llama33_70b_Lang-22_C1.java) |
| 4 | Lang-22 (`Fraction.greatestCommonDivisor`) | C2 few-shot | 0.481 s | 507 | [pilot_llama33_70b_Lang-22_C2.java](./pilot_llama33_70b_Lang-22_C2.java) |
| 5 | Lang-26 (`FastDateFormat.format(Date)`) | C1 zero-shot | 0.787 s | 352 | [pilot_llama33_70b_Lang-26_C1.java](./pilot_llama33_70b_Lang-26_C1.java) |
| 6 | Lang-26 (`FastDateFormat.format(Date)`) | C2 few-shot | 0.509 s | 486 | [pilot_llama33_70b_Lang-26_C2.java](./pilot_llama33_70b_Lang-26_C2.java) |
| 7 | Lang-60 (`StrBuilder.contains(char)`) | C1 zero-shot | 0.643 s | 374 | [pilot_llama33_70b_Lang-60_C1.java](./pilot_llama33_70b_Lang-60_C1.java) |
| 8 | Lang-60 (`StrBuilder.contains(char)`) | C2 few-shot | 0.489 s | 589 | [pilot_llama33_70b_Lang-60_C2.java](./pilot_llama33_70b_Lang-60_C2.java) |
| 9 | Math-2 (`HypergeometricDistribution.getNumericalMean`) | C1 zero-shot | 0.825 s | 658 | [pilot_llama33_70b_Math-2_C1.java](./pilot_llama33_70b_Math-2_C1.java) |
| 10 | Math-2 (`HypergeometricDistribution.getNumericalMean`) | C2 few-shot | 0.896 s | 720 | [pilot_llama33_70b_Math-2_C2.java](./pilot_llama33_70b_Math-2_C2.java) |
| 11 | Math-27 (`Fraction.percentageValue`) | C1 zero-shot | 0.387 s | 660 | [pilot_llama33_70b_Math-27_C1.java](./pilot_llama33_70b_Math-27_C1.java) |
| 12 | Math-27 (`Fraction.percentageValue`) | C2 few-shot | 0.391 s | 674 | [pilot_llama33_70b_Math-27_C2.java](./pilot_llama33_70b_Math-27_C2.java) |
| 13 | Math-53 (`Complex.add`) | C1 zero-shot | 0.603 s | 552 | [pilot_llama33_70b_Math-53_C1.java](./pilot_llama33_70b_Math-53_C1.java) |
| 14 | Math-53 (`Complex.add`) | C2 few-shot | 0.446 s | 540 | [pilot_llama33_70b_Math-53_C2.java](./pilot_llama33_70b_Math-53_C2.java) |
| 15 | Math-63 (`MathUtils.equals(double,double)`) | C1 zero-shot | 0.815 s | 336 | [pilot_llama33_70b_Math-63_C1.java](./pilot_llama33_70b_Math-63_C1.java) |
| 16 | Math-63 (`MathUtils.equals(double,double)`) | C2 few-shot | 0.567 s | 662 | [pilot_llama33_70b_Math-63_C2.java](./pilot_llama33_70b_Math-63_C2.java) |
| 17 | Math-79 (`MathUtils.distance(int[],int[])`) | C1 zero-shot | 0.737 s | 499 | [pilot_llama33_70b_Math-79_C1.java](./pilot_llama33_70b_Math-79_C1.java) |
| 18 | Math-79 (`MathUtils.distance(int[],int[])`) | C2 few-shot | 0.600 s | 518 | [pilot_llama33_70b_Math-79_C2.java](./pilot_llama33_70b_Math-79_C2.java) |

\* As reported by the Groq Playground UI; likely reflects time-to-first-token
rather than total request latency (all other rows are consistent with total
latency at the reported token throughput, this one is not — recorded as-is
rather than corrected, since we can't recompute it from the UI alone).

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
- **Lang-26 C1/C2**: `FastDateFormat` has a protected constructor and a
  `getInstance()` static factory in the real class, but both conditions call
  `new FastDateFormat(...)` directly, which won't compile as-is. Both
  conditions' assertions are also weak/tautological (`assertNotNull` only) —
  the model has no way to predict exact formatted output without seeing
  `applyRules`, so it fell back to existence checks rather than value checks.
- **Math-2 C1/C2** (identical output, see below): the
  `testGetNumericalMean_LargePopulation` case asserts `1_500_000_000.0` for
  `new HypergeometricDistribution(2_000_000_000, 1_500_000_000, 1_500_000_000)`,
  but the correct mean (`sampleSize * numberOfSuccesses / (double) populationSize`
  = `1.5e9 * (1.5e9 / 2e9)`) is `1_125_000_000.0` — a real arithmetic
  mistake in the model's own expected-value calculation, not a data-entry
  slip in this report.
- Lang-8, Math-53, Lang-22, and Math-27 outputs had no similar
  oracle-correctness issues found on inspection.

Across these 9 methods (all with both conditions available), C1 and C2
disagreed on 2 cases (Math-79, Math-63), splitting evenly (1 each) on which
condition was actually correct — not a clean sweep in either direction.
Math-2's identical (and wrong) output on both conditions is a reminder that
few-shot exemplars don't help with a model's own arithmetic errors. Worth
measuring at full scale rather than assuming a direction.

## Other observations

- Zero-shot (C1) consistently produced 3-6 test methods covering the obvious
  boundary/branch cases (NaN; zero/negative/overflow inputs; nulls) —
  reasonable structural coverage for a single call.
- Few-shot (C2) outputs mostly switched from `public void testX()` to bare
  `void testX()` test signatures — a directly observable style effect of the
  few-shot exemplars, both of which use bare `void`.
- **Math-2** C1 and C2 are **byte-for-byte identical** apart from the
  `public`/bare `void` style difference — confirmed by direct string
  comparison of the extracted Playground responses, not assumed. At
  `temperature=0` with a fairly simple/short focal method, the few-shot
  exemplars sometimes make no difference at all to test content.
- **Math-27** C1 and C2 are **not** identical, despite both being correct and
  logically equivalent — they use different test method naming conventions
  (`testSimpleFractionPercentage` vs. `testPercentageValue_SimpleFraction`,
  etc.), not just the `public`/bare `void` difference. Worth noting since an
  earlier draft of this file had mistakenly grouped Math-27 with Math-2 as
  "identical"; re-checked directly against the two files and corrected here.
- All 9 methods' runs completed without rate-limiting in the sessions they
  were generated in. Rate-limiting (`429`) and transient server errors
  (`503`) have been observed after ~30 consecutive requests in a single
  working session in past pilot rounds — worth budgeting deliberate
  pacing/backoff into the full-scale run rather than firing requests
  back-to-back.

## Next steps

- Run the remaining approved methods: **Lang-10, Lang-19, Lang-30, Lang-41,
  Lang-42, Lang-57** (6 remaining Lang bugs); **Math-9, Math-55, Math-59,
  Math-70, Math-75** (5 remaining Math bugs); and all **10 Chart bugs**
  (Chart-1, 10, 11, 15, 17, 19, 21, 22, 23, 24) — none of the Chart set has
  been started yet, so this is the biggest remaining gap.
- Run the same 60 prompts (30 methods × 2 conditions) against the other
  target models for a real cross-model comparison.
- Compile outputs against the real Defects4J checkout and measure line/branch
  coverage (JaCoCo), test smells (tsDetect), and compilability — per the
  RQ1-RQ3 design in `notes.md`. This pilot's manual oracle-correctness
  spot-check (above) should be replaced by that automated compile-and-run
  step at scale. Lang-26 is flagged as a likely compile failure and is a good
  first candidate to confirm via actual `javac`/`junit` execution rather than
  manual reasoning.
- Once network access to `api.groq.com` is available on a normal machine (the
  sandbox proxy blocks it), `scripts/run_pilot_groq.py` automates this whole
  pipeline instead of manual Playground use — the script will need updating
  with the corrected/verified focal-method sources used here, and should
  include request pacing/backoff given the rate-limiting observed in past
  sessions.
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  