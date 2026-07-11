```java
@Test
void sample_returnsExactIntegerWhenNextHypergeometricIsExact() {
    // Mock RandomDataGenerator
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    // When nextHypergeometric is called with any int arguments, return 5.0
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(5.0);

    // Create HypergeometricDistribution instance with mocked dependency
    // Using arbitrary valid parameters for the distribution itself
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);

    int result = distribution.sample();
    assertEquals(5, result, "sample() should return the exact integer value.");
}

@Test
void sample_roundsHalfUpWhenNextHypergeometricHasPointFive() {
    // Mock RandomDataGenerator
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    // When nextHypergeometric is called with any int arguments, return 5.5
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(5.5);

    // Create HypergeometricDistribution instance with mocked dependency
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);

    int result = distribution.sample();
    assertEquals(6, result, "sample() should round .5 up (Math.round behavior).");
}

@Test
void sample_roundsDownWhenNextHypergeometricIsPointFourNine() {
    // Mock RandomDataGenerator
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    // When nextHypergeometric is called with any int arguments, return 5.49
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(5.49);

    // Create HypergeometricDistribution instance with mocked dependency
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);

    int result = distribution.sample();
    assertEquals(5, result, "sample() should round .49 down.");
}

@Test
void sample_roundsUpWhenNextHypergeometricIsPointNine() {
    // Mock RandomDataGenerator
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    // When nextHypergeometric is called with any int arguments, return 5.9
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(5.9);

    // Create HypergeometricDistribution instance with mocked dependency
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);

    int result = distribution.sample();
    assertEquals(6, result, "sample() should round .9 up.");
}

@Test
void sample_returnsZeroWhenNextHypergeometricIsZero() {
    // Mock RandomDataGenerator
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    // When nextHypergeometric is called with any int arguments, return 0.0
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(0.0);

    // Create HypergeometricDistribution instance with mocked dependency
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);

    int result = distribution.sample();
    assertEquals(0, result, "sample() should return 0 when nextHypergeometric is 0.0.");
}

@Test
void sample_handlesNegativeValueRoundingAwayFromZeroForPointFive() {
    // FastMath.round(double) uses Math.round(double), which rounds -X.5 to -X (e.g., -2.5 to -2).
    // Mock RandomDataGenerator
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    // When nextHypergeometric is called with any int arguments, return -2.5
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(-2.5);

    // Create HypergeometricDistribution instance with mocked dependency
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);

    int result = distribution.sample();
    assertEquals(-2, result, "sample() should correctly round negative .5 value (-2.5 to -2).");
}

@Test
void sample_handlesNegativeValueRoundingDown() {
    // Math.round(-2.6) -> -3
    // Mock RandomDataGenerator
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    // When nextHypergeometric is called with any int arguments, return -2.6
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(-2.6);

    // Create HypergeometricDistribution instance with mocked dependency
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);

    int result = distribution.sample();
    assertEquals(-3, result, "sample() should correctly round negative value down (-2.6 to -3).");
}

@Test
void sample_handlesNegativeValueRoundingUp() {
    // Math.round(-2.3) -> -2
    // Mock RandomDataGenerator
    RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
    // When nextHypergeometric is called with any int arguments, return -2.3
    Mockito.when(mockRandomData.nextHypergeometric(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(-2.3);

    // Create HypergeometricDistribution instance with mocked dependency
    HypergeometricDistribution distribution = new HypergeometricDistribution(100, 10, 20, mockRandomData);

    int result = distribution.sample();
    assertEquals(-2, result, "sample() should correctly round negative value up (-2.3 to -2).");
}
```