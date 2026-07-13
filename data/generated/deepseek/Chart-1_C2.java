@Test
void testGetLegendItems_plotIsNull() {
    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertTrue(result.isEmpty());
}

@Test
void testGetLegendItems_datasetIsNull() {
    int expectedIndex = 0;

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(null);

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    verify(obj, never()).isSeriesVisibleInLegend(anyInt());
    verify(obj, never()).getLegendItem(anyInt(), anyInt());
}

@Test
void testGetLegendItems_datasetHasZeroRows() {
    int expectedIndex = 0;

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(0);

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    verify(dataset).getRowCount();
    verify(obj, never()).isSeriesVisibleInLegend(anyInt());
    verify(obj, never()).getLegendItem(anyInt(), anyInt());
}

@Test
void testGetLegendItems_allSeriesVisible() {
    int expectedIndex = 0;
    int rowCount = 3;
    LegendItem legendItem0 = mock(LegendItem.class);
    LegendItem legendItem1 = mock(LegendItem.class);
    LegendItem legendItem2 = mock(LegendItem.class);

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(rowCount);

    doReturn(true).when(obj).isSeriesVisibleInLegend(0);
    doReturn(true).when(obj).isSeriesVisibleInLegend(1);
    doReturn(true).when(obj).isSeriesVisibleInLegend(2);

    doReturn(legendItem0).when(obj).getLegendItem(expectedIndex, 0);
    doReturn(legendItem1).when(obj).getLegendItem(expectedIndex, 1);
    doReturn(legendItem2).when(obj).getLegendItem(expectedIndex, 2);

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertEquals(rowCount, result.getItemCount());
    assertTrue(result.contains(legendItem0));
    assertTrue(result.contains(legendItem1));
    assertTrue(result.contains(legendItem2));

    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    verify(dataset).getRowCount();

    verify(obj, times(rowCount)).isSeriesVisibleInLegend(anyInt());
    verify(obj).isSeriesVisibleInLegend(0);
    verify(obj).isSeriesVisibleInLegend(1);
    verify(obj).isSeriesVisibleInLegend(2);

    verify(obj, times(rowCount)).getLegendItem(anyInt(), anyInt());
    verify(obj).getLegendItem(expectedIndex, 0);
    verify(obj).getLegendItem(expectedIndex, 1);
    verify(obj).getLegendItem(expectedIndex, 2);
}

@Test
void testGetLegendItems_noSeriesVisible() {
    int expectedIndex = 0;
    int rowCount = 2;

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(rowCount);

    doReturn(false).when(obj).isSeriesVisibleInLegend(anyInt());
    doReturn(mock(LegendItem.class)).when(obj).getLegendItem(anyInt(), anyInt());

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    verify(dataset).getRowCount();

    verify(obj, times(rowCount)).isSeriesVisibleInLegend(anyInt());
    verify(obj).isSeriesVisibleInLegend(0);
    verify(obj).isSeriesVisibleInLegend(1);

    verify(obj, never()).getLegendItem(anyInt(), anyInt());
}

@Test
void testGetLegendItems_someSeriesVisible() {
    int expectedIndex = 0;
    int rowCount = 3;
    LegendItem legendItem0 = mock(LegendItem.class);
    LegendItem legendItem2 = mock(LegendItem.class);

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(rowCount);

    doReturn(true).when(obj).isSeriesVisibleInLegend(0);
    doReturn(false).when(obj).isSeriesVisibleInLegend(1);
    doReturn(true).when(obj).isSeriesVisibleInLegend(2);

    doReturn(legendItem0).when(obj).getLegendItem(expectedIndex, 0);
    doReturn(legendItem2).when(obj).getLegendItem(expectedIndex, 2);

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertEquals(2, result.getItemCount());
    assertTrue(result.contains(legendItem0));
    assertTrue(result.contains(legendItem2));

    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    verify(dataset).getRowCount();

    verify(obj, times(rowCount)).isSeriesVisibleInLegend(anyInt());
    verify(obj).isSeriesVisibleInLegend(0);
    verify(obj).isSeriesVisibleInLegend(1);
    verify(obj).isSeriesVisibleInLegend(2);

    verify(obj, times(2)).getLegendItem(anyInt(), anyInt());
    verify(obj).getLegendItem(expectedIndex, 0);
    verify(obj, never()).getLegendItem(expectedIndex, 1);
    verify(obj).getLegendItem(expectedIndex, 2);
}

@Test
void testGetLegendItems_getLegendItemReturnsNull() {
    int expectedIndex = 0;
    int rowCount = 1;

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(rowCount);

    doReturn(true).when(obj).isSeriesVisibleInLegend(0);
    doReturn(null).when(obj).getLegendItem(expectedIndex, 0);

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    verify(dataset).getRowCount();
    verify(obj).isSeriesVisibleInLegend(0);
    verify(obj).getLegendItem(expectedIndex, 0);
}
