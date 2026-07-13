@Test
void sample_returnsExactIntegerWhenNextHypergeometricIsExact() {
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(5.0);
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);
    int result = distribution.sample();
    assertEquals(5, result, "sample() should return the exact integer value.");
}

@Test
void sample_roundsHalfUpWhenNextHypergeometricHasPointFive() {
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(5.5);
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);
    int result = distribution.sample();
    assertEquals(6, result, "sample() should round .5 up (Math.round behavior).");
}

@Test
void sample_roundsDownWhenNextHypergeometricIsPointFourNine() {
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(5.49);
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);
    int result = distribution.sample();
    assertEquals(5, result, "sample() should round .49 down.");
}

@Test
void sample_roundsUpWhenNextHypergeometricIsPointNine() {
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(5.9);
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);
    int result = distribution.sample();
    assertEquals(6, result, "sample() should round .9 up.");
}

@Test
void sample_returnsZeroWhenNextHypergeometricIsZero() {
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(0.0);
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);
    int result = distribution.sample();
    assertEquals(0, result, "sample() should return 0 when nextHypergeometric is 0.0.");
}

@Test
void sample_handlesNegativeValueRoundingAwayFromZeroForPointFive() {
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(-2.5);
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);
    int result = distribution.sample();
    assertEquals(-2, result, "sample() should correctly round negative .5 value (-2.5 to -2).");
}

@Test
void sample_handlesNegativeValueRoundingDown() {
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(-2.6);
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);
    int result = distribution.sample();
    assertEquals(-3, result, "sample() should correctly round negative value down (-2.6 to -3).");
}

@Test
void sample_handlesNegativeValueRoundingUp() {
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(-2.3);
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);
    int result = distribution.sample();
    assertEquals(-2, result, "sample() should correctly round negative value up (-2.3 to -2).");
}
