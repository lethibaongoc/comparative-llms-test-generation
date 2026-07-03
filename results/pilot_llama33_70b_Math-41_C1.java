package org.apache.commons.math.stat.descriptive.moment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VarianceTest {

    @Test
    public void testBasicCase() {
        Variance variance = new Variance();
        double[] values = {2.0, 4.0, 6.0};
        double[] weights = {1.0, 1.0, 1.0};
        double mean = 4.0;
        double result = variance.evaluate(values, weights, mean, 0, values.length);
        assertEquals(4.0, result, 1e-9);
    }

    @Test
    public void testSubRangeCase() {
        Variance variance = new Variance();
        double[] values = {2.0, 4.0, 6.0, 100.0, 200.0};
        double[] weights = {1.0, 1.0, 1.0, 5.0, 5.0};
        double mean = 4.0;
        double result = variance.evaluate(values, weights, mean, 0, 3);
        assertEquals(4.0, result, 1e-9);
    }

    @Test
    public void testLengthOneCase() {
        Variance variance = new Variance();
        double[] values = {2.0};
        double[] weights = {1.0};
        double mean = 2.0;
        double result = variance.evaluate(values, weights, mean, 0, 1);
        assertEquals(0.0, result, 1e-9);
    }
}
