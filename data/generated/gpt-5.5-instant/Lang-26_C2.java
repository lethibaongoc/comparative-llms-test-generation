@Test
public void testFormat_epochInUtc() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd HH:mm:ss",
        TimeZone.getTimeZone("UTC"),
        Locale.US
    );

    assertEquals("1970-01-01 00:00:00", format.format(new Date(0L)));
}

@Test
public void testFormat_usesConfiguredTimeZone() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd HH:mm:ss",
        TimeZone.getTimeZone("GMT+07:00"),
        Locale.US
    );

    assertEquals("1970-01-01 07:00:00", format.format(new Date(0L)));
}

@Test
public void testFormat_formatsLeapDay() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd",
        TimeZone.getTimeZone("UTC"),
        Locale.US
    );
    Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
    calendar.clear();
    calendar.set(2024, Calendar.FEBRUARY, 29, 12, 30, 45);

    assertEquals("2024-02-29", format.format(calendar.getTime()));
}

@Test
public void testFormat_usesConfiguredLocale() {
    FastDateFormat format = FastDateFormat.getInstance(
        "EEEE, MMMM d, yyyy",
        TimeZone.getTimeZone("UTC"),
        Locale.US
    );
    Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
    calendar.clear();
    calendar.set(2023, Calendar.JULY, 4, 0, 0, 0);

    assertEquals("Tuesday, July 4, 2023", format.format(calendar.getTime()));
}

@Test
public void testFormat_preservesMilliseconds() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd HH:mm:ss.SSS",
        TimeZone.getTimeZone("UTC"),
        Locale.US
    );

    assertEquals("1970-01-01 00:00:01.234", format.format(new Date(1234L)));
}

@Test
public void testFormat_nullDateThrowsNullPointerException() {
    FastDateFormat format = FastDateFormat.getInstance(
        "yyyy-MM-dd",
        TimeZone.getTimeZone("UTC"),
        Locale.US
    );

    assertThrows(NullPointerException.class, () -> format.format((Date) null));
}
