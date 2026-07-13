@Test
public void testGetMaximumExplodePercent_nullDataset() {
    PiePlot plot = new PiePlot();
    plot.setDataset(null);

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
    plot.setExplodePercent("A", 0.15);
    plot.setExplodePercent("B", 0.60);
    plot.setExplodePercent("C", 0.40);

    assertEquals(0.60, plot.getMaximumExplodePercent(), 0.0000001);
}

@Test
public void testGetMaximumExplodePercent_ignoresKeysWithoutExplodePercentage() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("A", 1);
    dataset.setValue("B", 2);
    dataset.setValue("C", 3);

    PiePlot plot = new PiePlot(dataset);
    plot.setExplodePercent("B", 0.45);

    assertEquals(0.45, plot.getMaximumExplodePercent(), 0.0000001);
}
