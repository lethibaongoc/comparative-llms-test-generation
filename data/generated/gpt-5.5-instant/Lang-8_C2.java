@Test
public void testAppendTo_daylightSavingTime() {
    TimeZone zone = TimeZone.getTimeZone("America/New_York");
    Calendar calendar = Calendar.getInstance(zone, Locale.US);
    calendar.set(2024, Calendar.JULY, 15, 12, 0, 0);

    FastDatePrinter printer =
        new FastDatePrinter("z", zone, Locale.US);
    StringBuffer buffer = new StringBuffer();

    printer.appendTo(buffer, calendar);

    assertEquals(zone.getDisplayName(true, TimeZone.SHORT, Locale.US),
        buffer.toString());
}

@Test
public void testAppendTo_standardTime() {
    TimeZone zone = TimeZone.getTimeZone("America/New_York");
    Calendar calendar = Calendar.getInstance(zone, Locale.US);
    calendar.set(2024, Calendar.JANUARY, 15, 12, 0, 0);

    FastDatePrinter printer =
        new FastDatePrinter("z", zone, Locale.US);
    StringBuffer buffer = new StringBuffer();

    printer.appendTo(buffer, calendar);

    assertEquals(zone.getDisplayName(false, TimeZone.SHORT, Locale.US),
        buffer.toString());
}

@Test
public void testAppendTo_zoneWithoutDaylightSavingTime() {
    TimeZone zone = TimeZone.getTimeZone("UTC");
    Calendar calendar = Calendar.getInstance(zone, Locale.US);
    calendar.set(2024, Calendar.JULY, 15, 12, 0, 0);

    FastDatePrinter printer =
        new FastDatePrinter("z", zone, Locale.US);
    StringBuffer buffer = new StringBuffer();

    printer.appendTo(buffer, calendar);

    assertEquals(zone.getDisplayName(false, TimeZone.SHORT, Locale.US),
        buffer.toString());
}

@Test
public void testAppendTo_appendsToExistingBufferContent() {
    TimeZone zone = TimeZone.getTimeZone("UTC");
    Calendar calendar = Calendar.getInstance(zone, Locale.US);

    FastDatePrinter printer =
        new FastDatePrinter("z", zone, Locale.US);
    StringBuffer buffer = new StringBuffer("Zone: ");

    printer.appendTo(buffer, calendar);

    assertEquals("Zone: "
        + zone.getDisplayName(false, TimeZone.SHORT, Locale.US),
        buffer.toString());
}
