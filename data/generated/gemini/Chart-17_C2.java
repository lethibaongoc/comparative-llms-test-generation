@Test
void testClone() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = new ArrayList<>();
    original.data.add("test-item");

    TimeSeries clone = (TimeSeries) original.clone();

    assertNotNull(clone);
    assertNotSame(original, clone);
    assertNotSame(original.data, clone.data);
    assertEquals(original.data, clone.data);
}

@Test
void testCloneWithEmptyData() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = new ArrayList<>();

    TimeSeries clone = (TimeSeries) original.clone();

    assertNotNull(clone);
    assertNotSame(original, clone);
    assertNotSame(original.data, clone.data);
    assertTrue(clone.data.isEmpty());
}
