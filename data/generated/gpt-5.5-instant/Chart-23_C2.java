@Test
public void testEquals_sameInstance() {
    MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
    assertTrue(renderer.equals(renderer));
}

@Test
public void testEquals_null() {
    MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
    assertFalse(renderer.equals(null));
}

@Test
public void testEquals_differentType() {
    MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
    assertFalse(renderer.equals("not a renderer"));
}

@Test
public void testEquals_identicalRenderers() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    assertTrue(renderer1.equals(renderer2));
    assertTrue(renderer2.equals(renderer1));
}

@Test
public void testEquals_differentPlotLines() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    renderer2.setPlotLines(!renderer1.isPlotLines());

    assertFalse(renderer1.equals(renderer2));
}

@Test
public void testEquals_differentGroupPaint() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    renderer1.setGroupPaint(Color.RED);
    renderer2.setGroupPaint(Color.BLUE);

    assertFalse(renderer1.equals(renderer2));
}

@Test
public void testEquals_equalGroupPaintInstances() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    renderer1.setGroupPaint(new Color(10, 20, 30));
    renderer2.setGroupPaint(new Color(10, 20, 30));

    assertTrue(renderer1.equals(renderer2));
}

@Test
public void testEquals_differentGroupStroke() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    renderer1.setGroupStroke(new BasicStroke(1.0f));
    renderer2.setGroupStroke(new BasicStroke(2.0f));

    assertFalse(renderer1.equals(renderer2));
}

@Test
public void testEquals_inheritedPropertyDifferent() {
    MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();

    renderer1.setBaseSeriesVisible(true);
    renderer2.setBaseSeriesVisible(false);

    assertFalse(renderer1.equals(renderer2));
}
