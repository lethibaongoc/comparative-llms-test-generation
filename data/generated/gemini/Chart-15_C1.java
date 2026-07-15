@Test
void testGetMaximumExplodePercentWithNullDataset() {
    // Arrange: Create a PiePlot with a null dataset
    PiePlot plot = new PiePlot(null);

    // Act & Assert: Should return 0.0 directly
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.000001);
}

@Test
void testGetMaximumExplodePercentWithEmptyDataset() {
    // Arrange: Dataset is empty, so iterator has no keys
    DefaultPieDataset dataset = new DefaultPieDataset();
    PiePlot plot = new PiePlot(dataset);

    // Act & Assert: No keys, should return default 0.0
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.000001);
}

@Test
void testGetMaximumExplodePercentWithNoExplodedSlices() {
    // Arrange: Dataset has keys, but no explode percentages are set
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Slice A", 10.0);
    dataset.setValue("Slice B", 20.0);

    PiePlot plot = new PiePlot(dataset);

    // Act & Assert: Explode percentages map is empty/returns null for keys
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.000001);
}

@Test
void testGetMaximumExplodePercentWithSingleExplodedSlice() {
    // Arrange: Single key has an explode percentage set
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Slice A", 10.0);
    dataset.setValue("Slice B", 20.0);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("Slice A", 0.15);

    // Act & Assert: Should return the single exploded percentage
    assertEquals(0.15, plot.getMaximumExplodePercent(), 0.000001);
}

@Test
void testGetMaximumExplodePercentReturnsMaxVal() {
    // Arrange: Multiple exploded slices, one higher than the other
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Slice A", 10.0);
    dataset.setValue("Slice B", 20.0);
    dataset.setValue("Slice C", 30.0);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("Slice A", 0.10);
    plot.setExplodePercent("Slice B", 0.35);
    plot.setExplodePercent("Slice C", 0.20);

    // Act & Assert: Should find the maximum value (0.35)
    assertEquals(0.35, plot.getMaximumExplodePercent(), 0.000001);
}

@Test
void testGetMaximumExplodePercentWithNegativeValues() {
    // Arrange: Explode values are negative, initial result is 0.0
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Slice A", 10.0);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("Slice A", -0.10);

    // Act & Assert: Math.max(0.0, -0.10) is 0.0
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.000001);
}
