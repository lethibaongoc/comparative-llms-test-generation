"""
run_pilot_groq.py — Pilot run: Groq Llama-3.3-70B on 3 sample focal methods
Conditions: C1 (zero-shot), C2 (few-shot k=2)

NOTE: focal method source below is reconstructed from public Apache Commons /
Joda-Time source for demo purposes. Swap in the exact Defects4J buggy-commit
source before the official full experiment run.

Group 7 — SE1944
"""

import os, json, time, random
from datetime import datetime
from pathlib import Path
from openai import OpenAI


def _load_dotenv(path: Path):
    """Minimal .env loader (no extra dependency) — only fills vars not already set."""
    if not path.exists():
        return
    for line in path.read_text(encoding="utf-8").splitlines():
        line = line.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, _, value = line.partition("=")
        os.environ.setdefault(key.strip(), value.strip())


_load_dotenv(Path(__file__).resolve().parent.parent / ".env")

GROQ_API_KEY = os.environ.get("GROQ_API_KEY")
MODEL_VERSION = "llama-3.3-70b-versatile"
TEMPERATURE, TOP_P, MAX_TOKENS = 0, 1.0, 1024

RESULTS_DIR = Path(__file__).resolve().parent.parent / "results"

SYSTEM_INSTRUCTION = (
    "You are an expert Java developer. Generate a JUnit 5 unit test for the focal "
    "method below. Only output the test class code. Do not include any explanation."
)

# Few-shot exemplars (k=2) — curated to avoid data leakage with the 30-method test set
FEW_SHOT_BLOCK = """
[EXAMPLE 1]
[FOCAL METHOD] (Apache Commons CLI, Util.stripLeadingHyphens, commit 22d985c)
static String stripLeadingHyphens(final String str) {
    if (str == null) {
        return null;
    }
    if (str.startsWith("--")) {
        return str.substring(2);
    }
    if (str.startsWith("-")) {
        return str.substring(1);
    }
    return str;
}

[TEST]
@Test
void testStripLeadingHyphens() {
    assertEquals("foo", Util.stripLeadingHyphens("-foo"));
    assertEquals("foo", Util.stripLeadingHyphens("--foo"));
    assertEquals("--", Util.stripLeadingHyphens("--"));
}

[EXAMPLE 2]
[FOCAL METHOD] (Joda-Time, FieldUtils.safeAdd, commit d6ba4f0, adapted JUnit3 -> JUnit5)
public static int safeAdd(int val1, int val2) {
    int sum = val1 + val2;
    if ((val1 ^ sum) < 0 && (val2 ^ sum) < 0) {
        throw new ArithmeticException("The calculation caused an overflow: " + val1 + " + " + val2);
    }
    return sum;
}

[TEST]
@Test
void testSafeAdd() {
    assertEquals(5, FieldUtils.safeAdd(2, 3));
    assertThrows(ArithmeticException.class, () -> FieldUtils.safeAdd(Integer.MAX_VALUE, 1));
}
"""

