package org.apache.commons.lang.text;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StrBuilderTest {

    @Test
    void testContains_Present() {
        StrBuilder strBuilder = new StrBuilder();
        strBuilder.buffer = new char[] {'a', 'b', 'c'};
        strBuilder.size = 3;
        assertTrue(strBuilder.contains('a'));
        assertTrue(strBuilder.contains('b'));
        assertTrue(strBuilder.contains('c'));
    }

    @Test
    void testContains_NotPresent() {
        StrBuilder strBuilder = new StrBuilder();
        strBuilder.buffer = new char[] {'a', 'b', 'c'};
        strBuilder.size = 3;
        assertFalse(strBuilder.contains('d'));
    }

    @Test
    void testContains_Empty() {
        StrBuilder strBuilder = new StrBuilder();
        strBuilder.buffer = new char[] {};
        strBuilder.size = 0;
        assertFalse(strBuilder.contains('a'));
    }

    @Test
    void testContains_NullBuffer() {
        StrBuilder strBuilder = new StrBuilder();
        strBuilder.buffer = null;
        strBuilder.size = 0;
        assertDoesNotThrow(() -> strBuilder.contains('a'));
        assertFalse(strBuilder.contains('a'));
    }
}
