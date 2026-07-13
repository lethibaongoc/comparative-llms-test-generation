@Test
public void testGetDomainAxisIndex_existingAxis() {
    CategoryPlot plot = new CategoryPlot();

    CategoryAxis axis0 = new CategoryAxis("Axis 0");
    CategoryAxis axis1 = new CategoryAxis("Axis 1");

    plot.setDomainAxis(0, axis0);
    plot.setDomainAxis(1, axis1);

    assertEquals(0, plot.getDomainAxisIndex(axis0));
    assertEquals(1, plot.getDomainAxisIndex(axis1));
}

@Test
public void testGetDomainAxisIndex_axisNotPresent() {
    CategoryPlot plot = new CategoryPlot();

    CategoryAxis axis = new CategoryAxis("Axis");
    CategoryAxis other = new CategoryAxis("Other");

    plot.setDomainAxis(axis);

    assertEquals(-1, plot.getDomainAxisIndex(other));
}

@Test
public void testGetDomainAxisIndex_nullAxis() {
    CategoryPlot plot = new CategoryPlot();

    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> plot.getDomainAxisIndex(null));

    assertEquals("Null 'axis' argument.", ex.getMessage());
}
