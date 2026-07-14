"""
generate_tests.py - Generate unit tests for 10 Java methods using Gemini and Llama
Usage: python scripts/generate_tests.py
"""
import os
import json
import time
from dotenv import load_dotenv

load_dotenv()

ZERO_SHOT = "C1"
FEW_SHOT = "C2"

# Standardized on the same k=2 few-shot exemplar used for the Llama pilot
# (scripts/run_pilot_groq.py FEW_SHOT_BLOCK) so C2 is comparable across all
# 4 models — see notes.md 2026-07-14 for why this replaced the old add()
# example.
FEW_SHOT_EXAMPLE = '''
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
'''

def build_prompt(method_info, condition):
    source = method_info["source"]
    class_name = method_info["class"]
    method_name = method_info["method"]

    if condition == ZERO_SHOT:
        return f"""Generate JUnit 5 unit tests for this Java method.
Return ONLY the test methods (no class declaration, no imports).

Class: {class_name}
Method: {method_name}
Source:
{source}
"""
    else:
        return f"""Generate JUnit 5 unit tests for this Java method.
Return ONLY the test methods (no class declaration, no imports).

Here are 2 example tests for reference:
{FEW_SHOT_EXAMPLE}

Now generate tests for:
Class: {class_name}
Method: {method_name}
Source:
{source}
"""

def generate_gemini(prompt):
    from google import genai
    client = genai.Client(api_key=os.environ["GEMINI_API_KEY"])
    response = client.models.generate_content(
        model="gemini-2.5-flash",
        contents=prompt
    )
    return response.text.strip()

def generate_llama(prompt):
    # NOTE: this calls OpenRouter's meta-llama/llama-4-scout, which is NOT the
    # team's actual Llama model (that's llama-3.3-70b-versatile via Groq, see
    # results/PILOT_RESULTS.md and scripts/run_pilot_groq.py). Output below is
    # saved under a distinct folder name so it can't collide with that data.
    from openai import OpenAI
    client = OpenAI(
        api_key=os.environ["OPENROUTER_API_KEY"],
        base_url="https://openrouter.ai/api/v1"
    )
    response = client.chat.completions.create(
        model="meta-llama/llama-4-scout",
        messages=[{"role": "user", "content": prompt}],
        temperature=0
    )
    return response.choices[0].message.content.strip()

def save_result(method_id, model, condition, content):
    folder = f"data/generated/{model}"
    os.makedirs(folder, exist_ok=True)
    filename = f"{folder}/{method_id}_{condition}.java"
    with open(filename, "w", encoding="utf-8") as f:
        f.write(content)
    print(f"  Saved: {filename}")

def main():
    with open("data/raw/methods/methods.json", "r") as f:
        methods = json.load(f)

    print(f"Loaded {len(methods)} methods\n")

    for method in methods:
        mid = method["id"]
        print(f"Processing {mid}...")

        for condition in [ZERO_SHOT, FEW_SHOT]:
            prompt = build_prompt(method, condition)

            # Gemini
            try:
                result = generate_gemini(prompt)
                save_result(mid, "gemini", condition, result)
            except Exception as e:
                print(f"  [GEMINI {condition}] FAIL - {e}")

            time.sleep(2)

            # Llama
            try:
                result = generate_llama(prompt)
                save_result(mid, "llama-4-scout-openrouter", condition, result)
            except Exception as e:
                print(f"  [LLAMA {condition}] FAIL - {e}")

            time.sleep(1)

        print()

    print("Done! Check data/generated/")

if __name__ == "__main__":
    main()
