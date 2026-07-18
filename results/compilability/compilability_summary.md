# Compilability Results (Defects4J v2.0.0, javac)

- import mode: **lenient (auto project imports)**
- checkout version: **f** (f=fixed, b=buggy)
- total files: **240**

## Compilability rate per model

| Model | Compiled | Total | Rate |
|---|---|---|---|
| llama | 35 | 60 | 58.3% |
| gemini | 38 | 60 | 63.3% |
| deepseek | 25 | 60 | 41.7% |
| gpt-5.5-instant | 46 | 60 | 76.7% |

## Per condition (C1 zero-shot vs C2 few-shot)

| Model | C1 rate | C2 rate |
|---|---|---|
| llama | 60.0% | 56.7% |
| gemini | 63.3% | 63.3% |
| deepseek | 36.7% | 46.7% |
| gpt-5.5-instant | 73.3% | 80.0% |

## File shape handling (fragment = wrapped in scaffold, full_class = compiled as-is)

| Model | fragment | full_class |
|---|---|---|
| llama | 0 | 60 |
| gemini | 60 | 0 |
| deepseek | 57 | 3 |
| gpt-5.5-instant | 60 | 0 |

## Failure patterns per model

| Model | abstract_instantiation | ambiguous_method_overload | ambiguous_type_from_injected_import | cannot_find_symbol | illegal_escape_character | incompatible_types | no_suitable_constructor | other_compile_error | private_method_access | unreported_checked_exception |
|---|---|---|---|---|---|---|---|---|---|---|
| llama | 1 | 8 | 0 | 2 | 2 | 0 | 4 | 1 | 6 | 1 |
| gemini | 0 | 6 | 1 | 7 | 0 | 0 | 3 | 0 | 5 | 0 |
| deepseek | 0 | 6 | 1 | 15 | 0 | 2 | 4 | 2 | 4 | 1 |
| gpt-5.5-instant | 0 | 5 | 0 | 8 | 0 | 1 | 0 | 0 | 0 | 0 |