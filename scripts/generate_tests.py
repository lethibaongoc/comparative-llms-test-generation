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

FEW_SHOT_EXAMPLE = '''
// Example test for a simple add method:
// public int add(int a, int b) { return a + b; }
@Test
public void testAdd_positive() {
    assertEquals(5, obj.add(2, 3));
}
@Test
public void testAdd_negative() {
    assertEquals(-1, obj.add(2, -3));
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
                save_result(mid, "llama", condition, result)
            except Exception as e:
                print(f"  [LLAMA {condition}] FAIL - {e}")

            time.sleep(1)

        print()

    print("Done! Check data/generated/")

if __name__ == "__main__":
    main()
