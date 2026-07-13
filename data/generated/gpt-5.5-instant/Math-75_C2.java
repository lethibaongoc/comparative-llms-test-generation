@Test
public void testGetPct_existingValue() {
    Frequency obj = new Frequency();
    obj.addValue("apple");
    obj.addValue("apple");
    obj.addValue("banana");

    assertEquals(2.0 / 3.0, obj.getPct((Object) "apple"), 1e-10);
}

@Test
public void testGetPct_nonExistingValue() {
    Frequency obj = new Frequency();
    obj.addValue("apple");
    obj.addValue("banana");

    assertEquals(0.0, obj.getPct((Object) "orange"), 1e-10);
}

@Test
public void testGetPct_nullValue() {
    Frequency obj = new Frequency();
    obj.addValue("apple");

    assertEquals(0.0, obj.getPct((Object) null), 1e-10);
}

@Test
public void testGetPct_emptyFrequency() {
    Frequency obj = new Frequency();

    assertTrue(Double.isNaN(obj.getPct((Object) "apple")));
}
