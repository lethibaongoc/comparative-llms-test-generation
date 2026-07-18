#!/usr/bin/env bash
# One-command wrapper for measure_execution.py (stage 2: run tests + detect bugs).
# Usage (inside WSL, from anywhere):
#   cd /mnt/d/semester5/swt301/research/comparative-llms-test-generation
#   export PATH=$PATH:$HOME/defects4j/framework/bin
#   bash scripts/run_execution.sh                       # full sweep (checks out f AND b)
#   bash scripts/run_execution.sh --reuse-checkouts     # reuse trees from run_compile.sh (fast)
#   bash scripts/run_execution.sh --bugs Lang-8         # smoke-test one subject
#   bash scripts/run_execution.sh --models gpt-5.5-instant
# Extra args pass straight through to the Python script.
#
# This is the sibling of run_compile.sh and shares its jars, its _d4j_work/
# checkouts, and its defects4j install. run_compile.sh only ever needs the
# fixed (f) trees; this stage additionally checks out the buggy (b) trees, so
# --reuse-checkouts still has to build the b side the first time.
set -euo pipefail

HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JUNIT_VER="1.10.2"
JUNIT_JAR="$HERE/tools/junit-platform-console-standalone-${JUNIT_VER}.jar"
JUNIT_URL="https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/${JUNIT_VER}/junit-platform-console-standalone-${JUNIT_VER}.jar"

mkdir -p "$HERE/tools"

fetch() { # fetch <dest> <url>
  [ -f "$1" ] && return 0
  echo ">> Downloading $(basename "$1") ..."
  if command -v curl >/dev/null 2>&1; then curl -fsSL -o "$1" "$2"; else wget -qO "$1" "$2"; fi
}

# 1. JUnit 5 standalone jar -- both compiles AND runs the tests.
fetch "$JUNIT_JAR" "$JUNIT_URL"

# 2. Mockito line (same set as run_compile.sh): the Mockito-using tests need it
#    at RUN time too, not just to compile.
MOCKITO_CP=""
for spec in \
  "org/mockito/mockito-core/4.11.0/mockito-core-4.11.0.jar" \
  "net/bytebuddy/byte-buddy/1.12.19/byte-buddy-1.12.19.jar" \
  "net/bytebuddy/byte-buddy-agent/1.12.19/byte-buddy-agent-1.12.19.jar" \
  "org/objenesis/objenesis/3.3/objenesis-3.3.jar" ; do
  jar="$HERE/tools/$(basename "$spec")"
  fetch "$jar" "https://repo1.maven.org/maven2/$spec"
  MOCKITO_CP="${MOCKITO_CP:+$MOCKITO_CP:}$jar"
done

# 3. defects4j on PATH?
if ! command -v defects4j >/dev/null 2>&1; then
  echo "ERROR: 'defects4j' is not on PATH." >&2
  echo "       export PATH=\$PATH:\$HOME/defects4j/framework/bin" >&2
  exit 1
fi

# 4. pick javac/java (prefer JAVAC/JAVA env; else whatever is on PATH)
JAVAC="${JAVAC:-javac}"
JAVA="${JAVA:-java}"
echo ">> Using javac: $($JAVAC -version 2>&1)"
echo ">> Using java:  $($JAVA -version 2>&1 | head -1)"
echo ">> Using defects4j: $(command -v defects4j)"

OUT="$HERE/results/execution"
for a in "$@"; do [ "$a" = "--strict" ] && OUT="$HERE/results/execution-strict"; done

# 5. run
python3 "$HERE/scripts/measure_execution.py" \
  --repo     "$HERE" \
  --junit5   "$JUNIT_JAR" \
  --javac    "$JAVAC" \
  --java     "$JAVA" \
  --extra-cp "$MOCKITO_CP" \
  --work     "$HERE/_d4j_work" \
  --out      "$OUT" \
  "$@"

echo ""
echo ">> Results in: $OUT/"
echo "   - execution_results.csv"
echo "   - execution_summary.md"
echo "   - run_logs/"
