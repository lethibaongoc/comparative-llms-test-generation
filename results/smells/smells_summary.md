# Test-Smell Results (tsDetect, RQ3)

- granularity: **numerical** (smell instance counts)
- total files: **240**
- measured on ALL generated tests that parse (does not require compilation)

21 smell types detected. **total_smells** = sum of all smell instances in a test class (excludes IgnoredTest — a JUnit-4 tooling artifact on JUnit-5 non-public tests); **distinct** = number of different smell types present.

## Per model

| Model | Files parsed | Mean smells/test | Mean distinct types/test |
|---|---|---|---|
| deepseek | 60/60 | 1.65 | 0.3 |
| gemini | 60/60 | 0.0 | 0.0 |
| gpt-5.5-instant | 60/60 | 10.32 | 1.6 |
| llama | 58/60 | 8.24 | 1.59 |

## By condition (C1 zero-shot vs C2 few-shot) — RQ4

| Model | C1 smells/test | C2 smells/test |
|---|---|---|
| deepseek | 1.03 | 2.27 |
| gemini | 0.0 | 0.0 |
| gpt-5.5-instant | 20.63 | 0.0 |
| llama | 15.97 | 0.52 |

## Smell prevalence (parsed tests containing each smell)

| Smell | Files | Total instances |
|---|---|---|
| IgnoredTest *(excluded, JUnit-4 artifact)* | 153 | 790 |
| Magic Number Test | 61 | 334 |
| Lazy Test | 60 | 553 |
| Eager Test | 37 | 108 |
| Assertion Roulette | 23 | 80 |
| Exception Catching Throwing | 11 | 63 |
| Sensitive Equality | 7 | 46 |
| Duplicate Assert | 3 | 6 |
| Conditional Test Logic | 2 | 2 |
| General Fixture | 1 | 3 |
| Unknown Test | 1 | 1 |
| Constructor Initialization | 0 | 0 |
| Default Test | 0 | 0 |
| EmptyTest | 0 | 0 |
| Mystery Guest | 0 | 0 |
| Print Statement | 0 | 0 |
| Redundant Assertion | 0 | 0 |
| Verbose Test | 0 | 0 |
| Sleepy Test | 0 | 0 |
| Resource Optimism | 0 | 0 |
| Dependent Test | 0 | 0 |