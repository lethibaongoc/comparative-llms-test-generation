package org.apache.commons.lang.math;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FractionTest {

    @Test
    public void testReduceZero() {
        Fraction fraction = Fraction.ZERO;
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.ZERO, reduced);
    }

    @Test
    public void testReduceAlreadyReduced() {
        Fraction fraction = Fraction.getFraction(1, 1);
        Fraction reduced = fraction.reduce();
        assertEquals(fraction, reduced);
    }

    @Test
    public void testReduceNumeratorAndDenominator() {
        Fraction fraction = Fraction.getFraction(2, 2);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(1, 1), reduced);
    }

    @Test
    public void testReduceNumeratorOnly() {
        Fraction fraction = Fraction.getFraction(4, 1);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(4, 1), reduced);
    }

    @Test
    public void testReduceDenominatorOnly() {
        Fraction fraction = Fraction.getFraction(1, 4);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(1, 4), reduced);
    }

    @Test
    public void testReduceNegativeNumerator() {
        Fraction fraction = Fraction.getFraction(-2, 2);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(-1, 1), reduced);
    }

    @Test
    public void testReduceNegativeDenominator() {
        Fraction fraction = Fraction.getFraction(2, -2);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(-1, 1), reduced);
    }

    @Test
    public void testReduceBothNegative() {
        Fraction fraction = Fraction.getFraction(-2, -2);
        Fraction reduced = fraction.reduce();
        assertEquals(Fraction.getFraction(1, 1), reduced);
    }
}
