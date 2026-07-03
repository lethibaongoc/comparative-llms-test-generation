package org.apache.commons.math3.distribution;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class HypergeometricDistributionTest {

    @Test
    void testGetNumericalMean_SimpleCase() {
        HypergeometricDistribution distribution = new HypergeometricDistribution(50, 20, 10);
        double mean = distribution.getNumericalMean();
        assertEquals(4.0, mean, 1e-9);
    }

    @Test
    void testGetNumericalMean_SampleSizeEqualsPopulationSize() {
        HypergeometricDistribution distribution = new HypergeometricDistribution(50, 20, 50);
        double mean = distribution.getNumericalMean();
        assertEquals(20.0, mean, 1e-9);
    }

    @Test
    void testGetNumericalMean_LargePopulation() {
        HypergeometricDistribution distribution = new HypergeometricDistribution(2_000_000_000, 1_500_000_000, 1_500_000_000);
        double mean = distribution.getNumericalMean();
        assertEquals(1_500_000_000.0, mean, 1e-9);
    }
}
