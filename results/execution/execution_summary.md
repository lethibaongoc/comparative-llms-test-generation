# Execution & Bug-Detection Results (Defects4J v2.0.0)

- import mode: **lenient**
- per-file run timeout: **90s**, headless AWT
- total files: **240**

Definitions: **valid** = compiles on fixed, runs >=1 test, 0 failures.
**detected** = valid AND compiles on buggy AND >=1 failure on buggy
(the Defects4J fault-revealing criterion: pass on fixed, fail on buggy).

## Per model

| Model | Files | Compiles(f) | Valid (pass f) | Bugs detected | Detection rate |
|---|---|---|---|---|---|
| deepseek | 60 | 25 | 15 | 7 | 11.7% |
| gemini | 60 | 38 | 28 | 21 | 35.0% |
| gpt-5.5-instant | 60 | 46 | 35 | 21 | 35.0% |
| llama | 60 | 35 | 15 | 11 | 18.3% |

## Detection by condition (C1 zero-shot vs C2 few-shot)

| Model | C1 detected | C2 detected |
|---|---|---|
| deepseek | 4/30 | 3/30 |
| gemini | 12/30 | 9/30 |
| gpt-5.5-instant | 11/30 | 10/30 |
| llama | 5/30 | 6/30 |

## Bugs ever detected (any model/condition)

| Bug | #files detecting | detecting models |
|---|---|---|
| Chart-1 | 0 | - |
| Chart-10 | 3 | gemini/C1, gemini/C2, gpt-5.5-instant/C2 |
| Chart-11 | 0 | - |
| Chart-15 | 7 | deepseek/C2, gemini/C1, gemini/C2, gpt-5.5-instant/C1, gpt-5.5-instant/C2, llama/C1, llama/C2 |
| Chart-17 | 2 | gpt-5.5-instant/C1, gpt-5.5-instant/C2 |
| Chart-19 | 8 | deepseek/C1, deepseek/C2, gemini/C1, gemini/C2, gpt-5.5-instant/C1, gpt-5.5-instant/C2, llama/C1, llama/C2 |
| Chart-21 | 1 | gemini/C1 |
| Chart-22 | 4 | gemini/C1, gemini/C2, gpt-5.5-instant/C1, gpt-5.5-instant/C2 |
| Chart-23 | 3 | gemini/C2, llama/C1, llama/C2 |
| Chart-24 | 0 | - |
| Lang-8 | 0 | - |
| Lang-10 | 0 | - |
| Lang-19 | 3 | gemini/C1, gpt-5.5-instant/C1, gpt-5.5-instant/C2 |
| Lang-22 | 1 | gpt-5.5-instant/C2 |
| Lang-26 | 0 | - |
| Lang-30 | 0 | - |
| Lang-41 | 1 | gpt-5.5-instant/C1 |
| Lang-42 | 3 | gemini/C1, gemini/C2, gpt-5.5-instant/C1 |
| Lang-57 | 6 | deepseek/C1, deepseek/C2, gemini/C1, gemini/C2, gpt-5.5-instant/C1, gpt-5.5-instant/C2 |
| Lang-60 | 2 | gemini/C2, llama/C2 |
| Math-2 | 1 | gemini/C1 |
| Math-9 | 0 | - |
| Math-27 | 2 | llama/C1, llama/C2 |
| Math-53 | 0 | - |
| Math-55 | 0 | - |
| Math-59 | 4 | gpt-5.5-instant/C1, gpt-5.5-instant/C2, llama/C1, llama/C2 |
| Math-63 | 4 | deepseek/C1, gemini/C1, gemini/C2, gpt-5.5-instant/C1 |
| Math-70 | 2 | gemini/C1, gpt-5.5-instant/C2 |
| Math-75 | 2 | gemini/C1, gpt-5.5-instant/C1 |
| Math-79 | 1 | deepseek/C1 |