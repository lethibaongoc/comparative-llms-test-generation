package org.apache.commons.lang.math;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FractionTest {

    @Test
    void testReduceZero() {
        Fraction fraction = Fraction.ZERO;
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.ZERO, reduced);
    }

    @Test
    void testReduceAlreadyReduced() {
        Fraction fraction = Fraction.getFraction(1, 1);
        Fraction reduced = fraction.reduce();
        assertEquals(fraction, reduced);
    }

    @Test
    void testReduceNumeratorAndDenominator() {
        Fraction fraction = Fraction.getFraction(2, 2);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(1, 1), reduced);
    }

    @Test
    void testReduceNumeratorOnly() {
        Fraction fraction = Fraction.getFraction(4, 1);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(4, 1), reduced);
    }

    @Test
    void testReduceDenominatorOnly() {
        Fraction fraction = Fraction.getFraction(1, 4);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(1, 4), reduced);
    }

    @Test
    void testReduceNegativeNumerator() {
        Fraction fraction = Fraction.getFraction(-2, 2);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(-1, 1), reduced);
    }

    @Test
    void testReduceNegativeDenominator() {
        Fraction fraction = Fraction.getFraction(2, -2);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(-1, 1), reduced);
    }

    @Test
    void testReduceBothNegative() {
        Fraction fraction = Fraction.getFraction(-2, -2);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(1, 1), reduced);
    }
}
