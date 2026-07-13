package org.apache.commons.lang3;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @Test
    public void testContainsAny_EmptyString() {
        assertFalse(StringUtils.containsAny("", new char[] {'a', 'b', 'c'}));
    }

    @Test
    public void testContainsAny_EmptySearchChars() {
        assertFalse(StringUtils.containsAny("abc", new char[] {}));
    }

    @Test
    public void testContainsAny_NoMatch() {
        assertFalse(StringUtils.containsAny("abc", new char[] {'d', 'e', 'f'}));
    }

    @Test
    public void testContainsAny_Match() {
        assertTrue(StringUtils.containsAny("abc", new char[] {'a', 'b', 'c'}));
    }

    @Test
    public void testContainsAny_MatchAtStart() {
        assertTrue(StringUtils.containsAny("abc", new char[] {'a'}));
    }

    @Test
    public void testContainsAny_MatchAtEnd() {
        assertTrue(StringUtils.containsAny("abc", new char[] {'c'}));
    }

    @Test
    public void testContainsAny_MatchInMiddle() {
        assertTrue(StringUtils.containsAny("abc", new char[] {'b'}));
    }

    @Test
    public void testContainsAny_SupplementaryChars() {
        char[] searchChars = new char[] {'\uD800', '\uDC00'};
        assertTrue(StringUtils.containsAny("𐀀", searchChars));
    }

    @Test
    public void testContainsAny_SupplementaryChars_MissingLowSurrogate() {
        char[] searchChars = new char[] {'\uD800'};
        assertTrue(StringUtils.containsAny("\uD800", searchChars));
    }

    @Test
    public void testContainsAny_SupplementaryChars_Match() {
        char[] searchChars = new char[] {'\uD800', '\uDC00'};
        assertTrue(StringUtils.containsAny("𐀀", searchChars));
    }
}
