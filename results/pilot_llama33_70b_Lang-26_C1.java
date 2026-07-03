package org.apache.commons.lang3.time;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FastDateFormatTest {

    private FastDateFormat fastDateFormat;

    @BeforeEach
    public void setup() {
        fastDateFormat = new FastDateFormat("yyyy-MM-dd HH:mm:ss", TimeZone.getDefault(), Locale.getDefault());
    }

    @Test
    public void testFormat_Date() {
        Date date = new GregorianCalendar(2022, 0, 1, 12, 0, 0).getTime();
        String formattedDate = fastDateFormat.format(date);
        assertNotNull(formattedDate);
        assertTrue(formattedDate.length() > 0);
    }

    @Test
    public void testFormat_Date_Null() {
        assertThrows(NullPointerException.class, () -> fastDateFormat.format(null));
    }

    @Test
    public void testFormat_Date_Twice() {
        Date date = new GregorianCalendar(2022, 0, 1, 12, 0, 0).getTime();
        String formattedDate1 = fastDateFormat.format(date);
        String formattedDate2 = fastDateFormat.format(date);
        assertEquals(formattedDate1, formattedDate2);
    }
}
