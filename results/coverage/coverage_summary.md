# Coverage Results (JaCoCo on Defects4J v2.0.0 fixed trees)

- import mode: **lenient**
- per-file run timeout: **90s**, headless AWT
- total files: **240**

Coverage is of the **focal class**, on the fixed program, averaged over the tests that ran (compiled + executed). A test that fails assertions still counts the code it exercised before failing.

## Per model (mean over tests that ran)

| Model | Ran | Mean line cov | Mean branch cov |
|---|---|---|---|
| deepseek | 25 | 22.1% | 9.7% |
| gemini | 38 | 25.2% | 14.8% |
| gpt-5.5-instant | 46 | 27.5% | 16.7% |
| llama | 35 | 26.5% | 13.8% |

## By condition (C1 zero-shot vs C2 few-shot) — RQ4

| Model | C1 line | C1 branch | C2 line | C2 branch |
|---|---|---|---|---|
| deepseek | 18.3% | 5.4% | 25.1% | 13.1% |
| gemini | 23.6% | 13.5% | 26.8% | 16.2% |
| gpt-5.5-instant | 28.1% | 17.7% | 27.0% | 15.7% |
| llama | 25.7% | 13.5% | 27.3% | 14.1% |