@Test
void testGetLegendItemsWithNoPlotReturnsEmptyCollection() {
    AbstractCategoryItemRenderer renderer =
            mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(0, result.getItemCount());
}

@Test
void testGetLegendItemsWithNullDatasetReturnsEmptyCollection() {
    AbstractCategoryItemRenderer renderer =
            mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);
    CategoryPlot plot = mock(CategoryPlot.class);

    renderer.setPlot(plot);
    when(plot.getIndexOf(renderer)).thenReturn(0);
    when(plot.getDataset(0)).thenReturn(null);

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(0, result.getItemCount());
}

@Test
void testGetLegendItemsIncludesOnlySeriesVisibleInLegend() {
    AbstractCategoryItemRenderer renderer =
            mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);
    CategoryPlot plot = mock(CategoryPlot.class);
    CategoryDataset dataset = mock(CategoryDataset.class);
    LegendItem firstItem = mock(LegendItem.class);
    LegendItem thirdItem = mock(LegendItem.class);

    renderer.setPlot(plot);
    when(plot.getIndexOf(renderer)).thenReturn(1);
    when(plot.getDataset(1)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(3);
    when(renderer.isSeriesVisibleInLegend(0)).thenReturn(true);
    when(renderer.isSeriesVisibleInLegend(1)).thenReturn(false);
    when(renderer.isSeriesVisibleInLegend(2)).thenReturn(true);
    when(renderer.getLegendItem(1, 0)).thenReturn(firstItem);
    when(renderer.getLegendItem(1, 2)).thenReturn(thirdItem);

    LegendItemCollection result = renderer.getLegendItems();

    assertEquals(2, result.getItemCount());
    assertSame(firstItem, result.get(0));
    assertSame(thirdItem, result.get(1));
    verify(renderer, never()).getLegendItem(1, 1);
}
