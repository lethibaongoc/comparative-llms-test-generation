@Test
void testCloneCreatesDeepCopy() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries("Series");
    original.add(new Day(1, 1, 2020), 1.0);
    original.add(new Day(2, 1, 2020), 2.0);

    TimeSeries clone = (TimeSeries) original.clone();

    assertNotSame(original, clone);
    assertEquals(original.getItemCount(), clone.getItemCount());
    assertEquals(original.getValue(0), clone.getValue(0));
    assertEquals(original.getValue(1), clone.getValue(1));

    clone.add(new Day(3, 1, 2020), 3.0);

    assertEquals(2, original.getItemCount());
    assertEquals(3, clone.getItemCount());
}

@Test
void testCloneOfEmptyTimeSeries() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries("Empty Series");

    TimeSeries clone = (TimeSeries) original.clone();

    assertNotSame(original, clone);
    assertEquals(0, clone.getItemCount());
}
