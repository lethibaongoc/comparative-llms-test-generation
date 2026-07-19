# Results and Discussion

Draft sections for the SE1944-G7 report. All numbers are produced by the
harnesses in `scripts/` and stored under `results/`; figures are in `figures/`.
Four free LLMs (`gpt-5.5-instant`, `gemini-2.5-flash`, `llama-3.3-70b`,
`deepseek-v4-flash`) were each prompted zero-shot (C1) and few-shot k=2 (C2) on
30 Defects4J v2.0.0 focal methods (10 Apache Commons Lang, 10 Commons Math, 10
JFreeChart), giving 240 generated JUnit 5 tests. Every test was compiled and run
against the real fixed and buggy program versions with `javac`/JUnit 8.

Unless noted, coverage and smell means are taken over **all 60 files per model**
(a test that does not run contributes 0), i.e. the real-world *effectiveness*
of the model's output. Where the quality of runnable tests alone is relevant,
the *ran-only* mean is given as well.

---

## Results

### Compilability and executable validity (prerequisites)

| Model | Compilable | Valid (passes on fixed) |
|---|---|---|
| gpt-5.5-instant | 76.7% (46/60) | 58.3% (35/60) |
| gemini | 63.3% (38/60) | 46.7% (28/60) |
| llama | 58.3% (35/60) | 25.0% (15/60) |
| deepseek | 41.7% (25/60) | 25.0% (15/60) |

`gpt-5.5-instant` produces the most compilable and the most *valid* tests; a
large gap between "compiles" and "valid" for `llama` (58.3%→25.0%) shows that
many of its compilable tests still fail on correct code (over-fitted or wrong
expectations). The dominant compile failures are missing symbols and, for the
weaker models, invented/inaccessible constructors and ambiguous overloads
(`results/compilability/`). See **`figures/fig1_effectiveness.png`**.

### RQ-bug: bug-detection power

A test **detects** the injected bug when it passes on the fixed program and
fails on the buggy one (the Defects4J fault-revealing criterion).

| Model | Detection rate (lenient) | Detection rate (strict) |
|---|---|---|
| gpt-5.5-instant | 35.0% (21/60) | 26.7% |
| gemini | 35.0% (21/60) | 26.7% |
| llama | 18.3% (11/60) | 18.3% |
| deepseek | 11.7% (7/60) | 6.7% |

`gpt-5.5-instant` and `gemini` tie at the top and roughly double `llama` and
`deepseek`. Because a test must first be valid to detect anything, detection
tracks validity closely.

### RQ1 — Line coverage, RQ2 — Branch coverage

JaCoCo coverage of the focal class, over all 60 files (0 when the test did not
run); ran-only means in parentheses. See **`figures/fig2_coverage.png`**.

| Model | Line (all) | Line (ran) | Branch (all) | Branch (ran) |
|---|---|---|---|---|
| gpt-5.5-instant | 21.1% | 27.5% | 12.8% | 16.7% |
| gemini | 16.0% | 25.2% | 9.4% | 14.8% |
| llama | 15.5% | 26.5% | 8.0% | 13.8% |
| deepseek | 9.2% | 22.1% | 4.0% | 9.7% |

Kruskal-Wallis finds the four models' distributions differ for both line
coverage (H = 17.1, p = 0.0007) and branch coverage (H = 16.3, p = 0.001). After
Dunn's post-hoc with Bonferroni correction, the **only** pair that remains
significant is `gpt-5.5-instant` > `deepseek`, for both line (p = 0.0002, Cliff's
δ = 0.42, *medium*) and branch (p = 0.0004, δ = 0.40, *medium*). All other model
pairs are small or negligible effects. Notably, the *ran-only* coverage numbers
are close across models (22–28% line): when a model does produce a runnable
test, its coverage is similar; the model differences in the effectiveness view
are driven mostly by how *often* each model yields a runnable test.

### RQ3 — Test-smell density

tsDetect smell instances per test class, over all files that parse (238/240;
two `llama` files contain an illegal string escape). **IgnoredTest is excluded**
— tsDetect flags any non-`public` `@Test` method as ignored, a JUnit 4 rule that
misfires on these JUnit 5 tests (it fires on 153/154 bare-`void` files and 0/86
`public void` files, i.e. it measures style, not a smell). See
**`figures/fig3_smells.png`**.

| Model | Mean smells/test | Distinct smell types |
|---|---|---|
| gpt-5.5-instant | 10.3 | 1.6 |
| llama | 8.0 | 1.6 |
| deepseek | 1.7 | 0.3 |
| gemini | 0.0 | 0.0 |

This is the strongest cross-model effect (Kruskal-Wallis H = 57.4,
p = 2×10⁻¹²). Dunn/Bonferroni separates {gpt, llama} from {gemini, deepseek}
(e.g. gpt vs gemini δ = 0.48, *large*; gemini vs llama δ = −0.45, *medium*). The
most frequent real smells are **Lazy Test** (553 instances), **Magic Number
Test** (334), **Eager Test** (108) and **Assertion Roulette** (80). `gemini`
writes minimal tests that trip no smell threshold at all — but this cleanliness
coincides with its lower coverage, i.e. it under-tests rather than tests
cleanly-and-thoroughly.

### RQ4 — Few-shot (C2) vs zero-shot (C1)

