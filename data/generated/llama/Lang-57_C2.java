package org.apache.commons.lang;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Locale;

public class LocaleUtilsTest {

    @Test
    void testIsAvailableLocale_AvailableLocale_ReturnsTrue() {
        Locale locale = Locale.US;
        assertTrue(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    void testIsAvailableLocale_UnavailableLocale_ReturnsFalse() {
        Locale locale = new Locale("xx", "XX");
        if (!LocaleUtils.isAvailableLocale(locale)) {
            assertTrue(true);
        } else {
            fail("Locale xx_XX is available");
        }
    }

    @Test
    void testIsAvailableLocale_NullLocale_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> LocaleUtils.isAvailableLocale(null));
    }
}
