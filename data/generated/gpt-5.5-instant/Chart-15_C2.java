@Test
public void testGetMaximumExplodePercent_nullDataset() {
    PiePlot plot = new PiePlot();
    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.0000001);
}

@Test
public void testGetMaximumExplodePercent_noExplodePercentages() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1);
    dataset.setValue("B", 2);

    PiePlot plot = new PiePlot(dataset);

    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.0000001);
}

@Test
public void testGetMaximumExplodePercent_singleExplodePercentage() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("A", 0.35);

    assertEquals(0.35, plot.getMaximumExplodePercent(), 0.0000001);
}

@Test
public void testGetMaximumExplodePercent_multipleExplodePercentages() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1);
    dataset.setValue("B", 2);
    dataset.setValue("C", 3);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("A", 0.10);
    plot.setExplodePercent("B", 0.45);
    plot.setExplodePercent("C", 0.30);

    assertEquals(0.45, plot.getMaximumExplodePercent(), 0.0000001);
}

@Test
public void testGetMaximumExplodePercent_ignoresKeysWithoutExplodePercentage() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1);
    dataset.setValue("B", 2);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("B", 0.25);

    assertEquals(0.25, plot.getMaximumExplodePercent(), 0.0000001);
}

@Test
public void testGetMaximumExplodePercent_allZeroExplodePercentages() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1);
    dataset.setValue("B", 2);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("A", 0.0);
    plot.setExplodePercent("B", 0.0);

    assertEquals(0.0, plot.getMaximumExplodePercent(), 0.0000001);
}
