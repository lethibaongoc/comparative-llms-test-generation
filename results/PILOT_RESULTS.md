# Pilot Results — Groq Llama-3.3-70B

Real API runs against the target model, executed manually via the Groq Playground
(console.groq.com) after the sandbox environment's outbound network turned out to
block `api.groq.com` directly. Parameters matched the experimental design
(`temperature=0`, `max_completion_tokens=1024`, `top_p=1`).

## Scope

This pilot targets the finalized 30-method benchmark set (10 Apache Commons Lang +
10 Apache Commons Math + 10 JFreeChart bugs, listed below). **All 30 methods are
now complete (60/60 planned runs)** — C1 (zero-shot) and C2 (few-shot k=2) for
every method. Everything outside this 30-method list has been removed from
`results/` to keep the repo aligned with the approved scope.

| Project | Bug IDs (approved scope) | Done |
|---|---|---|
| Lang | 8, 10, 19, 22, 26, 30, 41, 42, 57, 60 | 10/10 |
| Math | 2, 9, 27, 53, 55, 59, 63, 70, 75, 79 | 10/10 |
| Chart | 1, 10, 11, 15, 17, 19, 21, 22, 23, 24 | 10/10 |

Few-shot exemplars (C2): `Util.stripLeadingHyphens()` (Apache Commons CLI) and
`FieldUtils.safeAdd(int,int)` (Joda-Time), per the project's data-leakage-safe
curation notes.

## Methodology / verification note

Every focal method below was verified against the **real Defects4J v2.0.0** bug
data. For Lang and Math, buggy/fixed commit hashes were looked up in
`framework/projects/{Lang,Math}/commit-db` and the exact method source was
pulled directly from the real `apache/commons-lang` / `apache/commons-math`
GitHub history via `git fetch` + `git show <sha>:<path>`. Chart uses **SVN**
revisions in its `commit-db` (via `Vcs::Svn`), which aren't directly
fetchable from the sandbox; those 10 methods were instead verified by
`git log -S"<patch substring>"` pickaxe search against the full history of
`jfree/jfreechart` (and, for Chart-11, `jfree/jcommon`, since
`ShapeUtilities` only moved into the jfreechart repo in a 2017 refactor),
cross-checked commit-by-commit against the corresponding
`framework/projects/Chart/patches/<bug>.src.patch` diff. No focal method was
reconstructed from memory. Math-53 (`Complex.add`) and Lang-22
(`Fraction.greatestCommonDivisor`) needed corrections after an initial check —
memory-reconstructed versions were missing a null-check/exception clause and
a shortcut branch, respectively, that exist in the real fixed source.

## Results table

