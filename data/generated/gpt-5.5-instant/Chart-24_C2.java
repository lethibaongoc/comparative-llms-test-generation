@Test
void testGetPaintWithinBounds() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);

    assertEquals(new Color(0, 0, 0), scale.getPaint(0.0));
    assertEquals(new Color(127, 127, 127), scale.getPaint(50.0));
    assertEquals(new Color(255, 255, 255), scale.getPaint(100.0));
}

@Test
void testGetPaintClampsValuesOutsideBounds() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);

    assertEquals(new Color(0, 0, 0), scale.getPaint(-10.0));
    assertEquals(new Color(255, 255, 255), scale.getPaint(110.0));
}

@Test
void testGetPaintWithNonZeroLowerBound() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);

    assertEquals(new Color(0, 0, 0), scale.getPaint(10.0));
    assertEquals(new Color(127, 127, 127), scale.getPaint(15.0));
    assertEquals(new Color(255, 255, 255), scale.getPaint(20.0));
}
