@Test
void testGetPaintLowerBound() {
    GrayPaintScale gps = new GrayPaintScale(0.0, 10.0);
    Color color = (Color) gps.getPaint(0.0);
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}

@Test
void testGetPaintUpperBound() {
    GrayPaintScale gps = new GrayPaintScale(0.0, 10.0);
    Color color = (Color) gps.getPaint(10.0);
    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}

@Test
void testGetPaintMidPoint() {
    GrayPaintScale gps = new GrayPaintScale(0.0, 10.0);
    Color color = (Color) gps.getPaint(5.0);
    assertEquals(127, color.getRed());
    assertEquals(127, color.getGreen());
    assertEquals(127, color.getBlue());
}

@Test
void testGetPaintBelowLowerBound() {
    GrayPaintScale gps = new GrayPaintScale(0.0, 10.0);
    Color color = (Color) gps.getPaint(-5.0);
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());
}

@Test
void testGetPaintAboveUpperBound() {
    GrayPaintScale gps = new GrayPaintScale(0.0, 10.0);
    Color color = (Color) gps.getPaint(15.0);
    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());
}
