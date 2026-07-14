@Test
void testFormatWithDate() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
    Date date = new Date(2023 - 1900, 5, 15);
    assertEquals("2023-06-15", formatter.format(date));
}

@Test
void testFormatWithSpecificTimeZone() {
    TimeZone tz = TimeZone.getTimeZone("America/New_York");
    FastDateFormat formatter = FastDateFormat.getInstance("HH:mm", tz);
    Date date = new Date(2023 - 1900, 5, 15, 14, 30);
    assertEquals("14:30", formatter.format(date));
}

@Test
void testFormatWithLocale() {
    Locale locale = Locale.GERMANY;
    FastDateFormat formatter = FastDateFormat.getInstance("EEEE", locale);
    Date date = new Date(2023 - 1900, 5, 15);
    assertEquals("Donnerstag", formatter.format(date));
}

@Test
void testFormatWithNullDate() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
    assertThrows(NullPointerException.class, () -> formatter.format(null));
}

@Test
void testFormatWithComplexPattern() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = new Date(2023 - 1900, 5, 15, 10, 30, 45);
    String result = formatter.format(date);
    assertTrue(result.matches("2023-06-15 10:30:45\\.\\d{3}"));
}

@Test
void testFormatWithEpochDate() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
    Date date = new Date(0);
    assertEquals("1970-01-01", formatter.format(date));
}

@Test
void testFormatWithLeapYear() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
    Date date = new Date(2020 - 1900, 1, 29);
    assertEquals("2020-02-29", formatter.format(date));
}

@Test
void testFormatWithDifferentTimeZones() {
    TimeZone utc = TimeZone.getTimeZone("UTC");
    TimeZone est = TimeZone.getTimeZone("America/New_York");
    FastDateFormat formatterUtc = FastDateFormat.getInstance("yyyy-MM-dd HH:mm", utc);
    FastDateFormat formatterEst = FastDateFormat.getInstance("yyyy-MM-dd HH:mm", est);
    Date date = new Date(2023 - 1900, 5, 15, 12, 0);
    assertNotEquals(formatterUtc.format(date), formatterEst.format(date));
}

@Test
void testFormatWithMaxLengthEstimate() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSSZ");
    Date date = new Date(2023 - 1900, 5, 15, 10, 30, 45);
    String result = formatter.format(date);
    assertNotNull(result);
    assertFalse(result.isEmpty());
}
