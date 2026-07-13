# Status note (2026-07-13)

**Model:** `gemini-2.5-flash` via Google AI Studio API (`scripts/generate_tests.py` → `generate_gemini()`).

These 20 files were generated from an earlier, 10-method version of
`data/raw/methods/methods.json` that has since been corrected (see
`data/raw/README.md` and `results/PILOT_RESULTS.md`).

**5 of the 10 method IDs below were generated against the wrong focal
method** — the source used at generation time did not match the real
Defects4J-verified method for that ID:

| ID | Generated against (wrong) | Should be (verified) |
|---|---|---|
| `Lang-8`  | `FastDateParser.parse`            | `FastDatePrinter.appendTo` |
| `Lang-10` | `FastDateParser.parsePattern`-ish | `FastDateParser.escapeRegex` |
| `Math-2`  | `HypergeometricDistribution.sample` | `HypergeometricDistribution.getNumericalMean` |
| `Math-53` | `Complex.add` (missing NaN check) | `Complex.add` (corrected body) |
| `Chart-11`| `XYPlot.equals`                   | `ShapeUtilities.equal(GeneralPath,GeneralPath)` |

`Lang-8_C1/C2.java`, `Lang-10_C1/C2.java`, `Math-2_C1/C2.java`,
`Math-53_C1/C2.java`, and `Chart-11_C1/C2.java` (10 files) should be
**regenerated** once real verified source is filled into `methods.json` for
these IDs — do not use them as valid study data until then.

The other 10 files (`Math-9`, `Math-27`, `Math-55`, `Chart-1`, `Chart-10` ×
C1/C2) matched the verified method name, but their source was still never
checked against the real commit (`verified: false` in `methods.json`) —
treat them as provisional, not confirmed-correct, until verified.
