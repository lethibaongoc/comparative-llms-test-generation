@Test
void testGetPctWithNull() {
    Frequency freq = new Frequency();
    assertEquals(0.0, freq.getPct(null), 0.0001);
}

@Test
void testGetPctWithSingleValue() {
    Frequency freq = new Frequency();
    freq.addValue("A");
    assertEquals(1.0, freq.getPct("A"), 0.0001);
}

@Test
void testGetPctWithMultipleValues() {
    Frequency freq = new Frequency();
    freq.addValue("A");
    freq.addValue("B");
    freq.addValue("B");
    assertEquals(0.3333, freq.getPct("A"), 0.0001);
    assertEquals(0.6667, freq.getPct("B"), 0.0001);
}

@Test
void testGetPctWithNonExistentValue() {
    Frequency freq = new Frequency();
    freq.addValue("A");
    freq.addValue("B");
    assertEquals(0.0, freq.getPct("C"), 0.0001);
}

@Test
void testGetPctWithIntegerValues() {
    Frequency freq = new Frequency();
    freq.addValue(1);
    freq.addValue(2);
    freq.addValue(2);
    assertEquals(0.3333, freq.getPct(1), 0.0001);
    assertEquals(0.6667, freq.getPct(2), 0.0001);
}

@Test
void testGetPctWithMixedTypes() {
    Frequency freq = new Frequency();
    freq.addValue("A");
    freq.addValue(1);
    assertEquals(0.5, freq.getPct("A"), 0.0001);
    assertEquals(0.5, freq.getPct(1), 0.0001);
    assertEquals(0.0, freq.getPct(2.0), 0.0001);
}
