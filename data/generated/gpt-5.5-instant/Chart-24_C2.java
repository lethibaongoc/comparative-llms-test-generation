@Test
public void testGetPaint_lowerBoundReturnsBlack() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);

    assertEquals(new Color(0, 0, 0), scale.getPaint(0.0));
}

@Test
public void testGetPaint_upperBoundReturnsWhite() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);

    assertEquals(new Color(255, 255, 255), scale.getPaint(100.0));
}

@Test
public void testGetPaint_midpointReturnsGray() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 100.0);

    assertEquals(new Color(127, 127, 127), scale.getPaint(50.0));
}

@Test
public void testGetPaint_valueBelowLowerBoundIsClamped() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);

    assertEquals(new Color(0, 0, 0), scale.getPaint(5.0));
}

@Test
public void testGetPaint_valueAboveUpperBoundIsClamped() {
    GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);

    assertEquals(new Color(255, 255, 255), scale.getPaint(25.0));
}

@Test
public void testGetPaint_intermediateValueUsesTruncation() {
    GrayPaintScale scale = new GrayPaintScale(0.0, 10.0);

    assertEquals(new Color(63, 63, 63), scale.getPaint(2.5));
}
