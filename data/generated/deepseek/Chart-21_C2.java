@Test
void testUpdateBoundsWithEmptyDataset() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    dataset.updateBounds();
    assertEquals(Double.NaN, dataset.getMinimumRangeValue());
    assertEquals(-1, dataset.getMinimumRangeValueRow());
    assertEquals(-1, dataset.getMinimumRangeValueColumn());
    assertEquals(Double.NaN, dataset.getMaximumRangeValue());
    assertEquals(-1, dataset.getMaximumRangeValueRow());
    assertEquals(-1, dataset.getMaximumRangeValueColumn());
}

@Test
void testUpdateBoundsWithSingleItem() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(
        5.0, 3.0, 2.0, 1.0, 4.0, 6.0, 0.5, 7.0, null
    );
    dataset.add(item, "Series1", "Category1");
    dataset.updateBounds();
    assertEquals(7.0, dataset.getMaximumRangeValue());
    assertEquals(0, dataset.getMaximumRangeValueRow());
    assertEquals(0, dataset.getMaximumRangeValueColumn());
    assertEquals(0.5, dataset.getMinimumRangeValue());
    assertEquals(0, dataset.getMinimumRangeValueRow());
    assertEquals(0, dataset.getMinimumRangeValueColumn());
}

@Test
void testUpdateBoundsWithMultipleItems() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(
        10.0, 8.0, 5.0, 3.0, 7.0, 12.0, 2.0, 15.0, null
    );
    BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(
        20.0, 18.0, 15.0, 12.0, 17.0, 22.0, 10.0, 25.0, null
    );
    dataset.add(item1, "Series1", "Category1");
    dataset.add(item2, "Series1", "Category2");
    dataset.updateBounds();
    assertEquals(25.0, dataset.getMaximumRangeValue());
    assertEquals(0, dataset.getMaximumRangeValueRow());
    assertEquals(1, dataset.getMaximumRangeValueColumn());
    assertEquals(2.0, dataset.getMinimumRangeValue());
    assertEquals(0, dataset.getMinimumRangeValueRow());
    assertEquals(0, dataset.getMinimumRangeValueColumn());
}

@Test
void testUpdateBoundsWithNullItem() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    dataset.add(null, "Series1", "Category1");
    dataset.updateBounds();
    assertEquals(Double.NaN, dataset.getMinimumRangeValue());
    assertEquals(-1, dataset.getMinimumRangeValueRow());
    assertEquals(-1, dataset.getMinimumRangeValueColumn());
    assertEquals(Double.NaN, dataset.getMaximumRangeValue());
    assertEquals(-1, dataset.getMaximumRangeValueRow());
    assertEquals(-1, dataset.getMaximumRangeValueColumn());
}

@Test
void testUpdateBoundsWithNullMinOutlier() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(
        5.0, 3.0, 2.0, 1.0, 4.0, 6.0, null, 7.0, null
    );
    dataset.add(item, "Series1", "Category1");
    dataset.updateBounds();
    assertEquals(Double.NaN, dataset.getMinimumRangeValue());
    assertEquals(-1, dataset.getMinimumRangeValueRow());
    assertEquals(-1, dataset.getMinimumRangeValueColumn());
    assertEquals(7.0, dataset.getMaximumRangeValue());
    assertEquals(0, dataset.getMaximumRangeValueRow());
    assertEquals(0, dataset.getMaximumRangeValueColumn());
}

@Test
void testUpdateBoundsWithNullMaxOutlier() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(
        5.0, 3.0, 2.0, 1.0, 4.0, 6.0, 0.5, null, null
    );
    dataset.add(item, "Series1", "Category1");
    dataset.updateBounds();
    assertEquals(0.5, dataset.getMinimumRangeValue());
    assertEquals(0, dataset.getMinimumRangeValueRow());
    assertEquals(0, dataset.getMinimumRangeValueColumn());
    assertEquals(Double.NaN, dataset.getMaximumRangeValue());
    assertEquals(-1, dataset.getMaximumRangeValueRow());
    assertEquals(-1, dataset.getMaximumRangeValueColumn());
}

@Test
void testUpdateBoundsWithNaNValues() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(
        Double.NaN, Double.NaN, Double.NaN, Double.NaN,
        Double.NaN, Double.NaN, Double.NaN, Double.NaN, null
    );
    dataset.add(item, "Series1", "Category1");
    dataset.updateBounds();
    assertEquals(Double.NaN, dataset.getMinimumRangeValue());
    assertEquals(-1, dataset.getMinimumRangeValueRow());
    assertEquals(-1, dataset.getMinimumRangeValueColumn());
    assertEquals(Double.NaN, dataset.getMaximumRangeValue());
    assertEquals(-1, dataset.getMaximumRangeValueRow());
    assertEquals(-1, dataset.getMaximumRangeValueColumn());
}

@Test
void testUpdateBoundsWithMultipleRowsAndColumns() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(
        10.0, 8.0, 5.0, 3.0, 7.0, 12.0, 2.0, 15.0, null
    );
    BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(
        5.0, 4.0, 3.0, 2.0, 4.5, 6.0, 1.0, 8.0, null
    );
    BoxAndWhiskerItem item3 = new BoxAndWhiskerItem(
        30.0, 25.0, 20.0, 15.0, 22.0, 35.0, 10.0, 40.0, null
    );
    dataset.add(item1, "Series1", "Category1");
    dataset.add(item2, "Series2", "Category1");
    dataset.add(item3, "Series1", "Category2");
    dataset.updateBounds();
    assertEquals(40.0, dataset.getMaximumRangeValue());
    assertEquals(0, dataset.getMaximumRangeValueRow());
    assertEquals(1, dataset.getMaximumRangeValueColumn());
    assertEquals(1.0, dataset.getMinimumRangeValue());
    assertEquals(1, dataset.getMinimumRangeValueRow());
    assertEquals(0, dataset.getMinimumRangeValueColumn());
}
