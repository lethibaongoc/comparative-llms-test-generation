@org.junit.jupiter.api.Test
void appendTo_appendsDaylightTimeZoneDisplayName_whenDstIsActive() {
    java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone("America/New_York");
    java.util.Locale locale = java.util.Locale.US;
    FastDatePrinter printer = new FastDatePrinter("z", timeZone, locale);

    java.util.Calendar calendar = java.util.Calendar.getInstance(timeZone, locale);
    calendar.set(2024, java.util.Calendar.JULY, 15, 12, 0, 0);

    StringBuffer buffer = new StringBuffer();
    printer.appendTo(buffer, calendar);

    org.junit.jupiter.api.Assertions.assertEquals(
        timeZone.getDisplayName(true, java.util.TimeZone.SHORT, locale),
        buffer.toString()
    );
}

@org.junit.jupiter.api.Test
void appendTo_appendsStandardTimeZoneDisplayName_whenDstIsNotActive() {
    java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone("America/New_York");
    java.util.Locale locale = java.util.Locale.US;
    FastDatePrinter printer = new FastDatePrinter("z", timeZone, locale);

    java.util.Calendar calendar = java.util.Calendar.getInstance(timeZone, locale);
    calendar.set(2024, java.util.Calendar.JANUARY, 15, 12, 0, 0);

    StringBuffer buffer = new StringBuffer();
    printer.appendTo(buffer, calendar);

    org.junit.jupiter.api.Assertions.assertEquals(
        timeZone.getDisplayName(false, java.util.TimeZone.SHORT, locale),
        buffer.toString()
    );
}

@org.junit.jupiter.api.Test
void appendTo_appendsStandardTimeZoneDisplayName_whenZoneDoesNotUseDaylightTime() {
    java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone("UTC");
    java.util.Locale locale = java.util.Locale.US;
    FastDatePrinter printer = new FastDatePrinter("z", timeZone, locale);

    java.util.Calendar calendar = java.util.Calendar.getInstance(timeZone, locale);

    StringBuffer buffer = new StringBuffer("Time zone: ");
    printer.appendTo(buffer, calendar);

    org.junit.jupiter.api.Assertions.assertEquals(
        "Time zone: " + timeZone.getDisplayName(
            false,
            java.util.TimeZone.SHORT,
            locale
        ),
        buffer.toString()
    );
}
