```java
    @Test
    void getLegendItems_plotIsNull_returnsEmptyCollection() {
        // Arrange
        // The spy's plot field is null by default if not set.
        // Or explicitly: renderer.setPlot(null);

        // Act
        LegendItemCollection result = renderer.getLegendItems();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getItemCount(), "Expected an empty LegendItemCollection when plot is null");
        verifyNoInteractions(mockPlot); // No interaction with the mock plot
    }

    @Test
    void getLegendItems_datasetIsNull_returnsEmptyCollection() {
        // Arrange
        renderer.setPlot(mockPlot); // Set the mock plot
        when(mockPlot.getIndexOf(renderer)).thenReturn(0); // Any index
        when(mockPlot.getDataset(anyInt())).thenReturn(null); // Dataset is null

        // Act
        LegendItemCollection result = renderer.getLegendItems();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getItemCount(), "Expected an empty LegendItemCollection when dataset is null");
        verify(mockPlot).getIndexOf(renderer); // Verify plot.getIndexOf was called
        verify(mockPlot).getDataset(anyInt()); // Verify plot.getDataset was called
        verifyNoMoreInteractions(mockPlot); // No other interactions with plot
        verifyNoInteractions(mockDataset); // No interaction with the mock dataset
    }

    @Test
    void getLegendItems_noSeriesVisible_returnsEmptyCollection() {
        // Arrange
        int rowCount = 3;
        renderer.setPlot(mockPlot);
        when(mockPlot.getIndexOf(renderer)).thenReturn(0);
        when(mockPlot.getDataset(anyInt())).thenReturn(mockDataset);
        when(mockDataset.getRowCount()).thenReturn(rowCount);

        // Stub `isSeriesVisibleInLegend` to return false for all series
        doReturn(false).when(renderer).isSeriesVisibleInLegend(anyInt());

        // Act
        LegendItemCollection result = renderer.getLegendItems();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getItemCount(), "Expected an empty LegendItemCollection when no series are visible");
        verify(mockPlot).getIndexOf(renderer);
        verify(mockPlot).getDataset(anyInt());
        verify(mockDataset).getRowCount();
        // Verify isSeriesVisibleInLegend was called for each series
        verify(renderer, times(rowCount)).isSeriesVisibleInLegend(anyInt());
        // Verify getLegendItem was never called
        verify(renderer, never()).getLegendItem(anyInt(), anyInt());
    }

    @Test
    void getLegendItems_allSeriesVisible_returnsCollectionWithAllItems() {
        // Arrange
        int rowCount = 3;
        int datasetIndex = 0;
        renderer.setPlot(mockPlot);
        when(mockPlot.getIndexOf(renderer)).thenReturn(datasetIndex);
        when(mockPlot.getDataset(anyInt())).thenReturn(mockDataset);
        when(mockDataset.getRowCount()).thenReturn(rowCount);

        // Stub `isSeriesVisibleInLegend` to return true for all series
        doReturn(true).when(renderer).isSeriesVisibleInLegend(anyInt());

        // Stub `getLegendItem` to return specific items
        LegendItem item0 = new LegendItem("Series 0", "", "", "", null, null);
        LegendItem item1 = new LegendItem("Series 1", "", "", "", null, null);
        LegendItem item2 = new LegendItem("Series 2", "", "", "", null, null);

        doReturn(item0).when(renderer).getLegendItem(datasetIndex, 0);
        doReturn(item1).when(renderer).getLegendItem(datasetIndex, 1);
        doReturn(item2).when(renderer).getLegendItem(datasetIndex, 2);

        // Act
        LegendItemCollection result = renderer.getLegendItems();

        // Assert
        assertNotNull(result);
        assertEquals(rowCount, result.getItemCount(), "Expected LegendItemCollection with all items");
        assertTrue(result.getItems().containsAll(java.util.Arrays.asList(item0, item1, item2)), "Expected all stubbed items to be in the collection");

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
        // Arrange
        int rowCount = 3;
        int datasetIndex = 0;
        renderer.setPlot(mockPlot);
        when(mockPlot.getIndexOf(renderer)).thenReturn(datasetIndex);
        when(mockPlot.getDataset(anyInt())).thenReturn(mockDataset);
        when(mockDataset.getRowCount()).thenReturn(rowCount);

        // Stub `isSeriesVisibleInLegend` selectively
        doReturn(true).when(renderer).isSeriesVisibleInLegend(0); // Visible
        doReturn(false).when(renderer).isSeriesVisibleInLegend(1); // Not visible
        doReturn(true).when(renderer).isSeriesVisibleInLegend(2); // Visible

        // Stub `getLegendItem` for visible series
        LegendItem item0 = new LegendItem("Series 0", "", "", "", null, null);
        LegendItem item2 = new LegendItem("Series 2", "", "", "", null, null);

        doReturn(item0).when(renderer).getLegendItem(datasetIndex, 0);
        doReturn(item2).when(renderer).getLegendItem(datasetIndex, 2);

        // Act
        LegendItemCollection result = renderer.getLegendItems();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItemCount(), "Expected LegendItemCollection with 2 items");
        assertTrue(result.getItems().contains(item0), "Expected item0 to be in the collection");
        assertFalse(result.getItems().contains(new LegendItem("Series 1", "", "", "", null, null)), "Did not expect item1 to be in the collection");
        assertTrue(result.getItems().contains(item2), "Expected item2 to be in the collection");

        verify(mockPlot).getIndexOf(renderer);
        verify(mockPlot).getDataset(datasetIndex);
        verify(mockDataset).getRowCount();
        verify(renderer).isSeriesVisibleInLegend(0);
        verify(renderer).isSeriesVisibleInLegend(1); // Still called to check visibility
        verify(renderer).isSeriesVisibleInLegend(2);
        verify(renderer, times(1)).getLegendItem(datasetIndex, 0);
        verify(renderer, never()).getLegendItem(datasetIndex, 1); // Not called for invisible series
        verify(renderer, times(1)).getLegendItem(datasetIndex, 2);
    }

    @Test
    void getLegendItems_emptyDataset_returnsEmptyCollection() {
        // Arrange
        int rowCount = 0; // Empty dataset
        int datasetIndex = 0;
        renderer.setPlot(mockPlot);
        when(mockPlot.getIndexOf(renderer)).thenReturn(datasetIndex);
        when(mockPlot.getDataset(anyInt())).thenReturn(mockDataset);
        when(mockDataset.getRowCount()).thenReturn(rowCount);

        // Act
        LegendItemCollection result = renderer.getLegendItems();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getItemCount(), "Expected an empty LegendItemCollection for an empty dataset");

        verify(mockPlot).getIndexOf(renderer);
        verify(mockPlot).getDataset(datasetIndex);
        verify(mockDataset).getRowCount();
        verify(renderer, never()).isSeriesVisibleInLegend(anyInt()); // Loop should not run
        verify(renderer, never()).getLegendItem(anyInt(), anyInt());
    }
```