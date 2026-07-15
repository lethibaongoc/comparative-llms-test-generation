@Test
void testFormatWithValidDate() {
    // Arrange
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", mTimeZone, mLocale);
    Calendar cal = new GregorianCalendar(mTimeZone, mLocale);
    cal.set(2026, Calendar.JULY, 15, 12, 0, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date date = cal.getTime();

    // Act
    String result = formatter.format(date);

    // Assert
    assertEquals("2026-07-15 12:00:00", result);
}

@Test
void testFormatWithNullDateThrowsNullPointerException() {
    // Arrange
    FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");

    // Act & Assert
    assertThrows(NullPointerException.class, () -> formatter.format(null));
}

@Test
void testFormatRespectsTimeZone() {
    // Arrange
    TimeZone gmt = TimeZone.getTimeZone("GMT");
    TimeZone est = TimeZone.getTimeZone("EST");
    Date date = new Date(1700000000000L); // Specific epoch timestamp

    FastDateFormat formatterGmt = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", gmt);
    FastDateFormat formatterEst = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", est);

    // Act
    String resultGmt = formatterGmt.format(date);
    String resultEst = formatterEst.format(date);

    // Assert
    assertNotEquals(resultGmt, resultEst);
}

@Test
void testFormatRespectsLocale() {
    // Arrange
    Date date = new Date(1700000000000L);

    FastDateFormat formatterEn = FastDateFormat.getInstance("EEEE", Locale.ENGLISH);
    FastDateFormat formatterFr = FastDateFormat.getInstance("EEEE", Locale.FRENCH);

    // Act
    String resultEn = formatterEn.format(date);
    String resultFr = formatterFr.format(date);

    // Assert
    assertNotEquals(resultEn, resultFr);
}
