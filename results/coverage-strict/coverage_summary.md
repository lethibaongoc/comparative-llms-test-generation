# Coverage Results (JaCoCo on Defects4J v2.0.0 fixed trees)

- import mode: **strict**
- per-file run timeout: **90s**, headless AWT
- total files: **240**

Coverage is of the **focal class**, on the fixed program, averaged over the tests that ran (compiled + executed). A test that fails assertions still counts the code it exercised before failing.

## Per model (mean over tests that ran)

| Model | Ran | Mean line cov | Mean branch cov |
|---|---|---|---|
| deepseek | 22 | 23.5% | 10.3% |
| gemini | 28 | 28.4% | 15.7% |
| gpt-5.5-instant | 36 | 28.8% | 15.5% |
| llama | 35 | 26.5% | 13.8% |

## By condition (C1 zero-shot vs C2 few-shot) — RQ4

| Model | C1 line | C1 branch | C2 line | C2 branch |
|---|---|---|---|---|
| deepseek | 18.9% | 5.4% | 27.4% | 14.5% |
| gemini | 28.5% | 16.8% | 28.4% | 14.7% |
| gpt-5.5-instant | 29.6% | 16.8% | 28.2% | 14.4% |
| llama | 25.7% | 13.5% | 27.3% | 14.1% |