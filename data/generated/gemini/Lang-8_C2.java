@Test
void testAppendTo_DaylightSavingTimeActive() {
    TimeZone tz = TimeZone.getTimeZone("America/New_York");
    Calendar calendar = Calendar.getInstance(tz);
    // July is during Daylight Saving Time in New York
    calendar.set(2026, Calendar.JULY, 1, 12, 0, 0);
    calendar.set(Calendar.DST_OFFSET, 3600000); // 1 hour offset

    FastDatePrinter printer = new FastDatePrinter("zzz", tz, Locale.US);
    StringBuffer buffer = new StringBuffer();
    printer.appendTo(buffer, calendar);

    assertEquals("EDT", buffer.toString());
}

@Test
void testAppendTo_StandardTime() {
    TimeZone tz = TimeZone.getTimeZone("America/New_York");
    Calendar calendar = Calendar.getInstance(tz);
    // December is Standard Time in New York
    calendar.set(2026, Calendar.DECEMBER, 1, 12, 0, 0);
    calendar.set(Calendar.DST_OFFSET, 0);

    FastDatePrinter printer = new FastDatePrinter("zzz", tz, Locale.US);
    StringBuffer buffer = new StringBuffer();
    printer.appendTo(buffer, calendar);

    assertEquals("EST", buffer.toString());
}

@Test
void testAppendTo_TimeZoneWithoutDaylightSavings() {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    Calendar calendar = Calendar.getInstance(tz);
    calendar.set(Calendar.DST_OFFSET, 0);

    FastDatePrinter printer = new FastDatePrinter("zzz", tz, Locale.US);
    StringBuffer buffer = new StringBuffer();
    printer.appendTo(buffer, calendar);

    assertEquals("UTC", buffer.toString());
}
