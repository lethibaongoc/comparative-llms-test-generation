package org.apache.commons.lang3.time;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;



public class FastDatePrinterTest {

    @Test
    void testAppendTo_DaylightTime() {
        // Arrange
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        FastDatePrinter.TimeZoneNameRule rule = new FastDatePrinter.TimeZoneNameRule(TimeZone.getTimeZone("America/New_York"), Locale.US, 0);

        // Act
        StringBuffer buffer = new StringBuffer();
        rule.appendTo(buffer, calendar);

        // Assert
        assertEquals("EDT", buffer.toString());
    }

    @Test
    void testAppendTo_StandardTime() {
        // Arrange
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        FastDatePrinter.TimeZoneNameRule rule = new FastDatePrinter.TimeZoneNameRule(TimeZone.getTimeZone("America/New_York"), Locale.US, 0);

        // Act
        StringBuffer buffer = new StringBuffer();
        rule.appendTo(buffer, calendar);

        // Assert
        assertEquals("EST", buffer.toString());
    }

    @Test
    void testAppendTo_NoDaylightTime() {
        // Arrange
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        FastDatePrinter.TimeZoneNameRule rule = new FastDatePrinter.TimeZoneNameRule(TimeZone.getTimeZone("UTC"), Locale.US, 0);

        // Act
        StringBuffer buffer = new StringBuffer();
        rule.appendTo(buffer, calendar);

        // Assert
        assertEquals("UTC", buffer.toString());
    }
}
