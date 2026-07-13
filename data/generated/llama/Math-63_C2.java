package org.apache.commons.math.util;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    @Test
    void testEquals() {
        // Test exact equality
        assertTrue(MathUtils.equals(1.0, 1.0));

        // Test inequality
        assertFalse(MathUtils.equals(1.0, 2.0));

        // Test close values
        assertTrue(MathUtils.equals(1.000000000000001, 1.0));

        // Test NaN
        assertFalse(MathUtils.equals(Double.NaN, 1.0));
        assertFalse(MathUtils.equals(1.0, Double.NaN));
        assertFalse(MathUtils.equals(Double.NaN, Double.NaN));

        // Test infinity
        assertTrue(MathUtils.equals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertTrue(MathUtils.equals(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertFalse(MathUtils.equals(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
    }

    @Test
    void testEqualsEdgeCases() {
        // Test very close values
        assertTrue(MathUtils.equals(1.0000000000000001, 1.0));

        // Test very far values
        assertFalse(MathUtils.equals(1.0, 1e100));
    }

    @Test
    void testEqualsZero() {
        assertTrue(MathUtils.equals(0.0, 0.0));
        assertTrue(MathUtils.equals(-0.0, 0.0));
        assertTrue(MathUtils.equals(0.0, -0.0));
    }
}
