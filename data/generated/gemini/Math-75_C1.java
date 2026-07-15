@Test
void testGetPctWithValidComparableReturnsCorrectPercentage() {
    Frequency frequency = new Frequency();
    frequency.addValue("A");
    frequency.addValue("B");
    frequency.addValue("A");
    frequency.addValue("C");

    // "A" represents 2 out of 4 entries (50%)
    assertEquals(0.5, frequency.getPct((Object) "A"), 1e-9);
}

@Test
void testGetPctWithMissingValueReturnsZero() {
    Frequency frequency = new Frequency();
    frequency.addValue("A");

    // "B" is not in the frequency table, should return 0%
    assertEquals(0.0, frequency.getPct((Object) "B"), 1e-9);
}

@Test
void testGetPctWithNonComparableThrowsClassCastException() {
    Frequency frequency = new Frequency();
    frequency.addValue("A");

    // Object does not implement Comparable; the explicit cast (Comparable<?>) v should fail
    Object nonComparable = new Object();
    assertThrows(ClassCastException.class, () -> frequency.getPct(nonComparable));
}

@Test
void testGetPctWithEmptyFrequencyReturnsNaN() {
    Frequency frequency = new Frequency();

    // When no values have been added, the percentage denominator is 0, returning Double.NaN
    assertTrue(Double.isNaN(frequency.getPct((Object) "A")));
}
