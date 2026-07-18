# Compilability measurement — setup & run

Runs on **your machine** (not the Cowork sandbox — that environment can't host
Defects4J). Measures the real compilability rate of all 240 generated tests and
auto-classifies every failure into the study's bug-pattern buckets.

## Quick start

Defects4J is installed in WSL at `~/defects4j` (see §1 for how it got there).

```bash
# inside WSL
cd /mnt/d/semester5/swt301/research/comparative-llms-test-generation
export PATH=$PATH:$HOME/defects4j/framework/bin
bash scripts/run_compile.sh            # lenient mode (~45 min: 30 checkouts + ant compile)
bash scripts/run_compile.sh --strict --reuse-checkouts   # strict mode, reusing them (~2 min)
bash scripts/run_compile.sh --bugs Lang-8          # smoke test one subject
bash scripts/run_compile.sh --models gemini        # or one model
```

`--reuse-checkouts` skips checkout+`ant compile` wherever `_d4j_work/<Proj>_<n>f`
already exists. The run only ever reads those trees, so a second pass over the
same subjects can reuse them; drop the flag to force clean checkouts.

`run_compile.sh` auto-downloads the JUnit 5 and Mockito jars into `tools/`,
checks that `defects4j` is on PATH, and runs the measurement. Results land in
`results/compilability/`.

## 1. Install Defects4J v2.0.0

Installed 2026-07-17 into the WSL **native** filesystem at `~/defects4j`, not
under `/mnt/d`: Defects4J checks out and builds thousands of files per subject,
and doing that across the drvfs mount is far slower and hits permission/symlink
trouble. The repo path does not matter — `run_compile.sh` only needs
`defects4j` on PATH.

```bash
sudo apt update && sudo apt install -y openjdk-8-jdk perl cpanminus git subversion unzip build-essential
git clone --branch v2.0.0 --depth 1 https://github.com/rjust/defects4j.git ~/defects4j
cd ~/defects4j
cpanm --installdeps .
./init.sh                     # downloads project repos, ~2 GB, slow
export PATH=$PATH:$(pwd)/framework/bin
defects4j info -p Lang        # sanity check
```

### javac version

Defects4J's framework and the Lang/Math/Chart projects build under **Java 8**,
and the generated tests are compiled with the **same javac 8** — their
classpath is Java-8-compiled project code, so staying on one compiler avoids
cross-version noise. Override with `JAVAC=` if a different toolchain is wanted:

```bash
JAVAC=/usr/lib/jvm/java-21-openjdk-amd64/bin/javac bash scripts/run_compile.sh
```

## 2. Mockito (on by default)

16 files use `mock(...)`/`when(...)`/`Mockito.…`. Mockito is **not** a
dependency of Lang/Math/Chart, so without the jars they fail on the missing
dependency rather than on test quality. `run_compile.sh` therefore downloads
mockito-core 4.11.0 + byte-buddy + objenesis and passes them on `--extra-cp`
automatically (4.x is the last line that runs on Java 8). Report that this was
done. To measure the harsher "paste-and-run as-is" number instead, pass
`--extra-cp ""`.

## File shapes — why the harness has two paths

The models did not all answer in the same form, and the measurement is only
valid because the script handles both (`file_shape` column in the CSV):

| shape | count | who | handling |
|---|---|---|---|
| `fragment` | 177 | all gemini, all gpt-5.5-instant, 57 deepseek | no `package`/`import`/class — wrapped in a scaffold class in the focal package |
| `full_class` | 63 | all 60 llama, deepseek Lang-8_C1/C2 + Math-2_C2 | complete compilation unit the model wrote — compiled **as-is** |

Wrapping a `full_class` file in the scaffold nests a class inside a class and
fails all 63 on syntax alone — which would have been published as
"llama = 0% compilable". Do not "simplify" this back into one path.

## Output

Lenient writes to `results/compilability/`, strict to
`results/compilability-strict/` — two separately reported numbers, so neither
run clobbers the other:

- `compilability_results.csv` — 240 rows; `file_shape` says which path each
  file took, `error_category` buckets each failure
- `compilability_summary.md` — per-model rate, C1-vs-C2, file-shape split,
  failure-pattern matrix
- `errors/*.txt` — raw `javac` stderr per failure

## Error categories

| category | meaning |
|---|---|
| `ambiguous_reference` | **harness artifact, not a model error** — a simple name matched two of lenient mode's injected wildcard imports. Re-check any of these under `--strict` before reporting. |
| `protected_or_private_ctor` | `new FastDateFormat(...)` — Lang-26 pattern |
| `abstract_instantiation` | `new RegularTimePeriod()` — Chart-17 pattern |
| `no_suitable_constructor` | invented ctor arity/types — Chart-21, Math-9 |
| `private_method_access` | calling a private method directly (`updateBounds()`) |
| `mockito_missing` | test needs Mockito, not on classpath — should be **0** now that `run_compile.sh` supplies the jars; a non-zero count means the `--extra-cp` download failed |
| `cannot_find_symbol` | invented type/method/field (`Point`, `.data`) |
| `incompatible_types` | wrong-dimension args (`Vector2D` vs `Vector3D`) |
| `package_does_not_exist` | import of a non-existent package |
| `other_compile_error` / `unknown` | catch-all |

## Tip

Do a dry run on one model first to confirm your paths before the full run:

```bash
bash scripts/run_compile.sh --models gemini
```
