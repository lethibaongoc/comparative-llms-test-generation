@Test
void getDomainAxisIndex_ShouldReturnIndex_WhenAxisExists() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis = new CategoryAxis("Test Axis");
    plot.setDomainAxis(axis);
    int index = plot.getDomainAxisIndex(axis);
    assertEquals(0, index);
}

@Test
void getDomainAxisIndex_ShouldReturnCorrectIndex_WhenMultipleAxesExist() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis1 = new CategoryAxis("Axis 1");
    CategoryAxis axis2 = new CategoryAxis("Axis 2");
    CategoryAxis axis3 = new CategoryAxis("Axis 3");
    plot.setDomainAxis(0, axis1);
    plot.setDomainAxis(1, axis2);
    plot.setDomainAxis(2, axis3);
    assertEquals(1, plot.getDomainAxisIndex(axis2));
    assertEquals(2, plot.getDomainAxisIndex(axis3));
}

@Test
void getDomainAxisIndex_ShouldReturnMinusOne_WhenAxisDoesNotExist() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis existingAxis = new CategoryAxis("Existing");
    CategoryAxis nonExistingAxis = new CategoryAxis("Non-Existing");
    plot.setDomainAxis(existingAxis);
    assertEquals(-1, plot.getDomainAxisIndex(nonExistingAxis));
}

@Test
void getDomainAxisIndex_ShouldReturnMinusOne_WhenNoAxesSet() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis = new CategoryAxis("Any Axis");
    assertEquals(-1, plot.getDomainAxisIndex(axis));
}

@Test
void getDomainAxisIndex_ShouldThrowIllegalArgumentException_WhenAxisIsNull() {
    CategoryPlot plot = new CategoryPlot();
    assertThrows(IllegalArgumentException.class, () -> plot.getDomainAxisIndex(null));
}
