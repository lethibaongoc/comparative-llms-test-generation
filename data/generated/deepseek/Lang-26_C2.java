@Test
public void testFormat_validDate() {
    Date date = new Date(1640995200000L); // 2022-01-01 00:00:00 UTC
    String result = fastDateFormat.format(date);
    assertNotNull(result);
}

@Test
public void testFormat_nullDate() {
    assertThrows(NullPointerException.class, () -> {
        fastDateFormat.format(null);
    });
}

@Test
public void testFormat_dateWithSpecificTimeZone() {
    FastDateFormat fastDateFormat = new FastDateFormat(TimeZone.getTimeZone("America/New_York"), Locale.US);
    Date date = new Date(1640995200000L);
    String result = fastDateFormat.format(date);
    assertNotNull(result);
}

@Test
public void testFormat_dateWithDifferentLocale() {
    FastDateFormat fastDateFormat = new FastDateFormat(TimeZone.getTimeZone("UTC"), Locale.FRANCE);
    Date date = new Date(1640995200000L);
    String result = fastDateFormat.format(date);
    assertNotNull(result);
}

@Test
public void testFormat_epochDate() {
    Date epoch = new Date(0);
    String result = fastDateFormat.format(epoch);
    assertNotNull(result);
}

@Test
public void testFormat_futureDate() {
    Date future = new Date(4102444800000L); // 2100-01-01
    String result = fastDateFormat.format(future);
    assertNotNull(result);
}

@Test
public void testFormat_pastDate() {
    Date past = new Date(-86400000L); // 1969-12-31
    String result = fastDateFormat.format(past);
    assertNotNull(result);
}

@Test
public void testFormat_dateWithMaxLengthEstimate() {
    Date date = new Date();
    String result = fastDateFormat.format(date);
    assertNotNull(result);
}
