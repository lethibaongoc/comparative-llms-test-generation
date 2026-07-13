@Test
public void testEquals_sameInstance() {
    MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();

    assertTrue(renderer.equals(renderer));
}

@Test
public void testEquals_nullObject() {
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
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    assertTrue(r1.equals(r2));
    assertTrue(r2.equals(r1));
}

@Test
public void testEquals_differentPlotLines() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    r1.setPlotLines(true);
    r2.setPlotLines(false);

    assertFalse(r1.equals(r2));
    assertFalse(r2.equals(r1));
}

@Test
public void testEquals_differentGroupPaint() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    r1.setGroupPaint(Color.RED);
    r2.setGroupPaint(Color.BLUE);

    assertFalse(r1.equals(r2));
    assertFalse(r2.equals(r1));
}

@Test
public void testEquals_differentGroupStroke() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    r1.setGroupStroke(new BasicStroke(1.0f));
    r2.setGroupStroke(new BasicStroke(2.0f));

    assertFalse(r1.equals(r2));
    assertFalse(r2.equals(r1));
}

@Test
public void testEquals_sameModifiedProperties() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    r1.setPlotLines(false);
    r2.setPlotLines(false);
    r1.setGroupPaint(Color.GREEN);
    r2.setGroupPaint(Color.GREEN);
    BasicStroke stroke = new BasicStroke(3.0f);
    r1.setGroupStroke(stroke);
    r2.setGroupStroke(stroke);

    assertTrue(r1.equals(r2));
    assertTrue(r2.equals(r1));
}
