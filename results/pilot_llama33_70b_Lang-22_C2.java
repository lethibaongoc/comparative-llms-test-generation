package org.apache.commons.lang3.math;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FractionTest {

    @Test
    void testGreatestCommonDivisor_Zero() {
        assertEquals(1, Fraction.greatestCommonDivisor(0, 1));
        assertEquals(1, Fraction.greatestCommonDivisor(1, 0));
        assertEquals(0, Fraction.greatestCommonDivisor(0, 0));
    }

    @Test
    void testGreatestCommonDivisor_SimpleCases() {
        assertEquals(1, Fraction.greatestCommonDivisor(1, 1));
        assertEquals(2, Fraction.greatestCommonDivisor(2, 2));
        assertEquals(2, Fraction.greatestCommonDivisor(2, 4));
        assertEquals(2, Fraction.greatestCommonDivisor(4, 2));
    }

    @Test
    void testGreatestCommonDivisor_MultipleCases() {
        assertEquals(3, Fraction.greatestCommonDivisor(3, 6));
        assertEquals(3, Fraction.greatestCommonDivisor(6, 3));
        assertEquals(6, Fraction.greatestCommonDivisor(6, 12));
        assertEquals(6, Fraction.greatestCommonDivisor(12, 6));
    }

    @Test
    void testGreatestCommonDivisor_NegativeCases() {
        assertEquals(1, Fraction.greatestCommonDivisor(-1, 1));
        assertEquals(1, Fraction.greatestCommonDivisor(1, -1));
        assertEquals(2, Fraction.greatestCommonDivisor(-2, 2));
        assertEquals(2, Fraction.greatestCommonDivisor(2, -2));
    }

    @Test
    void testGreatestCommonDivisor_Overflow() {
        assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 1));
        assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(1, Integer.MIN_VALUE));
    }
}
