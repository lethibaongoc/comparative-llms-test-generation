@Test
public void testGetNumericalMean_typicalValues() {
    HypergeometricDistribution distribution = new HypergeometricDistribution(20, 8, 5);
    assertEquals(2.0, distribution.getNumericalMean(), 1e-10);
}

@Test
public void testGetNumericalMean_zeroSuccesses() {
    HypergeometricDistribution distribution = new HypergeometricDistribution(20, 0, 5);
    assertEquals(0.0, distribution.getNumericalMean(), 1e-10);
}

@Test
public void testGetNumericalMean_allSuccessesInPopulation() {
    HypergeometricDistribution distribution = new HypergeometricDistribution(10, 10, 4);
    assertEquals(4.0, distribution.getNumericalMean(), 1e-10);
}

@Test
public void testGetNumericalMean_fractionalResult() {
    HypergeometricDistribution distribution = new HypergeometricDistribution(9, 4, 5);
    assertEquals(20.0 / 9.0, distribution.getNumericalMean(), 1e-10);
}
