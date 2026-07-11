@Test
void testGetLegendItems_plotIsNull() {
    // Assuming 'obj' is an instance of AbstractCategoryItemRenderer (or a spy)
    // and its 'plot' field is null at the start of this test.
    // No mocks for plot are needed as it should not be accessed.
    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    // Verify no interaction with a potential 'plot' mock if it were passed in,
    // as the method should return early.
    // verifyNoInteractions(plot);
}

@Test
void testGetLegendItems_datasetIsNull() {
    // Assuming 'obj' has its 'plot' field set to the 'plot' mock.
    int expectedIndex = 0;

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(null); // Dataset is null

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    // No interactions with 'dataset' mock as it's null.
    // verifyNoInteractions(dataset);
    verify(obj, never()).isSeriesVisibleInLegend(anyInt());
    verify(obj, never()).getLegendItem(anyInt(), anyInt());
}

@Test
void testGetLegendItems_datasetHasZeroRows() {
    // Assuming 'obj' has its 'plot' field set to the 'plot' mock.
    int expectedIndex = 0;

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(0); // Dataset has 0 rows

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
    // Assuming 'obj' has its 'plot' field set to the 'plot' mock.
    int expectedIndex = 0;
    int rowCount = 3;
    LegendItem legendItem0 = mock(LegendItem.class);
    LegendItem legendItem1 = mock(LegendItem.class);
    LegendItem legendItem2 = mock(LegendItem.class);

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(rowCount);

    // All series visible
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
    // Assuming 'obj' has its 'plot' field set to the 'plot' mock.
    int expectedIndex = 0;
    int rowCount = 2;

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(rowCount);

    // No series visible
    doReturn(false).when(obj).isSeriesVisibleInLegend(anyInt());
    // Stub getLegendItem to return a mock item in case it's called (it shouldn't be)
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

    verify(obj, never()).getLegendItem(anyInt(), anyInt()); // Should not be called
}

@Test
void testGetLegendItems_someSeriesVisible() {
    // Assuming 'obj' has its 'plot' field set to the 'plot' mock.
    int expectedIndex = 0;
    int rowCount = 3;
    LegendItem legendItem0 = mock(LegendItem.class);
    LegendItem legendItem2 = mock(LegendItem.class);

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(rowCount);

    // Series 0 visible, 1 not, 2 visible
    doReturn(true).when(obj).isSeriesVisibleInLegend(0);
    doReturn(false).when(obj).isSeriesVisibleInLegend(1);
    doReturn(true).when(obj).isSeriesVisibleInLegend(2);

    doReturn(legendItem0).when(obj).getLegendItem(expectedIndex, 0);
    // Don't stub getLegendItem for index 1 as it should not be called if not visible.
    // If it were called, it might return a mock LegendItem that we wouldn't expect in the collection.
    doReturn(legendItem2).when(obj).getLegendItem(expectedIndex, 2);

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertEquals(2, result.getItemCount());
    assertTrue(result.contains(legendItem0));
    assertFalse(result.contains(legendItem1)); // Ensure item for series 1 is not added
    assertTrue(result.contains(legendItem2));

    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    verify(dataset).getRowCount();

    verify(obj, times(rowCount)).isSeriesVisibleInLegend(anyInt());
    verify(obj).isSeriesVisibleInLegend(0);
    verify(obj).isSeriesVisibleInLegend(1); // isSeriesVisibleInLegend is still called for all series
    verify(obj).isSeriesVisibleInLegend(2);

    verify(obj, times(2)).getLegendItem(anyInt(), anyInt()); // Called for series 0 and 2
    verify(obj).getLegendItem(expectedIndex, 0);
    verify(obj, never()).getLegendItem(expectedIndex, 1); // Not called for series 1
    verify(obj).getLegendItem(expectedIndex, 2);
}

@Test
void testGetLegendItems_getLegendItemReturnsNull() {
    // Assuming 'obj' has its 'plot' field set to the 'plot' mock.
    int expectedIndex = 0;
    int rowCount = 1;

    when(plot.getIndexOf(obj)).thenReturn(expectedIndex);
    when(plot.getDataset(expectedIndex)).thenReturn(dataset);
    when(dataset.getRowCount()).thenReturn(rowCount);

    doReturn(true).when(obj).isSeriesVisibleInLegend(0);
    doReturn(null).when(obj).getLegendItem(expectedIndex, 0); // getLegendItem returns null

    LegendItemCollection result = obj.getLegendItems();

    assertNotNull(result);
    assertTrue(result.isEmpty()); // JFreeChart's LegendItemCollection.add(null) gracefully ignores it.

    verify(plot).getIndexOf(obj);
    verify(plot).getDataset(expectedIndex);
    verify(dataset).getRowCount();
    verify(obj).isSeriesVisibleInLegend(0);
    verify(obj).getLegendItem(expectedIndex, 0);
}