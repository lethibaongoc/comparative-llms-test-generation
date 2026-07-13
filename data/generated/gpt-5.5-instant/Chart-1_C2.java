@Test
public void testGetLegendItems_noPlot_returnsEmptyCollection() {
    AbstractCategoryItemRenderer renderer =
        mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(0, result.getItemCount());
}

@Test
public void testGetLegendItems_nullDataset_returnsEmptyCollection() {
    AbstractCategoryItemRenderer renderer =
        mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);
    CategoryPlot plot = mock(CategoryPlot.class);

    renderer.setPlot(plot);
    when(plot.getIndexOf(renderer)).thenReturn(1);
    when(plot.getDataset(1)).thenReturn(null);

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(0, result.getItemCount());
}

@Test
public void testGetLegendItems_addsOnlyVisibleSeries() {
    AbstractCategoryItemRenderer renderer =
        mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);
    CategoryPlot plot = mock(CategoryPlot.class);
    CategoryDataset dataset = mock(CategoryDataset.class);
    LegendItem firstItem = mock(LegendItem.class);
    LegendItem thirdItem = mock(LegendItem.class);

    renderer.setPlot(plot);
    when(plot.getIndexOf(renderer)).thenReturn(2);
    when(plot.getDataset(2)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(3);

    doReturn(true).when(renderer).isSeriesVisibleInLegend(0);
    doReturn(false).when(renderer).isSeriesVisibleInLegend(1);
    doReturn(true).when(renderer).isSeriesVisibleInLegend(2);
    doReturn(firstItem).when(renderer).getLegendItem(2, 0);
    doReturn(thirdItem).when(renderer).getLegendItem(2, 2);

    LegendItemCollection result = renderer.getLegendItems();

    assertEquals(2, result.getItemCount());
    assertSame(firstItem, result.get(0));
    assertSame(thirdItem, result.get(1));
    verify(renderer, never()).getLegendItem(2, 1);
}

@Test
public void testGetLegendItems_allSeriesHidden_returnsEmptyCollection() {
    AbstractCategoryItemRenderer renderer =
        mock(AbstractCategoryItemRenderer.class, CALLS_REAL_METHODS);
    CategoryPlot plot = mock(CategoryPlot.class);
    CategoryDataset dataset = mock(CategoryDataset.class);

    renderer.setPlot(plot);
    when(plot.getIndexOf(renderer)).thenReturn(0);
    when(plot.getDataset(0)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(2);
    doReturn(false).when(renderer).isSeriesVisibleInLegend(0);
    doReturn(false).when(renderer).isSeriesVisibleInLegend(1);

    LegendItemCollection result = renderer.getLegendItems();

    assertEquals(0, result.getItemCount());
    verify(renderer, never()).getLegendItem(anyInt(), anyInt());
}
