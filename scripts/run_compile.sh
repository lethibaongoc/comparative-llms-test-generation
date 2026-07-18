#!/usr/bin/env bash
# One-command wrapper for measure_compilability.py
# Usage (inside WSL, from anywhere):
#   cd /mnt/d/semester5/swt301/research/comparative-llms-test-generation
#   bash scripts/run_compile.sh              # lenient mode
#   bash scripts/run_compile.sh --strict     # strict mode
# Extra args are passed straight through to the Python script.
set -euo pipefail

# repo root = parent of this script's dir
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

# 1. JUnit 5 jar
fetch "$JUNIT_JAR" "$JUNIT_URL"

# 2. Mockito -- 16 generated tests use it and it is not a Lang/Math/Chart
#    dependency, so without these they fail on the missing jar, not on merit.
#    4.x is the last line that still runs on the Java 8 Defects4J requires.
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
  echo "       Install it (see COMPILABILITY_SETUP.md) or add framework/bin to PATH:" >&2
  echo "       export PATH=\$PATH:\$HOME/defects4j/framework/bin" >&2
  exit 1
fi

# 4. pick javac (prefer JAVAC env; else whatever is on PATH)
JAVAC="${JAVAC:-javac}"
echo ">> Using javac: $($JAVAC -version 2>&1)"
echo ">> Using defects4j: $(command -v defects4j)"

# 5. strict and lenient are two separate reported numbers -- keep them in
#    separate output dirs so a strict run cannot clobber the lenient results.
OUT="$HERE/results/compilability"
for a in "$@"; do [ "$a" = "--strict" ] && OUT="$HERE/results/compilability-strict"; done

# 6. run
python3 "$HERE/scripts/measure_compilability.py" \
  --repo     "$HERE" \
  --junit5   "$JUNIT_JAR" \
  --javac    "$JAVAC" \
  --extra-cp "$MOCKITO_CP" \
  --work     "$HERE/_d4j_work" \
  --out      "$OUT" \
  "$@"

echo ""
echo ">> Results in: $OUT/"
echo "   - compilability_results.csv"
echo "   - compilability_summary.md"
