package org.apache.commons.math3.fraction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FractionTest {

    @Test
    void testPercentageValue_SimpleFraction() {
        Fraction fraction = new Fraction(1, 4);
        assertEquals(25.0, fraction.percentageValue(), 1e-10);
    }

    @Test
    void testPercentageValue_FractionEqualToOne() {
        Fraction fraction = new Fraction(1, 1);
        assertEquals(100.0, fraction.percentageValue(), 1e-10);
    }

    @Test
    void testPercentageValue_FractionGreaterThanOne() {
        Fraction fraction = new Fraction(3, 2);
        assertEquals(150.0, fraction.percentageValue(), 1e-10);
    }

    @Test
    void testPercentageValue_LargeNumerator() {
        Fraction fraction = new Fraction(21474837, 1);
        assertEquals(2147483700.0, fraction.percentageValue(), 1e-10);
    }
}