FOCAL_METHODS = [
    {
        "id": "Lang-8",
        "class_context": """import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class FastDatePrinter implements DatePrinter, Serializable {
    static String getTimeZoneDisplay(TimeZone tz, boolean daylight, int style, Locale locale) { ... }
    private static class TimeZoneNameRule implements Rule {
        private final Locale mLocale;
        private final int mStyle;
        private final String mStandard;
        private final String mDaylight;
        TimeZoneNameRule(TimeZone timeZone, Locale locale, int style) {
            mLocale = locale;
            mStyle = style;
            mStandard = getTimeZoneDisplay(timeZone, false, style, locale);
            mDaylight = getTimeZoneDisplay(timeZone, true, style, locale);
        }""",
        "focal_method": """        @Override
        public void appendTo(StringBuffer buffer, Calendar calendar) {
            TimeZone zone = calendar.getTimeZone();
            if (zone.useDaylightTime() && calendar.get(Calendar.DST_OFFSET) != 0) {
                buffer.append(getTimeZoneDisplay(zone, true, mStyle, mLocale));
            } else {
                buffer.append(getTimeZoneDisplay(zone, false, mStyle, mLocale));
            }
        }""",
        "skeleton": """package org.apache.commons.lang3.time;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
public class FastDatePrinterTest {
}""",
    },
    {
        "id": "Math-53",
        "class_context": """public class Complex implements FieldElement<Complex>, Serializable {
    private final double real;
    private final double imaginary;
    private final boolean isNaN;
    public static final Complex NaN = new Complex(Double.NaN, Double.NaN);

    private Complex createComplex(double realPart, double imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }""",
        "focal_method": """    public Complex add(Complex addend) {
        if (isNaN || addend.isNaN) {
            return NaN;
        }
        return createComplex(real + addend.getReal(), imaginary + addend.getImaginary());
    }""",
        "skeleton": """package org.apache.commons.math4.complex;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
public class ComplexTest {
}""",
    },
    {
        "id": "Lang-22",
        "class_context": """public final class Fraction extends Number implements Comparable<Fraction> {
    private final int numerator;
    private final int denominator;""",
        "focal_method": """    private static int greatestCommonDivisor(int u, int v) {
        if (u == 0 || v == 0) {
            if (u == Integer.MIN_VALUE || v == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: gcd is 2^31");
            }
            return Math.abs(u) + Math.abs(v);
        }
        if (u > 0) { u = -u; }
        if (v > 0) { v = -v; }
        int k = 0;
        while ((u & 1) == 0 && (v & 1) == 0 && k < 31) {
            u /= 2; v /= 2; k++;
        }
        if (k == 31) {
            throw new ArithmeticException("overflow: gcd is 2^31");
        }
        int t = ((u & 1) == 1) ? v : -u;
        while (t != 0) {
            while ((t & 1) == 0) { t /= 2; }
            if (t > 0) { u = -t; } else { v = t; }
            t = (v - u) / 2;
        }
        return -u * (1 << k);
    }""",
        "skeleton": """package org.apache.commons.lang3.math;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
public class FractionTest {
}""",
    },
]


def build_prompt(method, condition):
    parts = [SYSTEM_INSTRUCTION, ""]
    if condition == "C2":
        parts.append(FEW_SHOT_BLOCK)
    parts.append(f"[CLASS CONTEXT]\n{method['class_context']}\n")
    parts.append(f"[FOCAL METHOD]\n{method['focal_method']}\n")
    parts.append(f"[TEST CLASS SKELETON]\n{method['skeleton']}")
    return "\n".join(parts)


def call_groq(prompt, method_id, condition):
    if not GROQ_API_KEY:
        raise ValueError("GROQ_API_KEY not set!")
    client = OpenAI(api_key=GROQ_API_KEY, base_url="https://api.groq.com/openai/v1")
    for attempt in range(5):
        try:
            t0 = time.time()
            resp = client.chat.completions.create(
                model=MODEL_VERSION,
                messages=[{"role": "user", "content": prompt}],
                temperature=TEMPERATURE, top_p=TOP_P, max_tokens=MAX_TOKENS,
            )
            return {
                "method_id": method_id, "condition": condition, "status": "success",
                "timestamp": datetime.utcnow().isoformat() + "Z",
                "response_model": resp.model,
                "latency_s": round(time.time() - t0, 3),
                "prompt_tokens": resp.usage.prompt_tokens,
                "completion_tokens": resp.usage.completion_tokens,
                "total_tokens": resp.usage.total_tokens,
                "output": resp.choices[0].message.content,
            }
        except Exception as e:
            if "rate_limit" in str(e).lower() or "429" in str(e):
                w = (2 ** attempt) + random.uniform(0, 1)
                print(f"[Rate limit] Retry {attempt + 1}/5 for {method_id}/{condition}")
                time.sleep(w)
            else:
                return {"method_id": method_id, "condition": condition, "status": "INVALID",
                        "output": None, "error": str(e)}
    return {"method_id": method_id, "condition": condition, "status": "INVALID",
            "output": None, "error": "Max retries"}


def main():
    RESULTS_DIR.mkdir(exist_ok=True)
    results = []
    for method in FOCAL_METHODS:
        for condition in ("C1", "C2"):
            prompt = build_prompt(method, condition)
            print(f"Calling Groq for {method['id']} / {condition} ...")
            result = call_groq(prompt, method["id"], condition)
            results.append(result)
            status = result["status"]
            if status == "success":
                print(f"  -> success ({result['total_tokens']} tokens, {result['latency_s']}s)")
            else:
                print(f"  -> {status} | {result.get('error')}")

    out_path = RESULTS_DIR / "pilot_groq_llama33_70b.json"
    with open(out_path, "w", encoding="utf-8") as f:
        json.dump(results, f, ensure_ascii=False, indent=2)

    passed = sum(1 for r in results if r["status"] == "success")
    print(f"\nPilot done: {passed}/{len(results)} calls succeeded.")
    print(f"Results saved to {out_path}")


if __name__ == "__main__":
    main()
