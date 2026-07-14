@Test
void shouldReturnZeroWhenDatasetIsNull() {
    PiePlot plot = new PiePlot();
    plot.dataset = null;
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.0001);
}

@Test
void shouldReturnZeroWhenExplodePercentagesIsEmpty() {
    PiePlot plot = new PiePlot();
    plot.dataset = mock(PieDataset.class);
    when(plot.dataset.getKeys()).thenReturn(Collections.emptyList());
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.0001);
}

@Test
void shouldReturnZeroWhenAllExplodeValuesAreNull() {
    PiePlot plot = new PiePlot();
    plot.dataset = mock(PieDataset.class);
    Comparable key1 = "A";
    Comparable key2 = "B";
    when(plot.dataset.getKeys()).thenReturn(Arrays.asList(key1, key2));
    plot.explodePercentages = new HashMap<>();
    plot.explodePercentages.put(key1, null);
    plot.explodePercentages.put(key2, null);
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.0001);
}

@Test
void shouldReturnMaximumExplodeValue() {
    PiePlot plot = new PiePlot();
    plot.dataset = mock(PieDataset.class);
    Comparable key1 = "A";
    Comparable key2 = "B";
    Comparable key3 = "C";
    when(plot.dataset.getKeys()).thenReturn(Arrays.asList(key1, key2, key3));
    plot.explodePercentages = new HashMap<>();
    plot.explodePercentages.put(key1, 0.1);
    plot.explodePercentages.put(key2, 0.5);
    plot.explodePercentages.put(key3, 0.3);
    assertEquals(0.5, plot.getMaximumExplodePercent(), 0.0001);
}

@Test
void shouldIgnoreNullExplodeValuesAndReturnMaximum() {
    PiePlot plot = new PiePlot();
    plot.dataset = mock(PieDataset.class);
    Comparable key1 = "A";
    Comparable key2 = "B";
    Comparable key3 = "C";
    when(plot.dataset.getKeys()).thenReturn(Arrays.asList(key1, key2, key3));
    plot.explodePercentages = new HashMap<>();
    plot.explodePercentages.put(key1, 0.2);
    plot.explodePercentages.put(key2, null);
    plot.explodePercentages.put(key3, 0.7);
    assertEquals(0.7, plot.getMaximumExplodePercent(), 0.0001);
}

@Test
void shouldReturnMaximumWithSingleExplodeValue() {
    PiePlot plot = new PiePlot();
    plot.dataset = mock(PieDataset.class);
    Comparable key1 = "A";
    when(plot.dataset.getKeys()).thenReturn(Collections.singletonList(key1));
    plot.explodePercentages = new HashMap<>();
    plot.explodePercentages.put(key1, 0.75);
    assertEquals(0.75, plot.getMaximumExplodePercent(), 0.0001);
}
