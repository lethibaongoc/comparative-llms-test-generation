\# Dataset



\- Source: Defects4J v2.0.0 (https://github.com/rjust/defects4j)

\- Projects: Lang, Math, Chart

\- Focal methods: 30 total

&#x20; - Lang: bugs 8,10,19,22,26,30,41,42,57,60

&#x20; - Math: bugs 2,9,27,53,55,59,63,70,75,79

&#x20; - Chart: bugs 1,10,11,15,17,19,21,22,23,24

\- Retrieved: 2026-06-29

\- License: Defects4J is available for research use



\## methods.json status (2026-07-13)

\- `methods/methods.json` now lists all 30 approved IDs with the class/method

  names verified in `results/PILOT_RESULTS.md`'s methodology (real Defects4J

  source pulled via `git show`/pickaxe search against `apache/commons-lang`,

  `apache/commons-math`, `jfree/jfreechart`).

\- Most `source` fields are intentionally `null` (`"verified": false`) — the

  actual verified Java source was never saved as structured data and must be

  re-fetched from the real commits before `scripts/generate_tests.py` is run

  for those IDs. A handful of simple methods kept a plausible placeholder

  source carried over from an earlier draft, but those are also marked

  `"verified": false` since they were never checked against the real commit

  either — do not treat any entry here as ground truth until `verified: true`.

\- See `results/PILOT_RESULTS.md` for the verification methodology to reuse.

