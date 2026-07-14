@Test
void testGetMaximumExplodePercentWhenDatasetIsNull() {
    PiePlot plot = new PiePlot();
    assertNull(plot.getDataset());
    assertEquals(0.0, plot.getMaximumExplodePercent());
}

@Test
void testGetMaximumExplodePercentWhenDatasetHasNoKeys() {
    PiePlot plot = new PiePlot();
    DefaultPieDataset dataset = new DefaultPieDataset();
    plot.setDataset(dataset);
    assertEquals(0.0, plot.getMaximumExplodePercent());
}

@Test
void testGetMaximumExplodePercentWhenAllExplodePercentagesAreNull() {
    PiePlot plot = new PiePlot();
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1.0);
    dataset.setValue("B", 2.0);
    plot.setDataset(dataset);
    assertEquals(0.0, plot.getMaximumExplodePercent());
}

@Test
void testGetMaximumExplodePercentWithSingleExplodedSection() {
    PiePlot plot = new PiePlot();
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1.0);
    dataset.setValue("B", 2.0);
    plot.setDataset(dataset);
    plot.setExplodePercent("A", 0.5);
    assertEquals(0.5, plot.getMaximumExplodePercent());
}

@Test
void testGetMaximumExplodePercentWithMultipleExplodedSections() {
    PiePlot plot = new PiePlot();
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1.0);
    dataset.setValue("B", 2.0);
    dataset.setValue("C", 3.0);
    plot.setDataset(dataset);
    plot.setExplodePercent("A", 0.3);
    plot.setExplodePercent("B", 0.7);
    plot.setExplodePercent("C", 0.5);
    assertEquals(0.7, plot.getMaximumExplodePercent());
}

@Test
void testGetMaximumExplodePercentWithExplodePercentGreaterThanOne() {
    PiePlot plot = new PiePlot();
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1.0);
    dataset.setValue("B", 2.0);
    plot.setDataset(dataset);
    plot.setExplodePercent("A", 1.5);
    plot.setExplodePercent("B", 1.2);
    assertEquals(1.5, plot.getMaximumExplodePercent());
}

@Test
void testGetMaximumExplodePercentWhenExplodeValueIsZero() {
    PiePlot plot = new PiePlot();
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1.0);
    dataset.setValue("B", 2.0);
    plot.setDataset(dataset);
    plot.setExplodePercent("A", 0.0);
    plot.setExplodePercent("B", 0.0);
    assertEquals(0.0, plot.getMaximumExplodePercent());
}

@Test
void testGetMaximumExplodePercentWithMixedExplodeAndNullValues() {
    PiePlot plot = new PiePlot();
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1.0);
    dataset.setValue("B", 2.0);
    dataset.setValue("C", 3.0);
    plot.setDataset(dataset);
    plot.setExplodePercent("A", 0.2);
    plot.setExplodePercent("C", 0.9);
    assertEquals(0.9, plot.getMaximumExplodePercent());
}
