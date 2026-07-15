@Test
void testGetMaximumExplodePercentWithNullDataset() {
    PiePlot plot = new PiePlot();

    assertEquals(0.0, plot.getMaximumExplodePercent(), 1e-10);
}

@Test
void testGetMaximumExplodePercentWithExplodePercentages() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1);
    dataset.setValue("B", 2);
    dataset.setValue("C", 3);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("A", 0.10);
    plot.setExplodePercent("B", 0.25);
    plot.setExplodePercent("C", 0.15);

    assertEquals(0.25, plot.getMaximumExplodePercent(), 1e-10);
}

@Test
void testGetMaximumExplodePercentWhenNoExplodePercentagesAreSet() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1);
    dataset.setValue("B", 2);

    PiePlot plot = new PiePlot(dataset);

    assertEquals(0.0, plot.getMaximumExplodePercent(), 1e-10);
}
