@Test
void testEqualsSameObject() {
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
void testEqualsIdenticalRenderers() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
    assertTrue(renderer1.equals(renderer2));
}

@Test
void testEqualsDifferentPlotLines() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    renderer1.setPlotLines(true);
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
    renderer2.setPlotLines(false);
    assertFalse(renderer1.equals(renderer2));
}

@Test
void testEqualsDifferentGroupPaint() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    renderer1.setGroupPaint(Color.RED);
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
    renderer2.setGroupPaint(Color.BLUE);
    assertFalse(renderer1.equals(renderer2));
}

@Test
void testEqualsDifferentGroupStroke() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    renderer1.setGroupStroke(new BasicStroke(1.0f));
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
    renderer2.setGroupStroke(new BasicStroke(2.0f));
    assertFalse(renderer1.equals(renderer2));
}
