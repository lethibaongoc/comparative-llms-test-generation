@Test
void testUpdateBoundsUsesMinimumAndMaximumOutliers() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
            new DefaultBoxAndWhiskerCategoryDataset();

    dataset.add(new BoxAndWhiskerItem(
            5.0, 5.0, 3.0, 7.0, 2.0, 8.0,
            -4.0, 12.0, null), "R1", "C1");
    dataset.add(new BoxAndWhiskerItem(
            10.0, 10.0, 8.0, 12.0, 7.0, 13.0,
            -8.0, 20.0, null), "R2", "C2");

    assertEquals(-8.0, dataset.getRangeLowerBound(true), 0.0);
    assertEquals(20.0, dataset.getRangeUpperBound(true), 0.0);
}

@Test
void testUpdateBoundsIgnoresNaNOutlierValues() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
            new DefaultBoxAndWhiskerCategoryDataset();

    dataset.add(new BoxAndWhiskerItem(
            5.0, 5.0, 3.0, 7.0, 2.0, 8.0,
            Double.NaN, Double.NaN, null), "R1", "C1");
    dataset.add(new BoxAndWhiskerItem(
            6.0, 6.0, 4.0, 8.0, 3.0, 9.0,
            -2.0, 11.0, null), "R2", "C2");

    assertEquals(-2.0, dataset.getRangeLowerBound(true), 0.0);
    assertEquals(11.0, dataset.getRangeUpperBound(true), 0.0);
}

@Test
void testUpdateBoundsIgnoresNullOutlierValues() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
            new DefaultBoxAndWhiskerCategoryDataset();

    dataset.add(new BoxAndWhiskerItem(
            5.0, 5.0, 3.0, 7.0, 2.0, 8.0,
            null, null, null), "R1", "C1");

    assertTrue(Double.isNaN(dataset.getRangeLowerBound(true)));
    assertTrue(Double.isNaN(dataset.getRangeUpperBound(true)));
}

@Test
void testUpdateBoundsRecalculatesAfterRemovingExtremeItem() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
            new DefaultBoxAndWhiskerCategoryDataset();

    dataset.add(new BoxAndWhiskerItem(
            5.0, 5.0, 3.0, 7.0, 2.0, 8.0,
            -10.0, 25.0, null), "R1", "C1");
    dataset.add(new BoxAndWhiskerItem(
            6.0, 6.0, 4.0, 8.0, 3.0, 9.0,
            -3.0, 14.0, null), "R2", "C2");

    dataset.remove("R1", "C1");

    assertEquals(-3.0, dataset.getRangeLowerBound(true), 0.0);
    assertEquals(14.0, dataset.getRangeUpperBound(true), 0.0);
}

@Test
void testUpdateBoundsResetsValuesWhenDatasetBecomesEmpty() {
    DefaultBoxAndWhiskerCategoryDataset dataset =
            new DefaultBoxAndWhiskerCategoryDataset();

    dataset.add(new BoxAndWhiskerItem(
            5.0, 5.0, 3.0, 7.0, 2.0, 8.0,
            -4.0, 12.0, null), "R1", "C1");

    dataset.remove("R1", "C1");

    assertTrue(Double.isNaN(dataset.getRangeLowerBound(true)));
    assertTrue(Double.isNaN(dataset.getRangeUpperBound(true)));
}
