package org.apache.commons.lang3.math;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FractionTest {

    @Test
    void testGreatestCommonDivisor() {
        assertEquals(1, Fraction.greatestCommonDivisor(1, 1));
        assertEquals(2, Fraction.greatestCommonDivisor(2, 2));
        assertEquals(2, Fraction.greatestCommonDivisor(4, 6));
        assertEquals(3, Fraction.greatestCommonDivisor(9, 12));
        assertEquals(1, Fraction.greatestCommonDivisor(7, 11));
        assertEquals(1, Fraction.greatestCommonDivisor(-1, 1));
        assertEquals(1, Fraction.greatestCommonDivisor(1, -1));
        assertEquals(1, Fraction.greatestCommonDivisor(-1, -1));
        assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 1));
        assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(1, Integer.MIN_VALUE));
    }
}
