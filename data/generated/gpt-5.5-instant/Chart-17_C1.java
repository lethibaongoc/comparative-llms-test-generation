@Test
public void testClone_createsDeepCopy() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries("S");
    original.add(new Day(1, 1, 2020), 1.0);
    original.add(new Day(2, 1, 2020), 2.0);

    TimeSeries clone = (TimeSeries) original.clone();

    assertNotSame(original, clone);
    assertEquals(original, clone);

    clone.add(new Day(3, 1, 2020), 3.0);

    assertEquals(2, original.getItemCount());
    assertEquals(3, clone.getItemCount());
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
    assertEquals(original, clone);
    assertEquals(0, clone.getItemCount());
}

@Test
public void testClone_preservesDataValues() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries("S");
    Day day = new Day(15, 6, 2021);
    original.add(day, 42.5);

    TimeSeries clone = (TimeSeries) original.clone();

    assertEquals(original.getValue(day), clone.getValue(day));
    assertEquals(original.getTimePeriod(0), clone.getTimePeriod(0));
}
