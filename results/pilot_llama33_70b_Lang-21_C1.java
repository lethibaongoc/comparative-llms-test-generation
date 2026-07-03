package org.apache.commons.lang3.time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {

    @Test
    public void testIsSameLocalTime_SameTime_ReturnsTrue() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        Calendar cal2 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        assertTrue(DateUtils.isSameLocalTime(cal1, cal2));
    }

    @Test
    public void testIsSameLocalTime_DifferentTime_ReturnsFalse() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        Calendar cal2 = new GregorianCalendar(2022, 0, 1, 13, 0, 0);
        assertFalse(DateUtils.isSameLocalTime(cal1, cal2));
    }

    @Test
    public void testIsSameLocalTime_NullCalendar_ThrowsIllegalArgumentException() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        Calendar cal2 = null;
        assertThrows(IllegalArgumentException.class, () -> DateUtils.isSameLocalTime(cal1, cal2));
    }

    @Test
    public void testIsSameLocalTime_DifferentCalendarType_ReturnsFalse() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2022, 0, 1, 12, 0, 0);
        assertTrue(DateUtils.isSameLocalTime(cal1, cal2));
    }

    @Test
    public void testIsSameLocalTime_SameTimeDifferentYear_ReturnsFalse() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        Calendar cal2 = new GregorianCalendar(2023, 0, 1, 12, 0, 0);
        assertFalse(DateUtils.isSameLocalTime(cal1, cal2));
    }
}
