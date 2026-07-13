package org.apache.commons.lang3.time;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

public class FastDatePrinterTest {

    @Test
    public void testAppendToWithDaylightTime() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Locale locale = Locale.US;
        int style = TimeZone.LONG;

        Object rule = createTimeZoneNameRule(tz, locale, style);

        Calendar calendar = Calendar.getInstance(tz, locale);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        StringBuffer buffer = new StringBuffer();
        invokeAppendTo(rule, buffer, calendar);

        String expected = FastDatePrinter.getTimeZoneDisplay(tz, true, style, locale);
        assertEquals(expected, buffer.toString());
    }

    @Test
    public void testAppendToWithoutDaylightTime() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Locale locale = Locale.US;
        int style = TimeZone.LONG;

        Object rule = createTimeZoneNameRule(tz, locale, style);

        Calendar calendar = Calendar.getInstance(tz, locale);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        StringBuffer buffer = new StringBuffer();
        invokeAppendTo(rule, buffer, calendar);

        String expected = FastDatePrinter.getTimeZoneDisplay(tz, false, style, locale);
        assertEquals(expected, buffer.toString());
    }

    @Test
    public void testAppendToWithNoDSTSupport() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        Locale locale = Locale.US;
        int style = TimeZone.LONG;

        Object rule = createTimeZoneNameRule(tz, locale, style);

        Calendar calendar = Calendar.getInstance(tz, locale);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        StringBuffer buffer = new StringBuffer();
        invokeAppendTo(rule, buffer, calendar);

        String expected = FastDatePrinter.getTimeZoneDisplay(tz, false, style, locale);
        assertEquals(expected, buffer.toString());
    }

    @Test
    public void testAppendToWithShortStyle() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Locale locale = Locale.US;
        int style = TimeZone.SHORT;

        Object rule = createTimeZoneNameRule(tz, locale, style);

        Calendar calendar = Calendar.getInstance(tz, locale);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        StringBuffer buffer = new StringBuffer();
        invokeAppendTo(rule, buffer, calendar);

        String expected = FastDatePrinter.getTimeZoneDisplay(tz, true, style, locale);
        assertEquals(expected, buffer.toString());
    }

    private Object createTimeZoneNameRule(TimeZone tz, Locale locale, int style) throws Exception {
        Class<?> innerClass = Class.forName("org.apache.commons.lang3.time.FastDatePrinter$TimeZoneNameRule");
        Constructor<?> constructor = innerClass.getDeclaredConstructor(TimeZone.class, Locale.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(tz, locale, style);
    }

    private void invokeAppendTo(Object rule, StringBuffer buffer, Calendar calendar) throws Exception {
        java.lang.reflect.Method method = rule.getClass().getDeclaredMethod("appendTo", StringBuffer.class, Calendar.class);
        method.setAccessible(true);
        method.invoke(rule, buffer, calendar);
    }
}
