@Test
@DisplayName("Should return the larger of two positive float values")
void testMaxWithPositiveFloats() {
    assertEquals(5.5f, FastMath.max(2.3f, 5.5f));
    assertEquals(5.5f, FastMath.max(5.5f, 2.3f));
}

@Test
@DisplayName("Should return the larger of two negative float values")
void testMaxWithNegativeFloats() {
    assertEquals(-2.3f, FastMath.max(-2.3f, -5.5f));
    assertEquals(-2.3f, FastMath.max(-5.5f, -2.3f));
}

@Test
@DisplayName("Should correctly compare positive and negative values")
void testMaxWithMixedSignFloats() {
    assertEquals(1.1f, FastMath.max(-1.1f, 1.1f));
    assertEquals(1.1f, FastMath.max(1.1f, -1.1f));
}

@Test
@DisplayName("Should return the same value when both arguments are equal")
void testMaxWithEqualFloats() {
    assertEquals(3.14f, FastMath.max(3.14f, 3.14f));
    assertEquals(0.0f, FastMath.max(0.0f, 0.0f));
}

@Test
@DisplayName("Should return Float.NaN if either argument is NaN")
void testMaxWithNaN() {
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, 5.0f)));
    assertTrue(Float.isNaN(FastMath.max(5.0f, Float.NaN)));
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, Float.NaN)));
}

@Test
@DisplayName("Should handle infinity values correctly")
void testMaxWithInfinity() {
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(Float.POSITIVE_INFINITY, 1000.0f));
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(1000.0f, Float.POSITIVE_INFINITY));
    assertEquals(-1000.0f, FastMath.max(Float.NEGATIVE_INFINITY, -1000.0f));
    assertEquals(-1000.0f, FastMath.max(-1000.0f, Float.NEGATIVE_INFINITY));
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY));
}

@Test
@DisplayName("Should handle negative and positive zero correctly")
void testMaxWithPositiveAndNegativeZero() {
    // According to standard IEEE 754 math, -0.0f <= 0.0f evaluates to true,
    // so the method returns b (0.0f).
    assertEquals(0.0f, FastMath.max(-0.0f, 0.0f));
    assertEquals(0.0f, FastMath.max(0.0f, -0.0f));
}
