package org.apache.commons.lang3.time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {

    @Test
    void testIsSameLocalTime_SameTime() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        Calendar cal2 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        assertTrue(DateUtils.isSameLocalTime(cal1, cal2));
    }

    @Test
    void testIsSameLocalTime_DifferentTime() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        Calendar cal2 = new GregorianCalendar(2022, 0, 1, 13, 0, 0);
        assertFalse(DateUtils.isSameLocalTime(cal1, cal2));
    }

    @Test
    void testIsSameLocalTime_NullCalendar() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> DateUtils.isSameLocalTime(cal1, null));
    }

    @Test
    void testIsSameLocalTime_DifferentCalendarClass() {
        Calendar cal1 = new GregorianCalendar(2022, 0, 1, 12, 0, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(cal1.getTimeInMillis());
        assertTrue(DateUtils.isSameLocalTime(cal1, cal2));
    }
}
