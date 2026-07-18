#!/usr/bin/env bash
# One-command wrapper for measure_coverage.py (Stage 3: RQ1 line + RQ2 branch
# coverage via JaCoCo on the Defects4J fixed trees).
# Usage (inside WSL, from anywhere):
#   cd /mnt/d/semester5/swt301/research/comparative-llms-test-generation
#   export PATH=$PATH:$HOME/defects4j/framework/bin
#   bash scripts/run_coverage.sh                     # full sweep (lenient)
#   bash scripts/run_coverage.sh --strict            # strict import mode
#   bash scripts/run_coverage.sh --bugs Lang-8       # smoke-test one subject
# Shares the compile harness's jars and _d4j_work/ checkouts; only the fixed
# (f) trees are needed here. Extra args pass straight through.
set -euo pipefail

HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JUNIT_VER="1.10.2"
JUNIT_JAR="$HERE/tools/junit-platform-console-standalone-${JUNIT_VER}.jar"
JUNIT_URL="https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/${JUNIT_VER}/junit-platform-console-standalone-${JUNIT_VER}.jar"

# JaCoCo 0.8.11 is the last line that comfortably runs the Java 8 that
# Defects4J's Lang/Math/Chart build under. agent = the -runtime jar you attach
# with -javaagent; cli = the -nodeps CLI that turns the .exec into an XML report.
JACOCO_VER="0.8.11"
JACOCO_AGENT="$HERE/tools/jacocoagent-${JACOCO_VER}.jar"
JACOCO_CLI="$HERE/tools/jacococli-${JACOCO_VER}.jar"
JACOCO_AGENT_URL="https://repo1.maven.org/maven2/org/jacoco/org.jacoco.agent/${JACOCO_VER}/org.jacoco.agent-${JACOCO_VER}-runtime.jar"
JACOCO_CLI_URL="https://repo1.maven.org/maven2/org/jacoco/org.jacoco.cli/${JACOCO_VER}/org.jacoco.cli-${JACOCO_VER}-nodeps.jar"

mkdir -p "$HERE/tools"
fetch() { # fetch <dest> <url>
  [ -f "$1" ] && return 0
  echo ">> Downloading $(basename "$1") ..."
  if command -v curl >/dev/null 2>&1; then curl -fsSL -o "$1" "$2"; else wget -qO "$1" "$2"; fi
}

fetch "$JUNIT_JAR" "$JUNIT_URL"
fetch "$JACOCO_AGENT" "$JACOCO_AGENT_URL"
fetch "$JACOCO_CLI" "$JACOCO_CLI_URL"

# Mockito line (same as the compile/exec harnesses): needed at run time by the
# Mockito-using tests, else they fail on the missing jar rather than on merit.
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

if ! command -v defects4j >/dev/null 2>&1; then
  echo "ERROR: 'defects4j' is not on PATH." >&2
  echo "       export PATH=\$PATH:\$HOME/defects4j/framework/bin" >&2
  exit 1
fi

JAVAC="${JAVAC:-javac}"; JAVA="${JAVA:-java}"
echo ">> javac: $($JAVAC -version 2>&1)"
echo ">> java:  $($JAVA -version 2>&1 | head -1)"
echo ">> defects4j: $(command -v defects4j)"

OUT="$HERE/results/coverage"
for a in "$@"; do [ "$a" = "--strict" ] && OUT="$HERE/results/coverage-strict"; done

python3 "$HERE/scripts/measure_coverage.py" \
  --repo         "$HERE" \
  --junit5       "$JUNIT_JAR" \
  --jacoco-agent "$JACOCO_AGENT" \
  --jacoco-cli   "$JACOCO_CLI" \
  --javac        "$JAVAC" \
  --java         "$JAVA" \
  --extra-cp     "$MOCKITO_CP" \
  --work         "$HERE/_d4j_work" \
  --out          "$OUT" \
  "$@"

echo ""
echo ">> Results in: $OUT/"
echo "   - coverage_results.csv"
echo "   - coverage_summary.md"
echo "   - jacoco_xml/"
