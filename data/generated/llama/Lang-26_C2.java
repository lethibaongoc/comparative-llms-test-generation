package org.apache.commons.lang3.time;

import java.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FastDateFormatTest {

    private FastDateFormat formatter;

    @BeforeEach
    void setUp() {
        formatter = new FastDateFormat();
    }

    @Test
    void testFormat() {
        Date date = new Date();
        String formattedDate = formatter.format(date);
        assertNotNull(formattedDate);
    }

    @Test
    void testFormatWithNullDate() {
        assertThrows(NullPointerException.class, () -> formatter.format(null));
    }

    @Test
    void testFormatWithDifferentTimeZone() {
        formatter = new FastDateFormat(TimeZone.getTimeZone("GMT"), Locale.getDefault(), 20);
        Date date = new Date();
        String formattedDate = formatter.format(date);
        assertNotNull(formattedDate);
    }

    @Test
    void testFormatWithDifferentLocale() {
        formatter = new FastDateFormat(TimeZone.getDefault(), Locale.US, 20);
        Date date = new Date();
        String formattedDate = formatter.format(date);
        assertNotNull(formattedDate);
    }
}
