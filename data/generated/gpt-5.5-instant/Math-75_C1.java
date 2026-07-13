@Test
public void testGetPct_existingValue() {
    Frequency frequency = new Frequency();
    frequency.addValue("A");
    frequency.addValue("A");
    frequency.addValue("B");

    assertEquals(2.0 / 3.0, frequency.getPct((Object) "A"), 1e-10);
}

@Test
public void testGetPct_nonExistingValue() {
    Frequency frequency = new Frequency();
    frequency.addValue("A");
    frequency.addValue("B");

    assertEquals(0.0, frequency.getPct((Object) "C"), 1e-10);
}

@Test
public void testGetPct_emptyFrequency() {
    Frequency frequency = new Frequency();

    assertTrue(Double.isNaN(frequency.getPct((Object) "A")));
}
