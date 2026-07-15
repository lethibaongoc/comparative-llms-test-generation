@Test
void testMaxStandardValues() {
    assertEquals(5.0f, FastMath.max(2.0f, 5.0f));
    assertEquals(5.0f, FastMath.max(5.0f, 2.0f));
    assertEquals(-2.0f, FastMath.max(-5.0f, -2.0f));
}

@Test
void testMaxInfinities() {
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(Float.POSITIVE_INFINITY, 1.0f));
    assertEquals(1.0f, FastMath.max(Float.NEGATIVE_INFINITY, 1.0f));
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY));
}

@Test
void testMaxNaN() {
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, 1.0f)));
    assertTrue(Float.isNaN(FastMath.max(1.0f, Float.NaN)));
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, Float.NaN)));
}

@Test
void testMaxSignedZeros() {
    // Max of -0.0f and 0.0f should be 0.0f
    assertEquals(0.0f, FastMath.max(-0.0f, 0.0f));
    assertEquals(0.0f, FastMath.max(0.0f, -0.0f));

    // Ensure it correctly distinguishes positive and negative zero representation
    assertEquals(Float.floatToIntBits(0.0f), Float.floatToIntBits(FastMath.max(-0.0f, 0.0f)));
    assertEquals(Float.floatToIntBits(0.0f), Float.floatToIntBits(FastMath.max(0.0f, -0.0f)));
}
