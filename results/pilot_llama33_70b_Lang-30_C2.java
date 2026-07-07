package org.apache.commons.lang3;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @Test
    void testContainsAny() {
        assertTrue(StringUtils.containsAny("abc", new char[] {'a'}));
        assertTrue(StringUtils.containsAny("abc", new char[] {'b'}));
        assertTrue(StringUtils.containsAny("abc", new char[] {'c'}));
        assertFalse(StringUtils.containsAny("abc", new char[] {'d'}));
        assertFalse(StringUtils.containsAny("", new char[] {'a'}));
        assertFalse(StringUtils.containsAny("abc", new char[] {}));
    }

    @Test
    void testContainsAny_SupplementaryChars() {
        char[] supplementaryChars = new char[] {'\uD800', '\uDC00'};
        assertTrue(StringUtils.containsAny("𐀀", supplementaryChars));
        char[] supplementaryChars2 = new char[] {'\uD800'};
        assertTrue(StringUtils.containsAny("𐀀", supplementaryChars2));
    }

    @Test
    void testContainsAny_Null() {
        assertThrows(NullPointerException.class, () -> StringUtils.containsAny(null, new char[] {'a'}));
    }

    @Test
    void testContainsAny_SearchNull() {
        assertThrows(NullPointerException.class, () -> StringUtils.containsAny("abc", null));
    }
}
