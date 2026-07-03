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
    void testAppendFixedWidthPadRight_NormalPadding() {
        strBuilder.appendFixedWidthPadRight("abc", 5, '0');
        assertEquals("abc00", strBuilder.toString());
    }

    @Test
    void testAppendFixedWidthPadRight_Truncation() {
        strBuilder.appendFixedWidthPadRight("abcdefg", 5, '0');
        assertEquals("abcde", strBuilder.toString());
    }

    @Test
    void testAppendFixedWidthPadRight_ExactWidth() {
        strBuilder.appendFixedWidthPadRight("abcde", 5, '0');
        assertEquals("abcde", strBuilder.toString());
    }

    @Test
    void testAppendFixedWidthPadRight_WidthLessThanOrEqualToZero() {
        strBuilder.appendFixedWidthPadRight("abcde", 0, '0');
        assertEquals("", strBuilder.toString());

        strBuilder = new StrBuilder();
        strBuilder.appendFixedWidthPadRight("abcde", -1, '0');
        assertEquals("", strBuilder.toString());
    }

    @Test
    void testAppendFixedWidthPadRight_NullObject() {
        strBuilder.appendFixedWidthPadRight(null, 5, '0');
        assertEquals("", strBuilder.toString());
    }

    @Test
    void testAppendFixedWidthPadRight_Chaining() {
        strBuilder.appendFixedWidthPadRight("abc", 5, '0').appendFixedWidthPadRight("def", 5, '0');
        assertEquals("abc00def00", strBuilder.toString());
    }
}
