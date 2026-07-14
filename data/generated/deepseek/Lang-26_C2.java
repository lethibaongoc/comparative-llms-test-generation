@Test
void testFormatWithDate() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
    Date date = new Date(0); // 1970-01-01
    String result = formatter.format(date);
    assertNotNull(result);
    assertTrue(result.startsWith("1970-01-"));
}

@Test
void testFormatWithTimeZone() {
    FastDateFormat formatter = FastDateFormat.getInstance("HH:mm:ss", TimeZone.getTimeZone("GMT"));
    Date date = new Date(0);
    String result = formatter.format(date);
    assertEquals("00:00:00", result);
}

@Test
void testFormatWithLocale() {
    FastDateFormat formatter = FastDateFormat.getInstance("EEEE", Locale.FRANCE);
    Date date = new Date(0);
    String result = formatter.format(date);
    assertEquals("jeudi", result);
}

@Test
void testFormatWithCustomPattern() {
    FastDateFormat formatter = FastDateFormat.getInstance("MM/dd/yyyy HH:mm");
    Date date = new Date(0);
    String result = formatter.format(date);
    assertNotNull(result);
    assertTrue(result.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}"));
}

@Test
void testFormatWithNullDate() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
    assertThrows(NullPointerException.class, () -> formatter.format((Date) null));
}

@Test
void testFormatMultipleTimes() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy");
    Date date1 = new Date(0);
    Date date2 = new Date(1000000000000L);
    String result1 = formatter.format(date1);
    String result2 = formatter.format(date2);
    assertNotEquals(result1, result2);
    assertEquals("1970", result1);
    assertEquals("2001", result2);
}

@Test
void testFormatWithTimeZoneConversion() {
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("America/New_York"));
    Date date = new Date(0);
    String result = formatter.format(date);
    assertNotNull(result);
    assertFalse(result.contains("1970-01-01 00:00:00"));
}
