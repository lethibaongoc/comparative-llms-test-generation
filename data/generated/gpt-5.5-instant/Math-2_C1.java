@Test
public void testGetNumericalMean_typicalValues() {
    HypergeometricDistribution distribution =
        new HypergeometricDistribution(50, 20, 10);

    assertEquals(4.0, distribution.getNumericalMean(), 1e-12);
}

@Test
public void testGetNumericalMean_zeroSuccesses() {
    HypergeometricDistribution distribution =
        new HypergeometricDistribution(50, 0, 10);

    assertEquals(0.0, distribution.getNumericalMean(), 1e-12);
}

@Test
public void testGetNumericalMean_sampleEqualsPopulation() {
    HypergeometricDistribution distribution =
        new HypergeometricDistribution(30, 12, 30);

    assertEquals(12.0, distribution.getNumericalMean(), 1e-12);
}

@Test
public void testGetNumericalMean_allSuccessesInPopulation() {
    HypergeometricDistribution distribution =
        new HypergeometricDistribution(40, 40, 15);

    assertEquals(15.0, distribution.getNumericalMean(), 1e-12);
}

@Test
public void testGetNumericalMean_fractionalMean() {
    HypergeometricDistribution distribution =
        new HypergeometricDistribution(9, 4, 5);

    assertEquals(20.0 / 9.0, distribution.getNumericalMean(), 1e-12);
}
