@Test
void shouldReturnBlackForLowerBound() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 1.0);
    Paint result = scale.getPaint(0.0);
    Color color = (Color) result;
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}

@Test
void shouldReturnWhiteForUpperBound() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 1.0);
    Paint result = scale.getPaint(1.0);
    Color color = (Color) result;
    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}

@Test
void shouldReturnMidGrayForMidpoint() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 1.0);
    Paint result = scale.getPaint(0.5);
    Color color = (Color) result;
    assertEquals(127, color.getRed());
    assertEquals(127, color.getGreen());
    assertEquals(127, color.getBlue());
}

@Test
void shouldClampValueBelowLowerBound() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);
    Paint result = scale.getPaint(5.0);
    Color color = (Color) result;
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}

@Test
void shouldClampValueAboveUpperBound() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);
    Paint result = scale.getPaint(25.0);
    Color color = (Color) result;
    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}

@Test
void shouldReturnCorrectGrayForValueWithinBounds() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);
    Paint result = scale.getPaint(25.0);
    Color color = (Color) result;
    assertEquals(63, color.getRed());
    assertEquals(63, color.getGreen());
    assertEquals(63, color.getBlue());
}

@Test
void shouldWorkWithNegativeBounds() {
    GrayPaintScale scale = new GrayPaintScale(-10.0, 10.0);
    Paint result = scale.getPaint(0.0);
    Color color = (Color) result;
    assertEquals(127, color.getRed());
    assertEquals(127, color.getGreen());
    assertEquals(127, color.getBlue());
}

@Test
void shouldReturnGrayWithAllComponentsEqual() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 255.0);
    Paint result = scale.getPaint(100.0);
    Color color = (Color) result;
    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();
    assertEquals(red, green);
    assertEquals(green, blue);
}
