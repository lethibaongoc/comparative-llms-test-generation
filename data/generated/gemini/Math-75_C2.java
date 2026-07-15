@Test
void testGetPct() {
    Frequency f = new Frequency();
    f.addValue(1);
    f.addValue(2);
    f.addValue(2);
    f.addValue(3);

    // Total sum is 4. '2' appears twice. Percentage should be 2/4 = 0.5.
    assertEquals(0.5, f.getPct(2), 10e-15);
    assertEquals(0.25, f.getPct(1), 10e-15);
    assertEquals(0.0, f.getPct(4), 10e-15);
}

@Test
void testGetPctNull() {
    Frequency f = new Frequency();
    assertThrows(NullPointerException.class, () -> f.getPct(null));
}

@Test
void testGetPctNonComparable() {
    Frequency f = new Frequency();
    // Passing a non-comparable object should result in a ClassCastException
    Object nonComparable = new Object();
    assertThrows(ClassCastException.class, () -> f.getPct(nonComparable));
}

@Test
void testGetPctEmpty() {
    Frequency f = new Frequency();
    // If no values have been added, the percentage should return NaN
    assertTrue(Double.isNaN(f.getPct(1)));
}
