package org.apache.commons.lang.text;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StrBuilderTest {

    private StrBuilder strBuilder;

    @BeforeEach
    void setUp() {
        strBuilder = new StrBuilder();
    }

    @Test
    void testContains_CharFound() {
        strBuilder.buffer = new char[] {'a', 'b', 'c'};
        strBuilder.size = 3;
        assertTrue(strBuilder.contains('b'));
    }

    @Test
    void testContains_CharNotFound() {
        strBuilder.buffer = new char[] {'a', 'b', 'c'};
        strBuilder.size = 3;
        assertFalse(strBuilder.contains('d'));
    }

    @Test
    void testContains_EmptyBuffer() {
        strBuilder.buffer = new char[] {};
        strBuilder.size = 0;
        assertFalse(strBuilder.contains('a'));
    }

    @Test
    void testContains_NullBuffer() {
        strBuilder.buffer = null;
        strBuilder.size = 0;
        assertThrows(NullPointerException.class, () -> strBuilder.contains('a'));
    }
}
