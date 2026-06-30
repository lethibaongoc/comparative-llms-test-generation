"""
test_api.py — Gate E3: Kiem tra Groq API (Llama-3.3-70B) hoat dong
Nhom: Group 7
"""

import os, json, time
from datetime import datetime
from openai import OpenAI

GROQ_API_KEY = os.environ.get("GROQ_API_KEY")
MODEL_VERSION = "llama-3.3-70b-versatile"
TEMPERATURE, TOP_P, MAX_TOKENS = 0, 1.0, 1024

PROMPT_C1 = """You are an expert Java developer. Generate a JUnit 5 unit test for the focal method below. Only output the test class code. Do not include any explanation.

[CLASS CONTEXT]
import java.util.Calendar;
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
        }

[FOCAL METHOD]
        @Override
        public void appendTo(StringBuffer buffer, Calendar calendar) {
            TimeZone zone = calendar.getTimeZone();
            if (zone.useDaylightTime() && calendar.get(Calendar.DST_OFFSET) != 0) {
                buffer.append(getTimeZoneDisplay(zone, true, mStyle, mLocale));
            } else {
                buffer.append(getTimeZoneDisplay(zone, false, mStyle, mLocale));
            }
        }

[TEST CLASS SKELETON]
package org.apache.commons.lang3.time;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
public class FastDatePrinterTest {
}
"""

def call_groq(prompt, condition="C1"):
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
                "status": "success", "condition": condition,
                "timestamp": datetime.utcnow().isoformat()+"Z",
                "response_model": resp.model, "cost_per_call": 0.0,
                "latency_s": round(time.time()-t0, 3),
                "prompt_tokens": resp.usage.prompt_tokens,
                "completion_tokens": resp.usage.completion_tokens,
                "total_tokens": resp.usage.total_tokens,
                "output": resp.choices[0].message.content,
            }
        except Exception as e:
            if "rate_limit" in str(e).lower() or "429" in str(e):
                import random
                w = (2**attempt) + random.uniform(0, 1)
                print(f"[Rate limit] Retry {attempt+1}/5")
                time.sleep(w)
            else:
                raise
    return {"status": "INVALID", "condition": condition, "output": None, "error": "Max retries"}

if __name__ == "__main__":
    print("Gate E3 - test_api.py: Groq Llama-3.3-70B")
    result = call_groq(PROMPT_C1, "C1")
    print(f"Status: {result['status']} | Tokens: {result.get('total_tokens')}")
    if result["status"]=="success":
        print("Gate E3 PASSED")
    else:
        print(f"Gate E3 FAILED: {result.get('error')}")