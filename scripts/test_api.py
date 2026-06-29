"""
test_api.py - Gate E3: Test ket noi 4 LLM APIs
Chay: python scripts/test_api.py
"""

import os

# ------------------------------------------------------------
# 1. GEMINI 3.5 FLASH (Google AI Studio)
# ------------------------------------------------------------
def test_gemini():
    try:
        import google.generativeai as genai
        genai.configure(api_key=os.environ["GEMINI_API_KEY"])
        model = genai.GenerativeModel("gemini-3.5-flash")
        response = model.generate_content("Say: API OK")
        print(f"[GEMINI] OK - {response.text.strip()}")
    except Exception as e:
        print(f"[GEMINI] FAIL - {e}")

# ------------------------------------------------------------
# 2. DEEPSEEK V4 FLASH
# ------------------------------------------------------------
def test_deepseek():
    try:
        from openai import OpenAI
        client = OpenAI(
            api_key=os.environ["DEEPSEEK_API_KEY"],
            base_url="https://api.deepseek.com"
        )
        response = client.chat.completions.create(
            model="deepseek-v4-flash",
            messages=[{"role": "user", "content": "Say: API OK"}],
            temperature=0
        )
        print(f"[DEEPSEEK] OK - {response.choices[0].message.content.strip()}")
    except Exception as e:
        print(f"[DEEPSEEK] FAIL - {e}")

# ------------------------------------------------------------
# 3. LLAMA 4 SCOUT (OpenRouter)
# ------------------------------------------------------------
def test_llama():
    try:
        from openai import OpenAI
        client = OpenAI(
            api_key=os.environ["OPENROUTER_API_KEY"],
            base_url="https://openrouter.ai/api/v1"
        )
        response = client.chat.completions.create(
            model="meta-llama/llama-4-scout",
            messages=[{"role": "user", "content": "Say: API OK"}],
            temperature=0
        )
        print(f"[LLAMA] OK - {response.choices[0].message.content.strip()}")
    except Exception as e:
        print(f"[LLAMA] FAIL - {e}")

# ------------------------------------------------------------
# 4. GPT-4O MINI (OpenAI)
# ------------------------------------------------------------
def test_gpt():
    try:
        from openai import OpenAI
        client = OpenAI(api_key=os.environ["OPENAI_API_KEY"])
        response = client.chat.completions.create(
            model="gpt-4o-mini",
            messages=[{"role": "user", "content": "Say: API OK"}],
            temperature=0
        )
        print(f"[GPT] OK - {response.choices[0].message.content.strip()}")
    except Exception as e:
        print(f"[GPT] FAIL - {e}")

# ------------------------------------------------------------
if __name__ == "__main__":
    print("Testing 4 APIs...")
    test_gemini()
    test_deepseek()
    test_llama()
    test_gpt()
    print("Done.")