@Test
void testGetMaximumExplodePercentWithNullDataset() {
    PiePlot plot = new PiePlot(null);
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.00001);
}

@Test
void testGetMaximumExplodePercentWithEmptyDataset() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    PiePlot plot = new PiePlot(dataset);
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.00001);
}

@Test
void testGetMaximumExplodePercentWithNoExplodedKeys() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Key 1", 10.0);
    dataset.setValue("Key 2", 20.0);
    PiePlot plot = new PiePlot(dataset);

    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.00001);
}

@Test
void testGetMaximumExplodePercentWithExplodedKeys() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Key 1", 10.0);
    dataset.setValue("Key 2", 20.0);
    dataset.setValue("Key 3", 30.0);
    PiePlot plot = new PiePlot(dataset);

    plot.setExplodePercent("Key 1", 0.15);
    plot.setExplodePercent("Key 2", 0.30);
    plot.setExplodePercent("Key 3", 0.05);

    assertEquals(0.30, plot.getMaximumExplodePercent(), 0.00001);
}

@Test
void testGetMaximumExplodePercentWithMixedExplodedAndUnexplodedKeys() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Key 1", 10.0);
    dataset.setValue("Key 2", 20.0);
    PiePlot plot = new PiePlot(dataset);

    plot.setExplodePercent("Key 2", 0.25);

    assertEquals(0.25, plot.getMaximumExplodePercent(), 0.00001);
}