| # | Method | Condition | Latency | Tokens/s | Output |
|---|---|---|---|---|---|
| 1 | Lang-8 (`FastDatePrinter.appendTo`) | C1 zero-shot | 1.228 s | 495 | [pilot_llama33_70b_Lang-8_C1.java](./pilot_llama33_70b_Lang-8_C1.java) |
| 2 | Lang-8 (`FastDatePrinter.appendTo`) | C2 few-shot | 1.035 s | 580 | [pilot_llama33_70b_Lang-8_C2.java](./pilot_llama33_70b_Lang-8_C2.java) |
| 3 | Lang-10 (`FastDateParser.escapeRegex`) | C1 zero-shot | 0.996 s | 462 | [pilot_llama33_70b_Lang-10_C1.java](./pilot_llama33_70b_Lang-10_C1.java) |
| 4 | Lang-10 (`FastDateParser.escapeRegex`) | C2 few-shot | 0.983 s | 505 | [pilot_llama33_70b_Lang-10_C2.java](./pilot_llama33_70b_Lang-10_C2.java) |
| 5 | Lang-19 (`NumericEntityUnescaper.translate`) | C1 zero-shot | 1.068 s | 535 | [pilot_llama33_70b_Lang-19_C1.java](./pilot_llama33_70b_Lang-19_C1.java) |
| 6 | Lang-19 (`NumericEntityUnescaper.translate`) | C2 few-shot | 1.220 s | 522 | [pilot_llama33_70b_Lang-19_C2.java](./pilot_llama33_70b_Lang-19_C2.java) |
| 7 | Lang-22 (`Fraction.greatestCommonDivisor`) | C1 zero-shot | 1.062 s* | 559 | [pilot_llama33_70b_Lang-22_C1.java](./pilot_llama33_70b_Lang-22_C1.java) |
| 8 | Lang-22 (`Fraction.greatestCommonDivisor`) | C2 few-shot | 0.481 s | 507 | [pilot_llama33_70b_Lang-22_C2.java](./pilot_llama33_70b_Lang-22_C2.java) |
| 9 | Lang-26 (`FastDateFormat.format(Date)`) | C1 zero-shot | 0.787 s | 352 | [pilot_llama33_70b_Lang-26_C1.java](./pilot_llama33_70b_Lang-26_C1.java) |
| 10 | Lang-26 (`FastDateFormat.format(Date)`) | C2 few-shot | 0.509 s | 486 | [pilot_llama33_70b_Lang-26_C2.java](./pilot_llama33_70b_Lang-26_C2.java) |
| 11 | Lang-30 (`StringUtils.containsAny`) | C1 zero-shot | 0.914 s | 476 | [pilot_llama33_70b_Lang-30_C1.java](./pilot_llama33_70b_Lang-30_C1.java) |
| 12 | Lang-30 (`StringUtils.containsAny`) | C2 few-shot | 0.658 s | 463 | [pilot_llama33_70b_Lang-30_C2.java](./pilot_llama33_70b_Lang-30_C2.java) |
| 13 | Lang-41 (`ClassUtils.getPackageName`) | C1 zero-shot | 0.487 s | 712 | [pilot_llama33_70b_Lang-41_C1.java](./pilot_llama33_70b_Lang-41_C1.java) |
| 14 | Lang-41 (`ClassUtils.getPackageName`) | C2 few-shot | 0.382 s | 436 | [pilot_llama33_70b_Lang-41_C2.java](./pilot_llama33_70b_Lang-41_C2.java) |
| 15 | Lang-42 (`Entities.escape`) | C1 zero-shot | 0.818 s | 481 | [pilot_llama33_70b_Lang-42_C1.java](./pilot_llama33_70b_Lang-42_C1.java) |
| 16 | Lang-42 (`Entities.escape`) | C2 few-shot | 0.812 s | 397 | [pilot_llama33_70b_Lang-42_C2.java](./pilot_llama33_70b_Lang-42_C2.java) |
| 17 | Lang-57 (`LocaleUtils.isAvailableLocale`) | C1 zero-shot | 0.286 s | 547 | [pilot_llama33_70b_Lang-57_C1.java](./pilot_llama33_70b_Lang-57_C1.java) |
| 18 | Lang-57 (`LocaleUtils.isAvailableLocale`) | C2 few-shot | 0.345 s | 559 | [pilot_llama33_70b_Lang-57_C2.java](./pilot_llama33_70b_Lang-57_C2.java) |
| 19 | Lang-60 (`StrBuilder.contains(char)`) | C1 zero-shot | 0.643 s | 374 | [pilot_llama33_70b_Lang-60_C1.java](./pilot_llama33_70b_Lang-60_C1.java) |
| 20 | Lang-60 (`StrBuilder.contains(char)`) | C2 few-shot | 0.489 s | 589 | [pilot_llama33_70b_Lang-60_C2.java](./pilot_llama33_70b_Lang-60_C2.java) |
| 21 | Math-2 (`HypergeometricDistribution.getNumericalMean`) | C1 zero-shot | 0.825 s | 658 | [pilot_llama33_70b_Math-2_C1.java](./pilot_llama33_70b_Math-2_C1.java) |
| 22 | Math-2 (`HypergeometricDistribution.getNumericalMean`) | C2 few-shot | 0.896 s | 720 | [pilot_llama33_70b_Math-2_C2.java](./pilot_llama33_70b_Math-2_C2.java) |
| 23 | Math-9 (`Line.revert`) | C1 zero-shot | 0.890 s | 310 | [pilot_llama33_70b_Math-9_C1.java](./pilot_llama33_70b_Math-9_C1.java) |
| 24 | Math-9 (`Line.revert`) | C2 few-shot | 0.707 s | 635 | [pilot_llama33_70b_Math-9_C2.java](./pilot_llama33_70b_Math-9_C2.java) |
| 25 | Math-27 (`Fraction.percentageValue`) | C1 zero-shot | 0.387 s | 660 | [pilot_llama33_70b_Math-27_C1.java](./pilot_llama33_70b_Math-27_C1.java) |
| 26 | Math-27 (`Fraction.percentageValue`) | C2 few-shot | 0.391 s | 674 | [pilot_llama33_70b_Math-27_C2.java](./pilot_llama33_70b_Math-27_C2.java) |
| 27 | Math-53 (`Complex.add`) | C1 zero-shot | 0.603 s | 552 | [pilot_llama33_70b_Math-53_C1.java](./pilot_llama33_70b_Math-53_C1.java) |
| 28 | Math-53 (`Complex.add`) | C2 few-shot | 0.446 s | 540 | [pilot_llama33_70b_Math-53_C2.java](./pilot_llama33_70b_Math-53_C2.java) |
| 29 | Math-55 (`Vector3D.crossProduct`) | C1 zero-shot | 0.847 s | 659 | [pilot_llama33_70b_Math-55_C1.java](./pilot_llama33_70b_Math-55_C1.java) |
| 30 | Math-55 (`Vector3D.crossProduct`) | C2 few-shot | 0.879 s | 651 | [pilot_llama33_70b_Math-55_C2.java](./pilot_llama33_70b_Math-55_C2.java) |
| 31 | Math-59 (`FastMath.max(float,float)`) | C1 zero-shot | 0.763 s | 537 | [pilot_llama33_70b_Math-59_C1.java](./pilot_llama33_70b_Math-59_C1.java) |
| 32 | Math-59 (`FastMath.max(float,float)`) | C2 few-shot | 0.562 s | 488 | [pilot_llama33_70b_Math-59_C2.java](./pilot_llama33_70b_Math-59_C2.java) |
| 33 | Math-63 (`MathUtils.equals(double,double)`) | C1 zero-shot | 0.815 s | 336 | [pilot_llama33_70b_Math-63_C1.java](./pilot_llama33_70b_Math-63_C1.java) |
| 34 | Math-63 (`MathUtils.equals(double,double)`) | C2 few-shot | 0.567 s | 662 | [pilot_llama33_70b_Math-63_C2.java](./pilot_llama33_70b_Math-63_C2.java) |
| 35 | Math-70 (`BisectionSolver.solve` 4-arg) | C1 zero-shot | 0.898 s | 527 | [pilot_llama33_70b_Math-70_C1.java](./pilot_llama33_70b_Math-70_C1.java) |
| 36 | Math-70 (`BisectionSolver.solve` 4-arg) | C2 few-shot | 0.712 s | 435 | [pilot_llama33_70b_Math-70_C2.java](./pilot_llama33_70b_Math-70_C2.java) |
| 37 | Math-75 (`Frequency.getPct(Object)`) | C1 zero-shot | 0.589 s | 338 | [pilot_llama33_70b_Math-75_C1.java](./pilot_llama33_70b_Math-75_C1.java) |
| 38 | Math-75 (`Frequency.getPct(Object)`) | C2 few-shot | 0.721 s | 397 | [pilot_llama33_70b_Math-75_C2.java](./pilot_llama33_70b_Math-75_C2.java) |
| 39 | Math-79 (`MathUtils.distance(int[],int[])`) | C1 zero-shot | 0.737 s | 499 | [pilot_llama33_70b_Math-79_C1.java](./pilot_llama33_70b_Math-79_C1.java) |
| 40 | Math-79 (`MathUtils.distance(int[],int[])`) | C2 few-shot | 0.600 s | 518 | [pilot_llama33_70b_Math-79_C2.java](./pilot_llama33_70b_Math-79_C2.java) |
| 41 | Chart-1 (`AbstractCategoryItemRenderer.getLegendItems`) | C1 zero-shot | 1.224 s | 530 | [pilot_llama33_70b_Chart-1_C1.java](./pilot_llama33_70b_Chart-1_C1.java) |
| 42 | Chart-1 (`AbstractCategoryItemRenderer.getLegendItems`) | C2 few-shot | 0.774 s | 715 | [pilot_llama33_70b_Chart-1_C2.java](./pilot_llama33_70b_Chart-1_C2.java) |
| 43 | Chart-10 (`StandardToolTipTagFragmentGenerator.generateToolTipFragment`) | C1 zero-shot | 0.537 s | 510 | [pilot_llama33_70b_Chart-10_C1.java](./pilot_llama33_70b_Chart-10_C1.java) |
| 44 | Chart-10 (`StandardToolTipTagFragmentGenerator.generateToolTipFragment`) | C2 few-shot | 0.354 s | 528 | [pilot_llama33_70b_Chart-10_C2.java](./pilot_llama33_70b_Chart-10_C2.java) |
| 45 | Chart-11 (`ShapeUtilities.equal(GeneralPath,GeneralPath)`) | C1 zero-shot | 0.912 s | 715 | [pilot_llama33_70b_Chart-11_C1.java](./pilot_llama33_70b_Chart-11_C1.java) |
| 46 | Chart-11 (`ShapeUtilities.equal(GeneralPath,GeneralPath)`) | C2 few-shot | 0.771 s | 647 | [pilot_llama33_70b_Chart-11_C2.java](./pilot_llama33_70b_Chart-11_C2.java) |
| 47 | Chart-15 (`PiePlot.getMaximumExplodePercent`) | C1 zero-shot | 0.844 s | 706 | [pilot_llama33_70b_Chart-15_C1.java](./pilot_llama33_70b_Chart-15_C1.java) |
| 48 | Chart-15 (`PiePlot.getMaximumExplodePercent`) | C2 few-shot | 0.953 s | 570 | [pilot_llama33_70b_Chart-15_C2.java](./pilot_llama33_70b_Chart-15_C2.java) |
| 49 | Chart-17 (`TimeSeries.clone`) | C1 zero-shot | 1.112 s | 524 | [pilot_llama33_70b_Chart-17_C1.java](./pilot_llama33_70b_Chart-17_C1.java) |
| 50 | Chart-17 (`TimeSeries.clone`) | C2 few-shot | 0.810 s | 428 | [pilot_llama33_70b_Chart-17_C2.java](./pilot_llama33_70b_Chart-17_C2.java) |
| 51 | Chart-19 (`CategoryPlot.getDomainAxisIndex`) | C1 zero-shot | 0.638 s | 576 | [pilot_llama33_70b_Chart-19_C1.java](./pilot_llama33_70b_Chart-19_C1.java) |
| 52 | Chart-19 (`CategoryPlot.getDomainAxisIndex`) | C2 few-shot | 1.162 s | 635 | [pilot_llama33_70b_Chart-19_C2.java](./pilot_llama33_70b_Chart-19_C2.java) |
| 53 | Chart-21 (`DefaultBoxAndWhiskerCategoryDataset.updateBounds`) | C1 zero-shot | 1.432 s | 570 | [pilot_llama33_70b_Chart-21_C1.java](./pilot_llama33_70b_Chart-21_C1.java) |
| 54 | Chart-21 (`DefaultBoxAndWhiskerCategoryDataset.updateBounds`) | C2 few-shot | 1.205 s | 578 | [pilot_llama33_70b_Chart-21_C2.java](./pilot_llama33_70b_Chart-21_C2.java) |
| 55 | Chart-22 (`KeyedObjects2D.removeRow`) | C1 zero-shot | 0.571 s | 578 | [pilot_llama33_70b_Chart-22_C1.java](./pilot_llama33_70b_Chart-22_C1.java) |
| 56 | Chart-22 (`KeyedObjects2D.removeRow`) | C2 few-shot | 0.656 s | 439 | [pilot_llama33_70b_Chart-22_C2.java](./pilot_llama33_70b_Chart-22_C2.java) |
| 57 | Chart-23 (`MinMaxCategoryRenderer.equals`) | C1 zero-shot | 0.953 s | 500 | [pilot_llama33_70b_Chart-23_C1.java](./pilot_llama33_70b_Chart-23_C1.java) |
| 58 | Chart-23 (`MinMaxCategoryRenderer.equals`) | C2 few-shot | 0.851 s | 534 | [pilot_llama33_70b_Chart-23_C2.java](./pilot_llama33_70b_Chart-23_C2.java) |
| 59 | Chart-24 (`GrayPaintScale.getPaint`) | C1 zero-shot | 0.838 s | 606 | [pilot_llama33_70b_Chart-24_C1.java](./pilot_llama33_70b_Chart-24_C1.java) |
| 60 | Chart-24 (`GrayPaintScale.getPaint`) | C2 few-shot | 0.967 s | 533 | [pilot_llama33_70b_Chart-24_C2.java](./pilot_llama33_70b_Chart-24_C2.java) |