Wilcoxon signed-rank on the 30 bug-paired differences per model.
See **`figures/fig4_fewshot_smells.png`**.

- **Test smells:** few-shot *sharply reduces* smells for `gpt-5.5-instant`
  (20.6 → 0.0 mean, p = 3×10⁻⁶) and `llama` (15.4 → 0.5, p = 8×10⁻⁶); pooled
  across all models p = 1×10⁻⁹. `deepseek` is the exception (1.0 → 2.3, not
  significant), and `gemini` is unchanged (already 0).
- **Coverage:** few-shot does *not* significantly change line or branch coverage
  for any model except a small gain for `deepseek` (p = 0.01 line, 0.03 branch),
  where the exemplars help it emit more runnable tests.
- **Bug detection:** few-shot does not consistently help — it slightly raised
  `llama` but slightly lowered `gemini`/`gpt`/`deepseek`.

---

## Discussion

**A capability ranking emerges, but it is narrow.** `gpt-5.5-instant` leads on
every effectiveness metric (compilability, validity, coverage, detection) and
`deepseek` trails, yet the only pairwise coverage difference that survives a
conservative correction is the two extremes (gpt vs deepseek). The practical
reading is that model choice matters most at the *ends* of the range; the middle
(`gemini`, `llama`) is statistically indistinguishable on coverage.

**Compilability is the real bottleneck, not per-test quality.** The ran-only
coverage figures (22–28% line) are similar across models, so the effectiveness
gaps come from how often each model produces a test that compiles and passes at
all. Improving these models as test generators is therefore mostly about getting
the API surface right (constructors, accessibility, imports), which is exactly
where the observed compile failures cluster.

**"Clean" can mean "thin."** `gemini` records zero test smells but also the
second-lowest coverage and the fewest assertions; its tests are short and
simple, which avoids smell thresholds without exercising much code. Smell
density should therefore be read alongside coverage, not as a standalone quality
score: `gpt`/`llama` are smelly *because* they write longer, more assertion-heavy
tests that also cover more.

**Few-shot's clearest effect is stylistic, not behavioural.** Two exemplars
sharply cut test smells (RQ4) and shifted models toward idiomatic, bare-`void`
JUnit 5 style, but did **not** significantly move coverage or detection. In
other words, few-shot prompting made the tests *cleaner* without making them
*find more bugs* — a useful but limited return, and an argument that a couple of
exemplars mainly teach form.

**Detection is low in absolute terms.** Even the best models detect only ~35% of
the 30 injected bugs (lenient), and less under strict imports. Combined with the
per-method oracle mistakes catalogued in the pilot (inventing null-argument
exceptions, wrong arithmetic expectations), this suggests free LLMs are, at this
snapshot, useful for *drafting* boundary/branch tests a developer then corrects,
rather than for autonomous fault-finding.

---

## Threats to Validity

**Construct validity.**
- *Coverage granularity.* Coverage is measured on the **focal class** only, not
  the whole program; it captures how well the test exercises the class under
  test but not integration effects.
- *Effectiveness vs quality.* Scoring non-running tests as 0 coverage conflates
  compilability with coverage. We mitigate this by reporting ran-only means
  alongside, which isolates the two signals.
- *Smell tool artifact.* tsDetect's IgnoredTest rule misfires on non-`public`
  JUnit 5 methods; we verified this (153/154 vs 0/86) and excluded it. Other
  tsDetect rules use fixed thresholds (e.g. Magic Number) that may not match
  every project's conventions.
- *Detection definition.* "Fail on buggy" assumes a failure is caused by the
  injected bug. Headless execution and a per-file timeout reduce flakiness, but
  a test could in principle fail on the buggy version for an unrelated reason.
- *Lenient imports.* Auto-injecting project package imports gives a test more
  credit than a raw copy-paste would; the strict-mode numbers bound this and
  move the same direction.

**Internal validity.**
- *Single sample per prompt.* Each test is one generation at temperature 0, so
  we cannot estimate within-model variance across regenerations; a different
  draw could shift individual results.
- *Mixed collection path.* `gpt-5.5-instant` was collected manually via the web
  UI (its API-hosted predecessor `gpt-4o-mini` was retired), unlike the API-based
  models; and it substitutes for the originally planned model, so cross-study
  comparisons to `gpt-4o-mini` are not direct.
- *Ground truth.* Focal-method sources were verified against the real Defects4J
  history (see `results/PILOT_RESULTS.md`), reducing oracle errors, though a few
  methods are hard to reach (private/abstract) and constrain what any test can do.

**External validity.**
- *Scope.* 30 bugs across three mature Java libraries (Lang/Math/Chart), Java 8,
  JUnit 5. Results may not generalize to other domains, languages, larger
  classes, or newer/other models.
- *Point-in-time models.* Free-tier LLMs change frequently; these numbers are a
  snapshot.

**Conclusion validity.**
- *Power.* With n = 60 per model and skewed distributions, non-parametric tests
  are appropriate but low-powered; the conservative Bonferroni correction leaves
  many pairwise comparisons non-significant despite visible mean gaps, so absence
  of a significant pair is not evidence of no difference.
- *Multiple comparisons.* We correct within each test family (Dunn/Bonferroni per
  metric) but not across the four RQs.
