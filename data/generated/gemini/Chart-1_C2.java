@Test
void testGetLegendItemsWhenPlotIsNull() {
    AbstractCategoryItemRenderer renderer = mock(AbstractCategoryItemRenderer.class);
    when(renderer.getPlot()).thenReturn(null);
    when(renderer.getLegendItems()).thenCallRealMethod();

    LegendItemCollection result = renderer.getLegendItems();
    assertNotNull(result);
    assertEquals(0, result.getItemCount());
}

@Test
void testGetLegendItemsWhenDatasetIsNull() {
    AbstractCategoryItemRenderer renderer = mock(AbstractCategoryItemRenderer.class);
    CategoryPlot plot = mock(CategoryPlot.class);

    when(renderer.getPlot()).thenReturn(plot);
    when(plot.getIndexOf(renderer)).thenReturn(0);
    when(plot.getDataset(0)).thenReturn(null);
    when(renderer.getLegendItems()).thenCallRealMethod();

    LegendItemCollection result = renderer.getLegendItems();
    assertNotNull(result);
    assertEquals(0, result.getItemCount());
}

@Test
void testGetLegendItemsWithVisibleAndInvisibleSeries() {
    AbstractCategoryItemRenderer renderer = mock(AbstractCategoryItemRenderer.class);
    CategoryPlot plot = mock(CategoryPlot.class);
    CategoryDataset dataset = mock(CategoryDataset.class);
    LegendItem item1 = mock(LegendItem.class);
    LegendItem item2 = mock(LegendItem.class);

    when(renderer.getPlot()).thenReturn(plot);
    when(plot.getIndexOf(renderer)).thenReturn(1);
    when(plot.getDataset(1)).thenReturn(dataset);

    // Dataset has 3 series
    when(dataset.getRowCount()).thenReturn(3);

    // Series 0 and 2 are visible in legend, series 1 is not
    when(renderer.isSeriesVisibleInLegend(0)).thenReturn(true);
    when(renderer.isSeriesVisibleInLegend(1)).thenReturn(false);
    when(renderer.isSeriesVisibleInLegend(2)).thenReturn(true);

    when(renderer.getLegendItem(1, 0)).thenReturn(item1);
    when(renderer.getLegendItem(1, 2)).thenReturn(item2);
    when(renderer.getLegendItems()).thenCallRealMethod();

    LegendItemCollection result = renderer.getLegendItems();
    assertNotNull(result);
    assertEquals(2, result.getItemCount());
    assertEquals(item1, result.get(0));
    assertEquals(item2, result.get(1));
}
