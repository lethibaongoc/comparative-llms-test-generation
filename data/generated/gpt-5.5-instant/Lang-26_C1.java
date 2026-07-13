@Test
public void testFormat_validDate_returnsExpectedFormattedString() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd",
        TimeZone.getTimeZone("UTC"),
        Locale.US);

    Date date = new Date(0L);

    assertEquals("1970-01-01", format.format(date));
}

@Test
public void testFormat_dateWithTime_returnsExpectedFormattedString() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd HH:mm:ss",
        TimeZone.getTimeZone("UTC"),
        Locale.US);

    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
    calendar.clear();
    calendar.set(2023, Calendar.JULY, 15, 10, 20, 30);

    assertEquals("2023-07-15 10:20:30", format.format(calendar.getTime()));
}

@Test
public void testFormat_nullDate_throwsNullPointerException() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd",
        TimeZone.getTimeZone("UTC"),
        Locale.US);

    assertThrows(NullPointerException.class, () -> format.format((Date) null));
}

@Test
public void testFormat_sameDateCalledTwice_returnsConsistentResult() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd HH:mm:ss",
        TimeZone.getTimeZone("UTC"),
        Locale.US);

    Date date = new Date(123456789L);

    String first = format.format(date);
    String second = format.format(date);

    assertEquals(first, second);
}
