@Test
void testGetPaintWithLowerBoundValue() {
    // Assuming lowerBound = 0.0, upperBound = 100.0
    GrayPaintScale paintScale = new GrayPaintScale(0.0, 100.0);
    Color expectedColor = new Color(0, 0, 0);
    assertEquals(expectedColor, paintScale.getPaint(0.0));
}

@Test
void testGetPaintWithUpperBoundValue() {
    GrayPaintScale paintScale = new GrayPaintScale(0.0, 100.0);
    Color expectedColor = new Color(255, 255, 255);
    assertEquals(expectedColor, paintScale.getPaint(100.0));
}

@Test
void testGetPaintWithValueBelowLowerBound() {
    GrayPaintScale paintScale = new GrayPaintScale(0.0, 100.0);
    Color expectedColor = new Color(0, 0, 0);
    assertEquals(expectedColor, paintScale.getPaint(-50.0));
}

@Test
void testGetPaintWithValueAboveUpperBound() {
    GrayPaintScale paintScale = new GrayPaintScale(0.0, 100.0);
    Color expectedColor = new Color(255, 255, 255);
    assertEquals(expectedColor, paintScale.getPaint(150.0));
}

@Test
void testGetPaintWithMidpointValue() {
    GrayPaintScale paintScale = new GrayPaintScale(0.0, 100.0);
    // (50.0 - 0.0) / (100.0 - 0.0) * 255.0 = 127.5 -> cast to int is 127
    Color expectedColor = new Color(127, 127, 127);
    assertEquals(expectedColor, paintScale.getPaint(50.0));
}
