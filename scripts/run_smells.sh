#!/usr/bin/env bash
# One-command wrapper for measure_smells.py (Stage 4: RQ3 test-smell density
# via tsDetect / TestSmellDetector).
# Usage (inside WSL, from anywhere):
#   cd /mnt/d/semester5/swt301/research/comparative-llms-test-generation
#   bash scripts/run_smells.sh                 # all 240 files, numerical counts
#   bash scripts/run_smells.sh --bugs Lang-8   # smoke-test one subject
#
# tsDetect is not on Maven Central, so this builds it from source once
# (cloning TestSmells/TestSmellDetector, `mvn package`) into tools/tsdetect.jar.
# Needs the fixed (f) checkouts under _d4j_work/ for the production-file paths,
# which the compile/exec stages already created. Extra args pass through.
set -euo pipefail

HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
TSDETECT_JAR="$HERE/tools/tsdetect.jar"
TSDETECT_REPO="https://github.com/TestSmells/TestSmellDetector.git"

mkdir -p "$HERE/tools"

if [ ! -f "$TSDETECT_JAR" ]; then
  echo ">> Building tsDetect from source (one-time) ..."
  command -v mvn >/dev/null 2>&1 || { echo "ERROR: maven (mvn) not on PATH" >&2; exit 1; }
  BUILD="$(mktemp -d)"
  git clone --depth 1 "$TSDETECT_REPO" "$BUILD" >/dev/null 2>&1
  ( cd "$BUILD" && mvn -q -DskipTests -Dmaven.test.skip=true package )
  cp "$BUILD"/target/TestSmellDetector-*-jar-with-dependencies.jar "$TSDETECT_JAR"
  rm -rf "$BUILD"
  echo ">> Built $TSDETECT_JAR"
fi

JAVA="${JAVA:-java}"
echo ">> java: $($JAVA -version 2>&1 | head -1)"
echo ">> tsdetect: $TSDETECT_JAR"

python3 "$HERE/scripts/measure_smells.py" \
  --repo     "$HERE" \
  --tsdetect "$TSDETECT_JAR" \
  --java     "$JAVA" \
  --work     "$HERE/_d4j_work" \
  --out      "$HERE/results/smells" \
  "$@"

echo ""
echo ">> Results in: $HERE/results/smells/"
echo "   - smells_results.csv"
echo "   - smells_summary.md"
echo "   - tsdetect_raw.csv / tsdetect_input.csv"
