@Test
public void testGetPctWithObject() {
    Frequency freq = new Frequency();
    freq.addValue("A");
    freq.addValue("B");
    freq.addValue("A");

    assertEquals(2.0/3.0, freq.getPct((Object)"A"), 0.0001);
    assertEquals(1.0/3.0, freq.getPct((Object)"B"), 0.0001);
    assertEquals(0.0, freq.getPct((Object)"C"), 0.0001);
}

@Test
public void testGetPctWithNullObject() {
    Frequency freq = new Frequency();
    freq.addValue("A");
    freq.addValue(null);

    assertEquals(0.5, freq.getPct((Object)null), 0.0001);
}

@Test
public void testGetPctWithEmptyFrequency() {
    Frequency freq = new Frequency();
    assertEquals(0.0, freq.getPct((Object)"A"), 0.0001);
}
