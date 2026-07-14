@Test
void testGetPaintWithLowerBoundValue() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);
    Paint result = scale.getPaint(0.0);
    assertTrue(result instanceof Color);
    Color color = (Color) result;
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}

@Test
void testGetPaintWithUpperBoundValue() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);
    Paint result = scale.getPaint(100.0);
    Color color = (Color) result;
    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}

@Test
void testGetPaintWithMidRangeValue() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);
    Paint result = scale.getPaint(50.0);
    Color color = (Color) result;
    assertEquals(127, color.getRed());
    assertEquals(127, color.getGreen());
    assertEquals(127, color.getBlue());
}

@Test
void testGetPaintWithValueBelowLowerBound() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);
    Paint result = scale.getPaint(-50.0);
    Color color = (Color) result;
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}

@Test
void testGetPaintWithValueAboveUpperBound() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);
    Paint result = scale.getPaint(150.0);
    Color color = (Color) result;
    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}

@Test
void testGetPaintWithNegativeLowerBound() {
    GrayPaintScale scale = new GrayPaintScale(-100.0, 100.0);
    Paint result = scale.getPaint(0.0);
    Color color = (Color) result;
    assertEquals(127, color.getRed());
    assertEquals(127, color.getGreen());
    assertEquals(127, color.getBlue());
}

@Test
void testGetPaintWithZeroRange() {
    GrayPaintScale scale = new GrayPaintScale(50.0, 50.0);
    Paint result = scale.getPaint(75.0);
    Color color = (Color) result;
    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}

@Test
void testGetPaintWithNonZeroLowerBound() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);
    Paint result = scale.getPaint(15.0);
    Color color = (Color) result;
    assertEquals(127, color.getRed());
    assertEquals(127, color.getGreen());
    assertEquals(127, color.getBlue());
}

@Test
void testGetPaintWithValueEqualToLowerBoundAfterClamping() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);
    Paint result = scale.getPaint(5.0);
    Color color = (Color) result;
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}
