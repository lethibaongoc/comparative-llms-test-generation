@Test
void testGetPctForExistingValue() {
    Frequency frequency = new Frequency();
    frequency.addValue("apple");
    frequency.addValue("apple");
    frequency.addValue("banana");

    assertEquals(2.0 / 3.0, frequency.getPct((Object) "apple"), 1e-10);
    assertEquals(1.0 / 3.0, frequency.getPct((Object) "banana"), 1e-10);
}

@Test
void testGetPctForNonExistingValue() {
    Frequency frequency = new Frequency();
    frequency.addValue("apple");
    frequency.addValue("banana");

    assertEquals(0.0, frequency.getPct((Object) "orange"), 1e-10);
}

@Test
void testGetPctForNullValue() {
    Frequency frequency = new Frequency();

    assertEquals(0.0, frequency.getPct((Object) null), 1e-10);
}
