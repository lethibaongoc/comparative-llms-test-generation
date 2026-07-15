@Test
void testGetDomainAxisIndex() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis1 = new CategoryAxis("Axis 1");
    CategoryAxis axis2 = new CategoryAxis("Axis 2");

    plot.setDomainAxis(0, axis1);
    plot.setDomainAxis(1, axis2);

    assertEquals(0, plot.getDomainAxisIndex(axis1));
    assertEquals(1, plot.getDomainAxisIndex(axis2));
}

@Test
@SuppressWarnings("all")
void testGetDomainAxisIndexWithNull() {
    CategoryPlot plot = new CategoryPlot();
    assertThrows(IllegalArgumentException.class, () -> plot.getDomainAxisIndex(null));
}

@Test
void testGetDomainAxisIndexNotFound() {
    CategoryPlot plot = new CategoryPlot();
    CategoryAxis axis1 = new CategoryAxis("Axis 1");
    CategoryAxis unassignedAxis = new CategoryAxis("Unassigned");

    plot.setDomainAxis(0, axis1);

    assertEquals(-1, plot.getDomainAxisIndex(unassignedAxis));
}
