@Test
void testGetNumericalMean() {
    // Assuming a mock or constructor sets: population=100, successes=20, sampleSize=10
    // Mean = 10 * (20 / 100.0) = 2.0
    HypergeometricDistribution dist = new HypergeometricDistribution(100, 20, 10);
    assertEquals(2.0, dist.getNumericalMean(), 1e-9);
}

@Test
void testGetNumericalMeanZeroSuccesses() {
    // Mean = 10 * (0 / 100.0) = 0.0
    HypergeometricDistribution dist = new HypergeometricDistribution(100, 0, 10);
    assertEquals(0.0, dist.getNumericalMean(), 1e-9);
}

@Test
void testGetNumericalMeanAllSuccesses() {
    // Mean = 10 * (100 / 100.0) = 10.0
    HypergeometricDistribution dist = new HypergeometricDistribution(100, 100, 10);
    assertEquals(10.0, dist.getNumericalMean(), 1e-9);
}
