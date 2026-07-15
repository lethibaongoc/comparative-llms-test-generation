@Test
void testFormatWithDate() {
    FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    Calendar cal = new GregorianCalendar(2026, Calendar.JULY, 15, 16, 27, 24);
    Date date = cal.getTime();

    assertEquals("2026-07-15 16:27:24", fdf.format(date));
}

@Test
void testFormatNullDateThrowsException() {
    FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd");

    assertThrows(NullPointerException.class, () -> fdf.format(null));
}
