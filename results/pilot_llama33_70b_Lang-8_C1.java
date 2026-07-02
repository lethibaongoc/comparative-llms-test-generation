package org.apache.commons.lang3.time;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class FastDatePrinterTest {

    @Test
    public void testAppendTo_StandardTime() {
        // Arrange
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
        calendar.set(Calendar.MONTH, Calendar.JANUARY); // Standard time
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        FastDatePrinter.TimeZoneNameRule rule = new FastDatePrinter.TimeZoneNameRule(TimeZone.getTimeZone("America/New_York"), Locale.US, 0);
        StringBuffer buffer = new StringBuffer();

        // Act
        rule.appendTo(buffer, calendar);

        // Assert
        assertEquals("EST", buffer.toString());
    }

    @Test
    public void testAppendTo_DaylightTime() {
        // Arrange
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
        calendar.set(Calendar.MONTH, Calendar.JUNE); // Daylight time
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        FastDatePrinter.TimeZoneNameRule rule = new FastDatePrinter.TimeZoneNameRule(TimeZone.getTimeZone("America/New_York"), Locale.US, 0);
        StringBuffer buffer = new StringBuffer();

        // Act
        rule.appendTo(buffer, calendar);

        // Assert
        assertEquals("EDT", buffer.toString());
    }

    @Test
    public void testAppendTo_TimeZoneWithoutDaylightSaving() {
        // Arrange
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        FastDatePrinter.TimeZoneNameRule rule = new FastDatePrinter.TimeZoneNameRule(TimeZone.getTimeZone("UTC"), Locale.US, 0);
        StringBuffer buffer = new StringBuffer();

        // Act
        rule.appendTo(buffer, calendar);

        // Assert
        assertEquals("UTC", buffer.toString());
    }
}
