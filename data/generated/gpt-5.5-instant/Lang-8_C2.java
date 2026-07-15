@Test
void testAppendToUsesStandardTimeZoneDisplayName() throws Exception {
    TimeZone zone = TimeZone.getTimeZone("GMT");
    Locale locale = Locale.US;

    Class<?> ruleClass = Class.forName(
            "org.apache.commons.lang3.time.FastDatePrinter$TimeZoneNameRule");
    java.lang.reflect.Constructor<?> constructor
            = ruleClass.getDeclaredConstructor(TimeZone.class, Locale.class,
                    int.class);
    constructor.setAccessible(true);
    Object rule = constructor.newInstance(zone, locale, TimeZone.LONG);

    Calendar calendar = Calendar.getInstance(zone, locale);
    calendar.set(2024, Calendar.JANUARY, 15);

    StringBuffer buffer = new StringBuffer();
    java.lang.reflect.Method appendTo = ruleClass.getDeclaredMethod(
            "appendTo", StringBuffer.class, Calendar.class);
    appendTo.setAccessible(true);
    appendTo.invoke(rule, buffer, calendar);

    assertEquals(zone.getDisplayName(false, TimeZone.LONG, locale),
            buffer.toString());
}

@Test
void testAppendToUsesDaylightTimeZoneDisplayName() throws Exception {
    TimeZone zone = TimeZone.getTimeZone("America/New_York");
    Locale locale = Locale.US;

    Class<?> ruleClass = Class.forName(
            "org.apache.commons.lang3.time.FastDatePrinter$TimeZoneNameRule");
    java.lang.reflect.Constructor<?> constructor
            = ruleClass.getDeclaredConstructor(TimeZone.class, Locale.class,
                    int.class);
    constructor.setAccessible(true);
    Object rule = constructor.newInstance(zone, locale, TimeZone.LONG);

    Calendar calendar = Calendar.getInstance(zone, locale);
    calendar.clear();
    calendar.set(2024, Calendar.JULY, 15, 12, 0, 0);

    assertNotEquals(0, calendar.get(Calendar.DST_OFFSET));

    StringBuffer buffer = new StringBuffer();
    java.lang.reflect.Method appendTo = ruleClass.getDeclaredMethod(
            "appendTo", StringBuffer.class, Calendar.class);
    appendTo.setAccessible(true);
    appendTo.invoke(rule, buffer, calendar);

    assertEquals(zone.getDisplayName(true, TimeZone.LONG, locale),
            buffer.toString());
}
