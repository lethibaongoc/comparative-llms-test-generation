@Test
void maxShouldReturnBWhenALessThanOrEqualToB() {
    assertEquals(5.0f, FastMath.max(3.0f, 5.0f));
    assertEquals(5.0f, FastMath.max(5.0f, 5.0f));
    assertEquals(-3.0f, FastMath.max(-5.0f, -3.0f));
    assertEquals(0.0f, FastMath.max(-1.0f, 0.0f));
}

@Test
void maxShouldReturnAWhenAGreaterThanB() {
    assertEquals(7.0f, FastMath.max(7.0f, 3.0f));
    assertEquals(-2.0f, FastMath.max(-2.0f, -5.0f));
    assertEquals(0.0f, FastMath.max(0.0f, -1.0f));
}

@Test
void maxShouldReturnNaNWhenEitherArgumentIsNaN() {
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, 5.0f)));
    assertTrue(Float.isNaN(FastMath.max(5.0f, Float.NaN)));
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, Float.NaN)));
}

@Test
void maxShouldHandlePositiveAndNegativeZero() {
    assertEquals(0.0f, FastMath.max(0.0f, -0.0f));
    assertEquals(0.0f, FastMath.max(-0.0f, 0.0f));
    assertEquals(-0.0f, FastMath.max(-0.0f, -0.0f));
}

@Test
void maxShouldHandleInfinities() {
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(Float.POSITIVE_INFINITY, 5.0f));
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(5.0f, Float.POSITIVE_INFINITY));
    assertEquals(5.0f, FastMath.max(Float.NEGATIVE_INFINITY, 5.0f));
    assertEquals(5.0f, FastMath.max(5.0f, Float.NEGATIVE_INFINITY));
}

@Test
void maxShouldHandleMinAndMaxFloatValues() {
    assertEquals(Float.MAX_VALUE, FastMath.max(Float.MAX_VALUE, 1.0f));
    assertEquals(1.0f, FastMath.max(Float.MIN_VALUE, 1.0f));
    assertEquals(Float.MIN_VALUE, FastMath.max(Float.MIN_VALUE, -1.0f));
}
