@Test
void getLegendItems_plotIsNull_returnsEmptyCollection() {
    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(0, result.getItemCount(), "Expected an empty LegendItemCollection when plot is null");
    verifyNoInteractions(mockPlot);
}

@Test
void getLegendItems_datasetIsNull_returnsEmptyCollection() {
    renderer.setPlot(mockPlot);
    when(mockPlot.getIndexOf(renderer)).thenReturn(0);
    when(mockPlot.getDataset(anyInt())).thenReturn(null);

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(0, result.getItemCount(), "Expected an empty LegendItemCollection when dataset is null");
    verify(mockPlot).getIndexOf(renderer);
    verify(mockPlot).getDataset(anyInt());
    verifyNoMoreInteractions(mockPlot);
    verifyNoInteractions(mockDataset);
}

@Test
void getLegendItems_noSeriesVisible_returnsEmptyCollection() {
    int rowCount = 3;
    renderer.setPlot(mockPlot);
    when(mockPlot.getIndexOf(renderer)).thenReturn(0);
    when(mockPlot.getDataset(anyInt())).thenReturn(mockDataset);
    when(mockDataset.getRowCount()).thenReturn(rowCount);

    doReturn(false).when(renderer).isSeriesVisibleInLegend(anyInt());

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(0, result.getItemCount(), "Expected an empty LegendItemCollection when no series are visible");
    verify(mockPlot).getIndexOf(renderer);
    verify(mockPlot).getDataset(anyInt());
    verify(mockDataset).getRowCount();
    verify(renderer, times(rowCount)).isSeriesVisibleInLegend(anyInt());
    verify(renderer, never()).getLegendItem(anyInt(), anyInt());
}

@Test
void getLegendItems_allSeriesVisible_returnsCollectionWithAllItems() {
    int rowCount = 3;
    int datasetIndex = 0;
    renderer.setPlot(mockPlot);
    when(mockPlot.getIndexOf(renderer)).thenReturn(datasetIndex);
    when(mockPlot.getDataset(anyInt())).thenReturn(mockDataset);
    when(mockDataset.getRowCount()).thenReturn(rowCount);

    doReturn(true).when(renderer).isSeriesVisibleInLegend(anyInt());

    LegendItem item0 = new LegendItem("Series 0", "", "", "", null, null);
    LegendItem item1 = new LegendItem("Series 1", "", "", "", null, null);
    LegendItem item2 = new LegendItem("Series 2", "", "", "", null, null);

    doReturn(item0).when(renderer).getLegendItem(datasetIndex, 0);
    doReturn(item1).when(renderer).getLegendItem(datasetIndex, 1);
    doReturn(item2).when(renderer).getLegendItem(datasetIndex, 2);

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(rowCount, result.getItemCount(), "Expected LegendItemCollection with all items");
    assertTrue(result.getItems().containsAll(java.util.Arrays.asList(item0, item1, item2)));

    verify(mockPlot).getIndexOf(renderer);
    verify(mockPlot).getDataset(datasetIndex);
    verify(mockDataset).getRowCount();
    verify(renderer, times(rowCount)).isSeriesVisibleInLegend(anyInt());
    verify(renderer).getLegendItem(datasetIndex, 0);
    verify(renderer).getLegendItem(datasetIndex, 1);
    verify(renderer).getLegendItem(datasetIndex, 2);
}

@Test
void getLegendItems_someSeriesVisible_returnsCollectionWithSelectedItems() {
    int rowCount = 3;
    int datasetIndex = 0;
    renderer.setPlot(mockPlot);
    when(mockPlot.getIndexOf(renderer)).thenReturn(datasetIndex);
    when(mockPlot.getDataset(anyInt())).thenReturn(mockDataset);
    when(mockDataset.getRowCount()).thenReturn(rowCount);

    doReturn(true).when(renderer).isSeriesVisibleInLegend(0);
    doReturn(false).when(renderer).isSeriesVisibleInLegend(1);
    doReturn(true).when(renderer).isSeriesVisibleInLegend(2);

    LegendItem item0 = new LegendItem("Series 0", "", "", "", null, null);
    LegendItem item2 = new LegendItem("Series 2", "", "", "", null, null);

    doReturn(item0).when(renderer).getLegendItem(datasetIndex, 0);
    doReturn(item2).when(renderer).getLegendItem(datasetIndex, 2);

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(2, result.getItemCount(), "Expected LegendItemCollection with 2 items");
    assertTrue(result.getItems().contains(item0));
    assertFalse(result.getItems().contains(new LegendItem("Series 1", "", "", "", null, null)));
    assertTrue(result.getItems().contains(item2));

    verify(mockPlot).getIndexOf(renderer);
    verify(mockPlot).getDataset(datasetIndex);
    verify(mockDataset).getRowCount();
    verify(renderer).isSeriesVisibleInLegend(0);
    verify(renderer).isSeriesVisibleInLegend(1);
    verify(renderer).isSeriesVisibleInLegend(2);
    verify(renderer, times(1)).getLegendItem(datasetIndex, 0);
    verify(renderer, never()).getLegendItem(datasetIndex, 1);
    verify(renderer, times(1)).getLegendItem(datasetIndex, 2);
}

@Test
void getLegendItems_emptyDataset_returnsEmptyCollection() {
    int rowCount = 0;
    int datasetIndex = 0;
    renderer.setPlot(mockPlot);
    when(mockPlot.getIndexOf(renderer)).thenReturn(datasetIndex);
    when(mockPlot.getDataset(anyInt())).thenReturn(mockDataset);
    when(mockDataset.getRowCount()).thenReturn(rowCount);

    LegendItemCollection result = renderer.getLegendItems();

    assertNotNull(result);
    assertEquals(0, result.getItemCount(), "Expected an empty LegendItemCollection for an empty dataset");

    verify(mockPlot).getIndexOf(renderer);
    verify(mockPlot).getDataset(datasetIndex);
    verify(mockDataset).getRowCount();
    verify(renderer, never()).isSeriesVisibleInLegend(anyInt());
    verify(renderer, never()).getLegendItem(anyInt(), anyInt());
}
