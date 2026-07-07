package org.apache.commons.lang;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Locale;

public class LocaleUtilsTest {

    @Test
    public void testIsAvailableLocale_AvailableLocale_ReturnsTrue() {
        Locale locale = Locale.getDefault();
        assertTrue(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testIsAvailableLocale_UnavailableLocale_ReturnsFalse() {
        Locale locale = new Locale("xx", "XX");
        assertFalse(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testIsAvailableLocale_NullLocale_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> LocaleUtils.isAvailableLocale(null));
    }
}
