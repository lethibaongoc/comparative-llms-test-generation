@Test
void testFormatDateWithSpecifiedTimeZoneAndLocale() {
    FastDateFormat format = FastDateFormat.getInstance(
            "yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"), Locale.US);

    Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
    calendar.clear();
    calendar.set(2024, Calendar.JANUARY, 15, 10, 30, 45);

    assertEquals("2024-01-15 10:30:45", format.format(calendar.getTime()));
}

@Test
void testFormatDateAppliesConfiguredTimeZone() {
    Date epoch = new Date(0L);

    FastDateFormat utcFormat = FastDateFormat.getInstance(
            "yyyy-MM-dd HH:mm", TimeZone.getTimeZone("UTC"), Locale.US);
    FastDateFormat vietnamFormat = FastDateFormat.getInstance(
            "yyyy-MM-dd HH:mm", TimeZone.getTimeZone("GMT+07:00"), Locale.US);

    assertEquals("1970-01-01 00:00", utcFormat.format(epoch));
    assertEquals("1970-01-01 07:00", vietnamFormat.format(epoch));
}

@Test
void testFormatDateUsesConfiguredLocale() {
    FastDateFormat format = FastDateFormat.getInstance(
            "MMMM", TimeZone.getTimeZone("UTC"), Locale.US);

    Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
    calendar.clear();
    calendar.set(2024, Calendar.MARCH, 1);

    assertEquals("March", format.format(calendar.getTime()));
}

@Test
void testFormatNullDateThrowsNullPointerException() {
    FastDateFormat format = FastDateFormat.getInstance(
            "yyyy-MM-dd", TimeZone.getTimeZone("UTC"), Locale.US);

    assertThrows(NullPointerException.class, () -> format.format((Date) null));
}
