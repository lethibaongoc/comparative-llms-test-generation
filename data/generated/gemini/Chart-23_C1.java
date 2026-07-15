@Test
void testEquals_SameInstance() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    assertTrue(r1.equals(r1));
}

@Test
void testEquals_Null() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    assertFalse(r1.equals(null));
}

@Test
void testEquals_DifferentClass() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    assertFalse(r1.equals("Not A Renderer"));
}

@Test
void testEquals_IdenticalObjects() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();
    assertTrue(r1.equals(r2));
}

@Test
void testEquals_DifferentPlotLines() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    r1.setPlotLines(true);
    r2.setPlotLines(false);

    assertFalse(r1.equals(r2));
    assertFalse(r2.equals(r1));
}

@Test
void testEquals_DifferentGroupPaint() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    r1.setGroupPaint(java.awt.Color.RED);
    r2.setGroupPaint(java.awt.Color.BLUE);

    assertFalse(r1.equals(r2));
    assertFalse(r2.equals(r1));
}

@Test
void testEquals_DifferentGroupStroke() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    r1.setGroupStroke(new java.awt.BasicStroke(1.0f));
    r2.setGroupStroke(new java.awt.BasicStroke(2.0f));

    assertFalse(r1.equals(r2));
    assertFalse(r2.equals(r1));
}

@Test
void testEquals_DifferentSuperEquals() {
    // Assuming the superclass (e.g., AbstractCategoryItemRenderer) contains fields
    // evaluated by super.equals(obj), changing one of those fields should cause equals to return false.
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    // Example: Changing a common property inherited from the superclass, like toolTipGenerator
    r1.setDefaultToolTipGenerator(new org.jfree.chart.labels.StandardCategoryToolTipGenerator());
    r2.setDefaultToolTipGenerator(null);

    assertFalse(r1.equals(r2));
}
