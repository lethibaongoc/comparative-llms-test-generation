@Test
public void testClone_returnsDistinctObjectWithSameContent() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries("S");
    original.add(new Day(1, 1, 2020), 1.0);
    original.add(new Day(2, 1, 2020), 2.0);

    TimeSeries clone = (TimeSeries) original.clone();

    assertNotSame(original, clone);
    assertEquals(original, clone);
}

@Test
public void testClone_deepCopiesDataList() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries("S");
    original.add(new Day(1, 1, 2020), 1.0);

    TimeSeries clone = (TimeSeries) original.clone();
    clone.add(new Day(2, 1, 2020), 2.0);

    assertEquals(1, original.getItemCount());
    assertEquals(2, clone.getItemCount());
}

@Test
public void testClone_modifyingOriginalDoesNotAffectClone() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries("S");
    original.add(new Day(1, 1, 2020), 1.0);

    TimeSeries clone = (TimeSeries) original.clone();
    original.add(new Day(2, 1, 2020), 2.0);

    assertEquals(2, original.getItemCount());
    assertEquals(1, clone.getItemCount());
}

@Test
public void testClone_emptySeries() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries("Empty");

    TimeSeries clone = (TimeSeries) original.clone();

    assertNotSame(original, clone);
    assertEquals(0, clone.getItemCount());
    assertEquals(original, clone);
}
