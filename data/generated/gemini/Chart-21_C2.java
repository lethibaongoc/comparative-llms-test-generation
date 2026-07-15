@Test
void testUpdateBoundsWithEmptyDataset() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    // Initially empty, should call updateBounds internally or we verify state via getters
    assertEquals(Double.NaN, dataset.getRangeBounds(true).getLowerBound(), 1e-9);
    assertEquals(Double.NaN, dataset.getRangeBounds(true).getUpperBound(), 1e-9);
}

@Test
void testUpdateBoundsWithSingleItem() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 0.5, 5.5, 0.2, 5.8, new java.util.ArrayList<>());
    dataset.add(item, "Row1", "Col1");

    // minOutlier = 0.2, maxOutlier = 5.8
    assertEquals(0.2, dataset.getRangeBounds(true).getLowerBound(), 1e-9);
    assertEquals(5.8, dataset.getRangeBounds(true).getUpperBound(), 1e-9);
}

@Test
void testUpdateBoundsWithMultipleItems() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 0.5, 5.5, 0.2, 5.8, new java.util.ArrayList<>());
    BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 0.5, 5.5, 0.1, 6.2, new java.util.ArrayList<>());

    dataset.add(item1, "Row1", "Col1");
    dataset.add(item2, "Row2", "Col2");

    assertEquals(0.1, dataset.getRangeBounds(true).getLowerBound(), 1e-9);
    assertEquals(6.2, dataset.getRangeBounds(true).getUpperBound(), 1e-9);
}

@Test
void testUpdateBoundsWithNullValues() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    // Item with null minOutlier and maxOutlier
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(null, null, null, null, null, null, null, null, new java.util.ArrayList<>());
    dataset.add(item, "Row1", "Col1");

    assertEquals(Double.NaN, dataset.getRangeBounds(true).getLowerBound(), 1e-9);
    assertEquals(Double.NaN, dataset.getRangeBounds(true).getUpperBound(), 1e-9);
}

@Test
void testUpdateBoundsWithNaNValues() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    // Item with NaN minOutlier and maxOutlier
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(
        Double.NaN, Double.NaN, Double.NaN, Double.NaN,
        Double.NaN, Double.NaN, Double.NaN, Double.NaN,
        new java.util.ArrayList<>()
    );
    dataset.add(item, "Row1", "Col1");

    assertEquals(Double.NaN, dataset.getRangeBounds(true).getLowerBound(), 1e-9);
    assertEquals(Double.NaN, dataset.getRangeBounds(true).getUpperBound(), 1e-9);
}
