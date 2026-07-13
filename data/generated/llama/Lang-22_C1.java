package org.apache.commons.lang3.math;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FractionTest {

    @Test
    public void testGreatestCommonDivisor_ZeroAndZero_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(0, 0));
    }

    @Test
    public void testGreatestCommonDivisor_MinIntAndZero_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 0));
    }

    @Test
    public void testGreatestCommonDivisor_OneAndOne_ReturnsOne() {
        assertEquals(1, Fraction.greatestCommonDivisor(1, 1));
    }

    @Test
    public void testGreatestCommonDivisor_OneAndTwo_ReturnsOne() {
        assertEquals(1, Fraction.greatestCommonDivisor(1, 2));
    }

    @Test
    public void testGreatestCommonDivisor_TwoAndTwo_ReturnsTwo() {
        assertEquals(2, Fraction.greatestCommonDivisor(2, 2));
    }

    @Test
    public void testGreatestCommonDivisor_FourAndSix_ReturnsTwo() {
        assertEquals(2, Fraction.greatestCommonDivisor(4, 6));
    }

    @Test
    public void testGreatestCommonDivisor_NegativeOneAndOne_ReturnsOne() {
        assertEquals(1, Fraction.greatestCommonDivisor(-1, 1));
    }

    @Test
    public void testGreatestCommonDivisor_NegativeTwoAndTwo_ReturnsTwo() {
        assertEquals(2, Fraction.greatestCommonDivisor(-2, 2));
    }

    @Test
    public void testGreatestCommonDivisor_NegativeFourAndNegativeSix_ReturnsTwo() {
        assertEquals(2, Fraction.greatestCommonDivisor(-4, -6));
    }

    @Test
    public void testGreatestCommonDivisor_LargeNumbers_ReturnsCorrectResult() {
        assertEquals(1, Fraction.greatestCommonDivisor(12345, 67890));
    }
}
