@Test
public void testGetPaint_valueWithinBounds() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);

    Paint paint = scale.getPaint(50.0);

    assertTrue(paint instanceof Color);
    Color color = (Color) paint;
    assertEquals(127, color.getRed());
    assertEquals(127, color.getGreen());
    assertEquals(127, color.getBlue());
}

@Test
public void testGetPaint_valueBelowLowerBound() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);

    Color color = (Color) scale.getPaint(-10.0);

    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}

@Test
public void testGetPaint_valueAboveUpperBound() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);

    Color color = (Color) scale.getPaint(150.0);

    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}

@Test
public void testGetPaint_atLowerBound() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);

    Color color = (Color) scale.getPaint(10.0);

    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}

@Test
public void testGetPaint_atUpperBound() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);

    Color color = (Color) scale.getPaint(20.0);

    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}
