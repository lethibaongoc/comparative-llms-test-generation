@Test
void testGetNumericalMeanWithTypicalValues() {
    HypergeometricDistribution distribution =
            new HypergeometricDistribution(100, 40, 10);

    assertEquals(4.0, distribution.getNumericalMean(), 1e-10);
}

@Test
void testGetNumericalMeanWhenNoSuccesses() {
    HypergeometricDistribution distribution =
            new HypergeometricDistribution(50, 0, 20);

    assertEquals(0.0, distribution.getNumericalMean(), 1e-10);
}

@Test
void testGetNumericalMeanWhenAllPopulationAreSuccesses() {
    HypergeometricDistribution distribution =
            new HypergeometricDistribution(20, 20, 5);

    assertEquals(5.0, distribution.getNumericalMean(), 1e-10);
}
