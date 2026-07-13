@Test
public void testUpdateBounds_singleItem() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
        new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(
        5.0, 5.0, 3.0, 7.0,
        2.0, 8.0, 1.0, 10.0, null);

    dataset.add(item, "R1", "C1");

    assertEquals(1.0, dataset.getRangeLowerBound(false), 0.0);
    assertEquals(10.0, dataset.getRangeUpperBound(false), 0.0);
}

@Test
public void testUpdateBounds_multipleItemsFindsGlobalBounds() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
        new DefaultBoxAndWhiskerCategoryDataset();
    dataset.add(new BoxAndWhiskerItem(
        5.0, 5.0, 3.0, 7.0,
        2.0, 8.0, -4.0, 10.0, null), "R1", "C1");
    dataset.add(new BoxAndWhiskerItem(
        6.0, 6.0, 4.0, 8.0,
        3.0, 9.0, -2.0, 15.0, null), "R2", "C2");

    assertEquals(-4.0, dataset.getRangeLowerBound(false), 0.0);
    assertEquals(15.0, dataset.getRangeUpperBound(false), 0.0);
}

@Test
public void testUpdateBounds_ignoresNaNOutliers() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
        new DefaultBoxAndWhiskerCategoryDataset();
    dataset.add(new BoxAndWhiskerItem(
        5.0, 5.0, 3.0, 7.0,
        2.0, 8.0, Double.NaN, Double.NaN, null), "R1", "C1");
    dataset.add(new BoxAndWhiskerItem(
        6.0, 6.0, 4.0, 8.0,
        3.0, 9.0, 1.0, 12.0, null), "R2", "C2");

    assertEquals(1.0, dataset.getRangeLowerBound(false), 0.0);
    assertEquals(12.0, dataset.getRangeUpperBound(false), 0.0);
}

@Test
public void testUpdateBounds_ignoresNullOutliers() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
        new DefaultBoxAndWhiskerCategoryDataset();
    dataset.add(new BoxAndWhiskerItem(
        5.0, 5.0, 3.0, 7.0,
        2.0, 8.0, null, null, null), "R1", "C1");
    dataset.add(new BoxAndWhiskerItem(
        6.0, 6.0, 4.0, 8.0,
        3.0, 9.0, -3.0, 11.0, null), "R2", "C2");

    assertEquals(-3.0, dataset.getRangeLowerBound(false), 0.0);
    assertEquals(11.0, dataset.getRangeUpperBound(false), 0.0);
}

@Test
public void testUpdateBounds_recalculatesAfterItemReplacement() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
        new DefaultBoxAndWhiskerCategoryDataset();
    dataset.add(new BoxAndWhiskerItem(
        5.0, 5.0, 3.0, 7.0,
        2.0, 8.0, -10.0, 20.0, null), "R1", "C1");

    dataset.add(new BoxAndWhiskerItem(
        5.0, 5.0, 3.0, 7.0,
        2.0, 8.0, 1.0, 9.0, null), "R1", "C1");

    assertEquals(1.0, dataset.getRangeLowerBound(false), 0.0);
    assertEquals(9.0, dataset.getRangeUpperBound(false), 0.0);
}

@Test
public void testUpdateBounds_emptyDatasetReturnsNaNBounds() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
        new DefaultBoxAndWhiskerCategoryDataset();

    assertTrue(Double.isNaN(dataset.getRangeLowerBound(false)));
    assertTrue(Double.isNaN(dataset.getRangeUpperBound(false)));
}
