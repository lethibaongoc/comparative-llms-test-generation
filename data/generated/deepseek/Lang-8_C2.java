package org.apache.commons.lang3.time;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import static org.junit.jupiter.api.Assertions.*;

public class FastDatePrinterTest {

    @Test
    void testAppendToDaylightTime() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Locale locale = Locale.US;
        int style = TimeZone.LONG;

        Object rule = createTimeZoneNameRule(tz, locale, style);
        StringBuffer buffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance(tz);
        calendar.set(2024, Calendar.JULY, 1, 12, 0, 0);

        invokeAppendTo(rule, buffer, calendar);

        String expected = FastDatePrinter.getTimeZoneDisplay(tz, true, style, locale);
        assertEquals(expected, buffer.toString());
    }

    @Test
    void testAppendToStandardTime() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Locale locale = Locale.US;
        int style = TimeZone.LONG;

        Object rule = createTimeZoneNameRule(tz, locale, style);
        StringBuffer buffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance(tz);
        calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);

        invokeAppendTo(rule, buffer, calendar);

        String expected = FastDatePrinter.getTimeZoneDisplay(tz, false, style, locale);
        assertEquals(expected, buffer.toString());
    }

    @Test
    void testAppendToNoDST() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        Locale locale = Locale.US;
        int style = TimeZone.LONG;

        Object rule = createTimeZoneNameRule(tz, locale, style);
        StringBuffer buffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance(tz);
        calendar.set(2024, Calendar.JULY, 1, 12, 0, 0);

        invokeAppendTo(rule, buffer, calendar);

        String expected = FastDatePrinter.getTimeZoneDisplay(tz, false, style, locale);
        assertEquals(expected, buffer.toString());
    }

    @Test
    void testAppendToShortStyle() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Locale locale = Locale.US;
        int style = TimeZone.SHORT;

        Object rule = createTimeZoneNameRule(tz, locale, style);
        StringBuffer buffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance(tz);
        calendar.set(2024, Calendar.JULY, 1, 12, 0, 0);

        invokeAppendTo(rule, buffer, calendar);

        String expected = FastDatePrinter.getTimeZoneDisplay(tz, true, style, locale);
        assertEquals(expected, buffer.toString());
    }

    @Test
    void testAppendToEmptyBuffer() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Locale locale = Locale.US;
        int style = TimeZone.LONG;

        Object rule = createTimeZoneNameRule(tz, locale, style);
        StringBuffer buffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance(tz);
        calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);

        assertEquals(0, buffer.length());
        invokeAppendTo(rule, buffer, calendar);
        assertTrue(buffer.length() > 0);
    }

    private Object createTimeZoneNameRule(TimeZone tz, Locale locale, int style) throws Exception {
        Class<?> innerClass = Class.forName("org.apache.commons.lang3.time.FastDatePrinter$TimeZoneNameRule");
        Constructor<?> constructor = innerClass.getDeclaredConstructor(TimeZone.class, Locale.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(tz, locale, style);
    }

    private void invokeAppendTo(Object rule, StringBuffer buffer, Calendar calendar) throws Exception {
        Method method = rule.getClass().getDeclaredMethod("appendTo", StringBuffer.class, Calendar.class);
        method.setAccessible(true);
        method.invoke(rule, buffer, calendar);
    }
}
