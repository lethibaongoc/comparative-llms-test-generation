@Test
void testGetDomainAxisIndexWithNullAxis() {
    CategoryPlot plot = new CategoryPlot();
    assertThrows(IllegalArgumentException.class, () -> plot.getDomainAxisIndex(null));
}

@Test
void testGetDomainAxisIndexWithExistingAxis() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis = new CategoryAxis("Test Axis");
    plot.setDomainAxis(axis);
    assertEquals(0, plot.getDomainAxisIndex(axis));
}

@Test
void testGetDomainAxisIndexWithNonExistentAxis() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis1 = new CategoryAxis("Axis 1");
    CategoryAxis axis2 = new CategoryAxis("Axis 2");
    plot.setDomainAxis(axis1);
    assertEquals(-1, plot.getDomainAxisIndex(axis2));
}

@Test
void testGetDomainAxisIndexWithMultipleAxes() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis1 = new CategoryAxis("Axis 1");
    CategoryAxis axis2 = new CategoryAxis("Axis 2");
    CategoryAxis axis3 = new CategoryAxis("Axis 3");
    plot.setDomainAxis(0, axis1);
    plot.setDomainAxis(1, axis2);
    plot.setDomainAxis(2, axis3);
    assertEquals(0, plot.getDomainAxisIndex(axis1));
    assertEquals(1, plot.getDomainAxisIndex(axis2));
    assertEquals(2, plot.getDomainAxisIndex(axis3));
}

@Test
void testGetDomainAxisIndexWithSameAxisAddedMultipleTimes() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis = new CategoryAxis("Test Axis");
    plot.setDomainAxis(axis);
    plot.setDomainAxis(axis);
    assertEquals(0, plot.getDomainAxisIndex(axis));
}
