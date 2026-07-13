@Test
public void testUpdateBounds_singleItem() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    BoxAndWhiskerItem item = new BoxAndWhiskerItem(
        5.0, 5.0, 4.0, 6.0,
        2.0, 8.0,
        2.0, 8.0,
        Collections.emptyList());

    dataset.add(item, "R1", "C1");

    assertEquals(2.0, dataset.getRangeLowerBound(true), 0.0000001);
    assertEquals(8.0, dataset.getRangeUpperBound(true), 0.0000001);
}

@Test
public void testUpdateBounds_multipleItemsFindsGlobalMinAndMax() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(
        5.0, 5.0, 4.0, 6.0,
        3.0, 9.0,
        3.0, 9.0,
        Collections.emptyList());

    BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(
        7.0, 7.0, 6.0, 8.0,
        -2.0, 15.0,
        -2.0, 15.0,
        Collections.emptyList());

    dataset.add(item1, "R1", "C1");
    dataset.add(item2, "R2", "C2");

    assertEquals(-2.0, dataset.getRangeLowerBound(true), 0.0000001);
    assertEquals(15.0, dataset.getRangeUpperBound(true), 0.0000001);
}

@Test
public void testUpdateBounds_ignoresNaNOutliers() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    BoxAndWhiskerItem nanItem = new BoxAndWhiskerItem(
        5.0, 5.0, 4.0, 6.0,
        Double.NaN, Double.NaN,
        Double.NaN, Double.NaN,
        Collections.emptyList());

    BoxAndWhiskerItem validItem = new BoxAndWhiskerItem(
        6.0, 6.0, 5.0, 7.0,
        1.0, 10.0,
        1.0, 10.0,
        Collections.emptyList());

    dataset.add(nanItem, "R1", "C1");
    dataset.add(validItem, "R1", "C2");

    assertEquals(1.0, dataset.getRangeLowerBound(true), 0.0000001);
    assertEquals(10.0, dataset.getRangeUpperBound(true), 0.0000001);
}

@Test
public void testUpdateBounds_allNaNOutliersProducesNaNBounds() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    BoxAndWhiskerItem item = new BoxAndWhiskerItem(
        5.0, 5.0, 4.0, 6.0,
        Double.NaN, Double.NaN,
        Double.NaN, Double.NaN,
        Collections.emptyList());

    dataset.add(item, "R1", "C1");

    assertTrue(Double.isNaN(dataset.getRangeLowerBound(true)));
    assertTrue(Double.isNaN(dataset.getRangeUpperBound(true)));
}