\* As reported by the Groq Playground UI; likely reflects time-to-first-token
rather than total request latency (all other rows are consistent with total
latency at the reported token throughput, this one is not — recorded as-is
rather than corrected, since we can't recompute it from the UI alone).

Across all 60 runs: average latency **0.78 s** (range 0.29 s–1.43 s), average
throughput **538 tokens/s** (range 310–720 tokens/s). No rate-limiting or
transient server errors were hit during this pilot.

## Correctness observations (test-oracle accuracy, not just compilability)

Since every focal method is the verified real Defects4J fixed source, we also
sanity-checked whether each generated test's *assertions* are actually
correct for that source — beyond "does it look reasonable." Issues found:

**Lang**
- **Lang-8, Lang-22, Lang-27, Lang-41, Lang-53, Lang-63(part), Math-2(part)**:
  see the original 9-method spot-checks below; no new issues found on
  re-inspection.
- **Lang-10 (`FastDateParser.escapeRegex`)**: C1 uses an invalid regex escape
  (`\$` where the real method emits `\[`-style bracket escapes), so its
  assertions don't match the real method's actual output. C2 has a vacuous
  self-comparison assertion (compares the method's output to itself rather
  than to an independently computed expected string), so it can't fail
  regardless of correctness.
- **Lang-19 (`NumericEntityUnescaper.translate`)**: both conditions construct
  malformed hex-entity test inputs (`&#0xHEX;` instead of the correct
  `&#xHEX;`), which changes the expected "characters consumed" counts the
  tests assert against; both also have an off-by-one in their
  "without-trailing-semicolon" test cases.
- **Lang-30 (`StringUtils.containsAny`)**: C1 fully correct. C2 has two tests
  that wrongly expect a `NullPointerException` for a null `searchChars`/
  `searchStr` argument — the real (fixed) method is null-safe and returns
  `false` in those cases.
- **Lang-41 (`ClassUtils.getPackageName`)**: both conditions fully correct.
- **Lang-42 (`Entities.escape`)**: C1 fully correct. C2 has an internal
  self-contradiction — two different test methods assert two different
  expected outputs for the same input string.
- **Lang-57 (`LocaleUtils.isAvailableLocale`)**: both conditions assert
  `NullPointerException` for a null `Locale` argument; this is a plausible
  but unverified assumption about the real method's null-handling and should
  be confirmed against the actual source before trusting it as an oracle.
- **Lang-60 C1**'s `testContains_NullBuffer` wrongly asserts
  `NullPointerException` for `buffer=null,size=0` (the real fixed loop never
  dereferences `buffer` when `size==0`); **Lang-60 C2** correctly uses
  `assertDoesNotThrow` for the same case.
- **Lang-26 C1/C2**: both call `new FastDateFormat(...)` directly, but the
  real class has a protected constructor and a `getInstance()` factory, so
  neither compiles as written; both also rely on weak/tautological
  `assertNotNull`-only assertions rather than checking actual formatted
  output.

**Math**
- **Math-2 C1/C2** (byte-for-byte identical apart from `public`/bare `void`
  style): both assert `1_500_000_000.0` for the mean of
  `HypergeometricDistribution(2e9, 1.5e9, 1.5e9)`, but the correct value
  (`sampleSize * numberOfSuccesses / populationSize` = `1.125e9`) is
  different — a genuine arithmetic mistake by the model, reproduced
  identically in both conditions.
- **Math-9 (`Line.revert`)**: both conditions share a compile-blocking
  structural bug — they attempt to construct an anonymous subclass of
  `Line` in a way that doesn't match any real constructor, so neither
  compiles as written.
- **Math-55 (`Vector3D.crossProduct`)**: C1 includes a "distributivity/
  associativity" test that only happens to pass for the specific vectors
  chosen — cross product is not actually associative in general, so this is
  a fragile/misleading test. C2 is fully correct (with one duplicate test
  method).
- **Math-59 (`FastMath.max(float,float)`)**: both conditions fully correct
  and genuinely fault-revealing.
- **Math-63**: the two conditions disagree with each other on
  `MathUtils.equals(NaN, NaN)` — C1 asserts `true`, C2 asserts `false`. Per
  the real `equals(double,double,int)` implementation (canonical
  `Double.doubleToLongBits(NaN)` bit pattern comparison), `true` is the
  behavior actually exhibited by the fixed code, so C1 is right and C2 is
  wrong here.
- **Math-70 (`BisectionSolver.solve` 4-arg)**: C1 is reasonable but makes a
  few speculative/unverified exception assumptions. C2 is excellent and
  genuinely fault-revealing — it explicitly verifies that the `initial`
  parameter has no effect on the result, which is exactly the injected bug's
  symptom (the buggy overload silently ignores the extra guess parameter).
- **Math-75 (`Frequency.getPct(Object)`)**: both conditions share a wrong
  assumption that passing `null` throws `ClassCastException` (casting `null`
  never throws `CCE` in Java); C2's cross-type comparable test is also
  likely incorrect.
- **Math-79 C1** asserts `Math.sqrt(27 * 3)` for `distance({1,2,3},{4,5,6})`
  (should be `Math.sqrt(27)`), and includes exception expectations for
  dimension-mismatch/empty-array cases that don't actually throw given the
  real (fixed) loop bound `i < p1.length`. **Math-79 C2** has neither issue.

**Chart**
- **Chart-1 (`AbstractCategoryItemRenderer.getLegendItems`)**: C1 correctly
  surfaces the injected off-by-one label bug (0-based series index used to
  build "Series N" labels vs. the dataset's 1-based "Series 1"/"Series 2"
  keys) in both its ascending- and descending-order tests, asserting exact
  label values. C2 never calls `plot.setRenderer(0, renderer)`, so the
  renderer may not be properly registered on the plot before
  `getLegendItems()` is called, and it only asserts item *count*, not label
  values — likely weaker at catching the specific injected bug than C1.
- **Chart-10 (`StandardToolTipTagFragmentGenerator.generateToolTipFragment`)**:
  both conditions correctly assert HTML-escaped output and are genuinely
  fault-revealing against the "forgot to escape" bug.
- **Chart-11 (`ShapeUtilities.equal(GeneralPath,GeneralPath)`)**: both
  conditions include tests with two genuinely *different* `GeneralPath`
  objects asserting `false`, which correctly catches the injected
  "always compares p1 to itself" bug; C2 additionally includes a
  same-reference test that trivially passes either way.
- **Chart-15 (`PiePlot.getMaximumExplodePercent`)**: both conditions
  correctly test the null-dataset case (default-constructed `PiePlot`),
  which is genuinely fault-revealing against the missing-null-check bug.
- **Chart-17 (`TimeSeries.clone`)**: both conditions share a compile-blocking
  bug — they call `new RegularTimePeriod()`, but `RegularTimePeriod` is an
  abstract class in the real library and cannot be instantiated directly.
- **Chart-19 (`CategoryPlot.getDomainAxisIndex`)**: both conditions fully
  correct and genuinely fault-revealing (null-axis case expects
  `IllegalArgumentException`).
- **Chart-21 (`DefaultBoxAndWhiskerCategoryDataset.updateBounds`)**: both
  conditions invent a `BoxAndWhiskerItem` constructor signature (6–7
  primitive/`String` arguments including row/column keys) that doesn't
  match the real 9-argument `Number`-based constructor, so neither compiles
  as written — a direct consequence of `updateBounds()` being a private
  method that can only be exercised indirectly, which the model handled
  reasonably in structure but not in exact API surface.
- **Chart-22 (`KeyedObjects2D.removeRow`)**: both conditions correctly test
  the core fault (unrecognised key should throw `UnknownKeyException`, with
  C2 asserting the exact message text), but both also assume a null key
  throws `NullPointerException`, which is unverified and likely wrong (the
  real path would go through `getRowIndex(null)` returning `-1`, triggering
  `UnknownKeyException` rather than an `NPE`).
- **Chart-23 (`MinMaxCategoryRenderer.equals`)**: both conditions fully
  correct and genuinely fault-revealing (the injected bug is a complete
  absence of an `equals()` override, falling back to reference equality).
- **Chart-24 (`GrayPaintScale.getPaint`)**: both conditions share the same
  arithmetic mistake — for the midpoint case (`value=0.5`, bounds `[0,1]`),
  `(int)(0.5 * 255.0)` truncates to `127`, but both tests hardcode an
  expected value of `128`; the same off-by-one recurs in the custom-bounds
  test case in both conditions.

## Other observations

- Zero-shot (C1) consistently produced 3-7 test methods covering the obvious
  boundary/branch cases (null; zero/negative/overflow inputs; NaN) —
  reasonable structural coverage for a single call.
- Few-shot (C2) outputs mostly switched from `public void testX()` to bare
  `void testX()` test signatures — a directly observable style effect of the
  few-shot exemplars, both of which use bare `void`.
- **Math-2** C1 and C2 are **byte-for-byte identical** apart from the
  `public`/bare `void` style difference — confirmed by direct string
  comparison. At `temperature=0` with a fairly simple/short focal method,
  the few-shot exemplars sometimes make no difference at all to test
  content.
- **Math-27** C1 and C2 are **not** identical, despite both being correct and
  logically equivalent — they use different test method naming conventions.
- Several methods that are private (`Chart-21`'s `updateBounds`) or awkward
  to construct test fixtures for (`Chart-17`'s abstract `RegularTimePeriod`,
  `Chart-21`'s `BoxAndWhiskerItem`, `Math-9`'s abstract `Line` subclassing)
  produced compile-blocking tests in **both** conditions — few-shot
  exemplars didn't help the model infer unfamiliar constructor/class-hierarchy
  details it wasn't shown in the prompt's class context.
- A recurring pattern across several methods (Lang-30, Lang-57, Math-75,
  Chart-22) is the model defaulting to "null argument throws NPE/CCE"
  without justification from the shown source — worth flagging as a
  systematic oracle-correctness risk to check for at full scale, not just a
  one-off mistake.
- All 60 runs completed without rate-limiting or transient server errors in
  the sessions they were generated in.

## Next steps

- Run the same 60 prompts (30 methods × 2 conditions) against the other
  target models for a real cross-model comparison — this pilot has now
  fully validated the prompt/verification/extraction pipeline end-to-end on
  Llama-3.3-70B across all three projects (Lang, Math, Chart) and both
  git- and SVN-tracked Defects4J projects.
- Compile outputs against the real Defects4J checkout and measure line/branch
  coverage (JaCoCo), test smells (tsDetect), and compilability — per the
  RQ1-RQ3 design in `notes.md`. This pilot's manual oracle-correctness
  spot-check (above) should be replaced by that automated compile-and-run
  step at scale. Known likely compile failures to confirm first via actual
  `javac`/`junit` execution: Lang-26 (protected constructor), Math-9
  (abstract `Line` subclass), Chart-17 (abstract `RegularTimePeriod`), and
  Chart-21 (invented `BoxAndWhiskerItem` constructor).
- Once network access to `api.groq.com` is available on a normal machine (the
  sandbox proxy blocks it), `scripts/run_pilot_groq.py` automates this whole
  pipeline instead of manual Playground use — the script will need updating
  with the corrected/verified focal-method sources used here (including the
  Chart project's SVN-to-git-commit mapping notes), and should include
  request pacing/backoff as a precaution for larger-scale runs even though
  none was needed in this pilot.
