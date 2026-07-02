# Pilot Results — Groq Llama-3.3-70B

First real API run against the target model, run manually via the Groq Playground
(console.groq.com) after the sandbox environment's outbound network turned out to
block `api.groq.com` directly. Parameters matched the experimental design
(`temperature=0`, `max_completion_tokens=1024`, `top_p=1`).

## Run 1 — Lang-8 / C1 (zero-shot)

- **Model:** `llama-3.3-70b-versatile`
- **Date:** 2026-07-03
- **Condition:** C1 (zero-shot)
- **Focal method:** `FastDatePrinter.TimeZoneNameRule#appendTo` (Lang-8)
- **Latency:** 1.228 s
- **Throughput:** 495 tokens/s
- **Output:** [`pilot_llama33_70b_Lang-8_C1.java`](./pilot_llama33_70b_Lang-8_C1.java)

Model generated 3 test methods (`testAppendTo_StandardTime`, `testAppendTo_DaylightTime`,
`testAppendTo_TimeZoneWithoutDaylightSaving`) covering standard time, daylight time, and
a non-DST timezone — reasonable coverage for a zero-shot single call.

## Next steps

- Run remaining conditions (Math-53, Lang-22 × C1/C2) using the same Playground
  workflow, or `scripts/run_pilot_groq.py` once network access to `api.groq.com`
  is available (works from a normal machine — the sandbox proxy blocks it).
- Compile line/branch coverage once outputs are compiled against the real
  Defects4J checkout.
