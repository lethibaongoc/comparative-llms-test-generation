package org.apache.commons.lang3.math;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class NumberUtilsTest {

    @Test
    void testCreateNumber_LowercaseHex() {
        Number result = NumberUtils.createNumber("0x1A");
        assertEquals(26, result.intValue());
    }

    @Test
    void testCreateNumber_NegativeLowercaseHex() {
        Number result = NumberUtils.createNumber("-0x1A");
        assertEquals(-26, result.intValue());
    }

    @Test
    void testCreateNumber_UppercaseHex() {
        Number result = NumberUtils.createNumber("0X1A");
        assertEquals(26, result.intValue());
    }

    @Test
    void testCreateNumber_NegativeUppercaseHex() {
        Number result = NumberUtils.createNumber("-0X1A");
        assertEquals(-26, result.intValue());
    }

    @Test
    void testCreateNumber_PlainDecimalInteger() {
        Number result = NumberUtils.createNumber("123");
        assertEquals(123, result.intValue());
    }

    @Test
    void testCreateNumber_NullInput() {
        Number result = NumberUtils.createNumber(null);
        assertNull(result);
    }
}
