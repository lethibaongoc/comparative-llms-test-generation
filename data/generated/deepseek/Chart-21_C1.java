@Test
void shouldSetMinAndMaxToNaNAndNegativeIndicesWhenDatasetIsEmpty() {
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
void shouldUpdateBoundsWithSingleValidItem() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = mock(BoxAndWhiskerItem.class);
    when(item.getMinOutlier()).thenReturn(5.0);
    when(item.getMaxOutlier()).thenReturn(15.0);
    dataset.addItem(item, "Series", "Category");
    dataset.updateBounds();
    assertEquals(5.0, dataset.getMinimumRangeValue());
    assertEquals(0, dataset.getMinimumRangeValueRow());
    assertEquals(0, dataset.getMinimumRangeValueColumn());
    assertEquals(15.0, dataset.getMaximumRangeValue());
    assertEquals(0, dataset.getMaximumRangeValueRow());
    assertEquals(0, dataset.getMaximumRangeValueColumn());
}

@Test
void shouldIgnoreNullMinOutlier() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = mock(BoxAndWhiskerItem.class);
    when(item.getMinOutlier()).thenReturn(null);
    when(item.getMaxOutlier()).thenReturn(10.0);
    dataset.addItem(item, "Series", "Category");
    dataset.updateBounds();
    assertEquals(Double.NaN, dataset.getMinimumRangeValue());
    assertEquals(10.0, dataset.getMaximumRangeValue());
}

@Test
void shouldIgnoreNullMaxOutlier() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = mock(BoxAndWhiskerItem.class);
    when(item.getMinOutlier()).thenReturn(5.0);
    when(item.getMaxOutlier()).thenReturn(null);
    dataset.addItem(item, "Series", "Category");
    dataset.updateBounds();
    assertEquals(5.0, dataset.getMinimumRangeValue());
    assertEquals(Double.NaN, dataset.getMaximumRangeValue());
}

@Test
void shouldIgnoreNaNMinOutlier() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = mock(BoxAndWhiskerItem.class);
    when(item.getMinOutlier()).thenReturn(Double.NaN);
    when(item.getMaxOutlier()).thenReturn(20.0);
    dataset.addItem(item, "Series", "Category");
    dataset.updateBounds();
    assertEquals(Double.NaN, dataset.getMinimumRangeValue());
    assertEquals(20.0, dataset.getMaximumRangeValue());
}

@Test
void shouldIgnoreNaNMaxOutlier() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item = mock(BoxAndWhiskerItem.class);
    when(item.getMinOutlier()).thenReturn(5.0);
    when(item.getMaxOutlier()).thenReturn(Double.NaN);
    dataset.addItem(item, "Series", "Category");
    dataset.updateBounds();
    assertEquals(5.0, dataset.getMinimumRangeValue());
    assertEquals(Double.NaN, dataset.getMaximumRangeValue());
}

@Test
void shouldUpdateMinAndMaxAcrossMultipleItems() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item1 = mock(BoxAndWhiskerItem.class);
    when(item1.getMinOutlier()).thenReturn(5.0);
    when(item1.getMaxOutlier()).thenReturn(25.0);
    BoxAndWhiskerItem item2 = mock(BoxAndWhiskerItem.class);
    when(item2.getMinOutlier()).thenReturn(1.0);
    when(item2.getMaxOutlier()).thenReturn(30.0);
    dataset.addItem(item1, "Series1", "Category1");
    dataset.addItem(item2, "Series2", "Category2");
    dataset.updateBounds();
    assertEquals(1.0, dataset.getMinimumRangeValue());
    assertEquals(1, dataset.getMinimumRangeValueRow());
    assertEquals(1, dataset.getMinimumRangeValueColumn());
    assertEquals(30.0, dataset.getMaximumRangeValue());
    assertEquals(1, dataset.getMaximumRangeValueRow());
    assertEquals(1, dataset.getMaximumRangeValueColumn());
}

@Test
void shouldKeepExistingMinWhenNewMinIsHigher() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item1 = mock(BoxAndWhiskerItem.class);
    when(item1.getMinOutlier()).thenReturn(5.0);
    when(item1.getMaxOutlier()).thenReturn(20.0);
    BoxAndWhiskerItem item2 = mock(BoxAndWhiskerItem.class);
    when(item2.getMinOutlier()).thenReturn(10.0);
    when(item2.getMaxOutlier()).thenReturn(30.0);
    dataset.addItem(item1, "Series1", "Category1");
    dataset.addItem(item2, "Series2", "Category2");
    dataset.updateBounds();
    assertEquals(5.0, dataset.getMinimumRangeValue());
    assertEquals(0, dataset.getMinimumRangeValueRow());
    assertEquals(30.0, dataset.getMaximumRangeValue());
    assertEquals(1, dataset.getMaximumRangeValueRow());
}

@Test
void shouldKeepExistingMaxWhenNewMaxIsLower() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem item1 = mock(BoxAndWhiskerItem.class);
    when(item1.getMinOutlier()).thenReturn(5.0);
    when(item1.getMaxOutlier()).thenReturn(30.0);
    BoxAndWhiskerItem item2 = mock(BoxAndWhiskerItem.class);
    when(item2.getMinOutlier()).thenReturn(1.0);
    when(item2.getMaxOutlier()).thenReturn(20.0);
    dataset.addItem(item1, "Series1", "Category1");
    dataset.addItem(item2, "Series2", "Category2");
    dataset.updateBounds();
    assertEquals(1.0, dataset.getMinimumRangeValue());
    assertEquals(1, dataset.getMinimumRangeValueRow());
    assertEquals(30.0, dataset.getMaximumRangeValue());
    assertEquals(0, dataset.getMaximumRangeValueRow());
}

@Test
void shouldHandleNullItemsInDataset() {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    BoxAndWhiskerItem validItem = mock(BoxAndWhiskerItem.class);
    when(validItem.getMinOutlier()).thenReturn(5.0);
    when(validItem.getMaxOutlier()).thenReturn(15.0);
    dataset.addItem(validItem, "Series1", "Category1");
    dataset.addItem(null, "Series2", "Category2");
    dataset.updateBounds();
    assertEquals(5.0, dataset.getMinimumRangeValue());
    assertEquals(15.0, dataset.getMaximumRangeValue());
}
