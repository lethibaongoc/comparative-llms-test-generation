"""
test_api.py - Gate E3: Test ket noi 4 LLM APIs
Chay: python scripts/test_api.py
"""
import os
from dotenv import load_dotenv

load_dotenv()

def test_gemini():
    try:
        from google import genai
        client = genai.Client(api_key=os.environ["GEMINI_API_KEY"])
        response = client.models.generate_content(
            model="gemini-2.5-flash",
            contents="Say: API OK"
        )
        print(f"[GEMINI] OK - {response.text.strip()}")
    except Exception as e:
        print(f"[GEMINI] FAIL - {e}")

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

if __name__ == "__main__":
    print("Testing 4 APIs...")
    test_gemini()
    test_deepseek()
    test_llama()
    test_gpt()
    print("Done.")
