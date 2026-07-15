@Test
void testGetNumericalMeanStandardCase() {
    HypergeometricDistribution dist = Mockito.spy(new HypergeometricDistribution(100, 30, 20));
    Mockito.doReturn(20).when(dist).getSampleSize();
    Mockito.doReturn(30).when(dist).getNumberOfSuccesses();
    Mockito.doReturn(100).when(dist).getPopulationSize();

    // Mean = 20 * (30 / 100.0) = 6.0
    assertEquals(6.0, dist.getNumericalMean(), 1e-9);
}

@Test
void testGetNumericalMeanWithZeroSuccesses() {
    HypergeometricDistribution dist = Mockito.spy(new HypergeometricDistribution(100, 0, 20));
    Mockito.doReturn(20).when(dist).getSampleSize();
    Mockito.doReturn(0).when(dist).getNumberOfSuccesses();
    Mockito.doReturn(100).when(dist).getPopulationSize();

    // Mean = 20 * (0 / 100.0) = 0.0
    assertEquals(0.0, dist.getNumericalMean(), 1e-9);
}

@Test
void testGetNumericalMeanWithZeroSampleSize() {
    HypergeometricDistribution dist = Mockito.spy(new HypergeometricDistribution(100, 30, 0));
    Mockito.doReturn(0).when(dist).getSampleSize();
    Mockito.doReturn(30).when(dist).getNumberOfSuccesses();
    Mockito.doReturn(100).when(dist).getPopulationSize();

    // Mean = 0 * (30 / 100.0) = 0.0
    assertEquals(0.0, dist.getNumericalMean(), 1e-9);
}

@Test
void testGetNumericalMeanAllSuccesses() {
    HypergeometricDistribution dist = Mockito.spy(new HypergeometricDistribution(50, 50, 10));
    Mockito.doReturn(10).when(dist).getSampleSize();
    Mockito.doReturn(50).when(dist).getNumberOfSuccesses();
    Mockito.doReturn(50).when(dist).getPopulationSize();

    // Mean = 10 * (50 / 50.0) = 10.0
    assertEquals(10.0, dist.getNumericalMean(), 1e-9);
}

@Test
void testGetNumericalMeanLargeValues() {
    HypergeometricDistribution dist = Mockito.spy(new HypergeometricDistribution(1000000, 300000, 50000));
    Mockito.doReturn(50000).when(dist).getSampleSize();
    Mockito.doReturn(300000).when(dist).getNumberOfSuccesses();
    Mockito.doReturn(1000000).when(dist).getPopulationSize();

    // Mean = 50000 * (300000 / 1000000.0) = 15000.0
    assertEquals(15000.0, dist.getNumericalMean(), 1e-9);
}
