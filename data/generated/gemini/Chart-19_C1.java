@Test
void testGetDomainAxisIndex_WithNullAxis_ThrowsIllegalArgumentException() {
    CategoryPlot plot = new CategoryPlot();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        plot.getDomainAxisIndex(null);
    });

    assertEquals("Null 'axis' argument.", exception.getMessage());
}

@Test
void testGetDomainAxisIndex_WithExistingAxis_ReturnsCorrectIndex() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis1 = mock(CategoryAxis.class);
    CategoryAxis axis2 = mock(CategoryAxis.class);

    plot.setDomainAxis(0, axis1);
    plot.setDomainAxis(1, axis2);

    assertEquals(0, plot.getDomainAxisIndex(axis1));
    assertEquals(1, plot.getDomainAxisIndex(axis2));
}

@Test
void testGetDomainAxisIndex_WithNonExistentAxis_ReturnsNegativeOne() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis existingAxis = mock(CategoryAxis.class);
    CategoryAxis nonExistentAxis = mock(CategoryAxis.class);

    plot.setDomainAxis(0, existingAxis);

    assertEquals(-1, plot.getDomainAxisIndex(nonExistentAxis));
}
