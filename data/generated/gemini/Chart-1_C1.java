@Test
void testGetLegendItems_WhenPlotIsNull_ReturnsEmptyCollection() {
    // Arrange
    AbstractCategoryItemRenderer renderer = mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);
    renderer.setPlot(null); // Assuming a setter or direct field access if spy/mock allows

    // Act
    LegendItemCollection result = renderer.getLegendItems();

    // Assert
    assertNotNull(result);
    assertEquals(0, result.getItemCount());
}

@Test
void testGetLegendItems_WhenDatasetIsNull_ReturnsEmptyCollection() {
    // Arrange
    AbstractCategoryItemRenderer renderer = mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);
    CategoryPlot mockPlot = mock(CategoryPlot.class);

    when(mockPlot.getIndexOf(renderer)).thenReturn(1);
    when(mockPlot.getDataset(1)).thenReturn(null);

    renderer.setPlot(mockPlot);

    // Act
    LegendItemCollection result = renderer.getLegendItems();

    // Assert
    assertNotNull(result);
    assertEquals(0, result.getItemCount());
}

@Test
@Disabled("Requires mocking protected methods getLegendItem and isSeriesVisibleInLegend")
void testGetLegendItems_WithVisibleAndHiddenSeries() {
    // Arrange
    AbstractCategoryItemRenderer renderer = mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);
    CategoryPlot mockPlot = mock(CategoryPlot.class);
    CategoryDataset mockDataset = mock(CategoryDataset.class);

    int rendererIndex = 0;
    when(mockPlot.getIndexOf(renderer)).thenReturn(rendererIndex);
    when(mockPlot.getDataset(rendererIndex)).thenReturn(mockDataset);

    // Dataset has 3 series (rows 0, 1, and 2)
    when(mockDataset.getRowCount()).thenReturn(3);

    // Series 0 is visible, Series 1 is not, Series 2 is visible
    doReturn(true).when(renderer).isSeriesVisibleInLegend(0);
    doReturn(false).when(renderer).isSeriesVisibleInLegend(1);
    doReturn(true).when(renderer).isSeriesVisibleInLegend(2);

    LegendItem item0 = mock(LegendItem.class);
    LegendItem item2 = mock(LegendItem.class);

    doReturn(item0).when(renderer).getLegendItem(rendererIndex, 0);
    doReturn(item2).when(renderer).getLegendItem(rendererIndex, 2);

    renderer.setPlot(mockPlot);

    // Act
    LegendItemCollection result = renderer.getLegendItems();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.getItemCount());
    assertEquals(item0, result.get(0));
    assertEquals(item2, result.get(1));
}
