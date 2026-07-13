@Test
public void testGetDomainAxisIndex_existingAxis() {
    CategoryPlot plot = new CategoryPlot();

    CategoryAxis axis1 = new CategoryAxis("Axis 1");
    CategoryAxis axis2 = new CategoryAxis("Axis 2");

    plot.setDomainAxis(0, axis1);
    plot.setDomainAxis(1, axis2);

    assertEquals(0, plot.getDomainAxisIndex(axis1));
    assertEquals(1, plot.getDomainAxisIndex(axis2));
}

@Test
public void testGetDomainAxisIndex_axisNotPresent() {
    CategoryPlot plot = new CategoryPlot();

    CategoryAxis axis1 = new CategoryAxis("Axis 1");
    CategoryAxis axis2 = new CategoryAxis("Axis 2");

    plot.setDomainAxis(axis1);

    assertEquals(-1, plot.getDomainAxisIndex(axis2));
}

@Test
public void testGetDomainAxisIndex_nullAxis() {
    CategoryPlot plot = new CategoryPlot();

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> plot.getDomainAxisIndex(null));

    assertEquals("Null 'axis' argument.", exception.getMessage());
}
