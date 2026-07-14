@Test
void testEqualsSameInstance() {
    MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
    assertTrue(renderer.equals(renderer));
}

@Test
void testEqualsNull() {
    MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
    assertFalse(renderer.equals(null));
}

@Test
void testEqualsDifferentClass() {
    MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
    assertFalse(renderer.equals("string"));
}

@Test
void testEqualsIdenticalObjects() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
    renderer1.setPlotLines(true);
    renderer2.setPlotLines(true);
    assertTrue(renderer1.equals(renderer2));
}

@Test
void testEqualsDifferentPlotLines() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
    renderer1.setPlotLines(true);
    renderer2.setPlotLines(false);
    assertFalse(renderer1.equals(renderer2));
}

@Test
void testEqualsDifferentGroupPaint() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
    renderer1.setGroupPaint(Color.RED);
    renderer2.setGroupPaint(Color.BLUE);
    assertFalse(renderer1.equals(renderer2));
}

@Test
void testEqualsDifferentGroupStroke() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
    renderer1.setGroupStroke(new BasicStroke(1.0f));
    renderer2.setGroupStroke(new BasicStroke(2.0f));
    assertFalse(renderer1.equals(renderer2));
}
