@Test
void testEqualsSameInstanceAndDifferentType() {
    MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();

    assertTrue(renderer.equals(renderer));
    assertFalse(renderer.equals(null));
    assertFalse(renderer.equals("renderer"));
}

@Test
void testEqualsWithEquivalentRenderer() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    assertTrue(renderer1.equals(renderer2));
    assertTrue(renderer2.equals(renderer1));
}

@Test
void testEqualsDetectsDifferentRendererProperties() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    renderer2.setDrawLines(!renderer1.isDrawLines());
    assertFalse(renderer1.equals(renderer2));

    renderer2.setDrawLines(renderer1.isDrawLines());
    renderer2.setGroupPaint(java.awt.Color.RED);
    assertFalse(renderer1.equals(renderer2));

    renderer1.setGroupPaint(java.awt.Color.RED);
    renderer2.setGroupStroke(new java.awt.BasicStroke(2.0f));
    assertFalse(renderer1.equals(renderer2));
}

@Test
void testEqualsDelegatesToSuperclassProperties() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    renderer2.setSeriesVisible(0, Boolean.FALSE);

    assertFalse(renderer1.equals(renderer2));
}
