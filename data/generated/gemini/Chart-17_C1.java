@Test
void testCloneIsDeepCopy() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = new ArrayList<>(Arrays.asList("A", "B", "C"));

    TimeSeries cloned = (TimeSeries) original.clone();

    assertNotSame(original, cloned, "Clone should be a different instance");
    assertEquals(original.data, cloned.data, "Cloned data should be equal to original data");
    assertNotSame(original.data, cloned.data, "Clone should perform a deep copy of the data list");

    cloned.data.add("D");
    assertNotEquals(original.data.size(), cloned.data.size(), "Modifying clone data should not affect original data");
}

@Test
void testCloneEmptyList() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = new ArrayList<>();

    TimeSeries cloned = (TimeSeries) original.clone();

    assertNotNull(cloned.data, "Cloned data list should not be null");
    assertTrue(cloned.data.isEmpty(), "Cloned data list should be empty");
}
