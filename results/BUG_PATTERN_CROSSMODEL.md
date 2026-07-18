# Cross-model confirmation of Llama pilot compile-failure patterns

**Scope:** the 4 compile-blocking patterns flagged in the Llama-3.3-70B pilot
(`results/PILOT_RESULTS.md`), re-checked on the other 3 models
(Gemini, DeepSeek, GPT-5.5-instant) by **static analysis** of the generated
files. Ground-truth `javac` numbers come from running
`scripts/run_compile.sh` (the Cowork sandbox could not host Defects4J).

## Summary matrix (C1 + C2)

| Pattern (focal method) | Llama | Gemini | DeepSeek | GPT-5.5-instant |
|---|---|---|---|---|
| **Lang-26** protected ctor `new FastDateFormat(...)` | ✗ FAIL | ✓ OK (`getInstance()`) | ✗ FAIL (C1, `new`) | ✓ OK (`getInstance()`) |
| **Chart-17** abstract / no-arg ctor | ✗ FAIL (`new RegularTimePeriod()`) | ✗ FAIL (`new TimeSeries()` + `.data`) | ✗ FAIL (`new TimeSeries()` + `.data`) | ✓ OK (concrete `Day`) |
| **Chart-21** invented `BoxAndWhiskerItem` ctor | ✗ FAIL (6–7 arg w/ String) | ✓ OK (9-arg `Number`) | ✗ FAIL (C1: mock + private `updateBounds()`) | ✓ OK (9-arg `Number`) |
| **Math-9** `Line` ctor | ✗ FAIL (anon subclass) | ✗ FAIL (Mockito + fake `Vector`) | ✗ FAIL (`new Line(Point,Point)`) | ✗ FAIL (`Vector2D` vs `Vector3D`) |

✓ = likely compiles (correct API use). ✗ = compile-blocking.
**These are static predictions — replace with the `javac` ground truth from
`results/compilability/` once the script has run.**

## Findings

1. **Invented-constructor (Chart-21) is largely Llama-specific.** Gemini and
   GPT-5.5 both use the correct 9-argument `Number`-based constructor;
   DeepSeek's C2 does too. Only Llama fabricates the signature
   (`new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, "row", "column")`). This
   pattern **does not generalize** across models.

2. **Protected-constructor (Lang-26) partially replicates.** Only DeepSeek (C1)
   repeats `new FastDateFormat(...)`. Gemini and GPT-5.5 avoid it via the
   `getInstance()` factory. Real but model-dependent.

3. **Abstract/no-arg construction (Chart-17) replicates on Gemini + DeepSeek,
   not GPT-5.5.** Gemini and DeepSeek both use the non-existent no-arg
   `new TimeSeries()` and a private `.data` field; GPT-5.5 sidesteps the
   problem with the concrete `Day` subclass and the public `add()` API.

4. **Math-9 fails on all four models** — each fabricates a different `Line`
   construction API. Genuinely hard: `Line.revert()` can't be exercised without
   correctly constructing a `Line`, and no model inferred the real constructor
   from the prompt's class context.

5. **New pattern found: Mockito dependency (15 files).** Across models, 15 files
   call `mock(...)`/`when(...)`. Defects4J's Lang/Math compile classpaths don't
   include Mockito, so these fail unless the jars are added — a distinct
   compile-risk category worth reporting separately.

6. **Extraction caveat.** All 240 files are **bare method fragments** (no
   `package`, `import`, or class declaration), so none compile standalone. The
   harness wraps each in a scaffold class inside the focal package and supplies
   imports. Report the import mode (lenient vs strict) alongside the numbers.

## For the report (threats to validity)

- Compile-failure patterns are **not uniform across models** — avoid
  generalizing Llama-specific failures (esp. Chart-21) to "LLMs in general".
- The **C2 few-shot exemplar inconsistency** for DeepSeek's original 10-method
  subset (documented in `notes.md`, 2026-07-15) overlaps some of these files;
  keep it as a noted confound for any C2 cross-model claim.
- Static predictions here should be replaced by the `javac` ground truth from
  `scripts/run_compile.sh` before the numbers go in the final report.
