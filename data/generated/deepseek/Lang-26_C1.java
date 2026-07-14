@Test
void testFormatWithDefaultTimeZoneAndLocale() {
    FastDateFormat formatter = new FastDateFormat("yyyy-MM-dd", TimeZone.getDefault(), Locale.getDefault());
    Date date = new Date(1640995200000L); // 2022-01-01 00:00:00 UTC
    String result = formatter.format(date);
    assertNotNull(result);
    assertTrue(result.matches("\\d{4}-\\d{2}-\\d{2}"));
}

@Test
void testFormatWithCustomTimeZone() {
    TimeZone tz = TimeZone.getTimeZone("America/New_York");
    FastDateFormat formatter = new FastDateFormat("HH:mm", tz, Locale.US);
    Date date = new Date(1640995200000L);
    String result = formatter.format(date);
    assertNotNull(result);
    assertTrue(result.matches("\\d{2}:\\d{2}"));
}

@Test
void testFormatWithCustomLocale() {
    FastDateFormat formatter = new FastDateFormat("EEEE", TimeZone.getDefault(), Locale.FRENCH);
    Date date = new Date(1640995200000L);
    String result = formatter.format(date);
    assertNotNull(result);
    assertFalse(result.isEmpty());
}

@Test
void testFormatWithNullDate() {
    FastDateFormat formatter = new FastDateFormat("yyyy-MM-dd", TimeZone.getDefault(), Locale.getDefault());
    assertThrows(NullPointerException.class, () -> formatter.format(null));
}

@Test
void testFormatWithEmptyPattern() {
    FastDateFormat formatter = new FastDateFormat("", TimeZone.getDefault(), Locale.getDefault());
    Date date = new Date();
    String result = formatter.format(date);
    assertNotNull(result);
    assertEquals("", result);
}

@Test
void testFormatWithComplexPattern() {
    FastDateFormat formatter = new FastDateFormat("yyyy-MM-dd HH:mm:ss.SSS z", TimeZone.getTimeZone("UTC"), Locale.US);
    Date date = new Date(1640995200000L);
    String result = formatter.format(date);
    assertNotNull(result);
    assertTrue(result.contains("2022-01-01"));
    assertTrue(result.contains("UTC"));
}

@Test
void testFormatWithMaxLengthEstimate() {
    FastDateFormat formatter = new FastDateFormat("yyyy-MM-dd", TimeZone.getDefault(), Locale.getDefault());
    Date date = new Date();
    String result = formatter.format(date);
    assertNotNull(result);
    assertTrue(result.length() <= formatter.getMaxLengthEstimate());
}

@Test
void testFormatWithDifferentYears() {
    FastDateFormat formatter = new FastDateFormat("yyyy", TimeZone.getDefault(), Locale.getDefault());
    Date date1 = new Date(946684800000L); // 2000-01-01
    Date date2 = new Date(1893456000000L); // 2030-01-01
    String result1 = formatter.format(date1);
    String result2 = formatter.format(date2);
    assertNotNull(result1);
    assertNotNull(result2);
    assertNotEquals(result1, result2);
}
