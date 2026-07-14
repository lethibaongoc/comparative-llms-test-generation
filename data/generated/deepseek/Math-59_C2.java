@Test
void testMaxFloat() {
    assertEquals(5.0f, FastMath.max(5.0f, 3.0f));
    assertEquals(5.0f, FastMath.max(3.0f, 5.0f));
    assertEquals(5.0f, FastMath.max(5.0f, 5.0f));
    assertEquals(3.0f, FastMath.max(3.0f, -2.0f));
    assertEquals(-2.0f, FastMath.max(-5.0f, -2.0f));
    assertEquals(0.0f, FastMath.max(-1.0f, 0.0f));
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(Float.POSITIVE_INFINITY, 1.0f));
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(1.0f, Float.POSITIVE_INFINITY));
    assertEquals(Float.NEGATIVE_INFINITY, FastMath.max(Float.NEGATIVE_INFINITY, -5.0f));
    assertEquals(1.0f, FastMath.max(1.0f, Float.NEGATIVE_INFINITY));
    assertEquals(Float.NaN, FastMath.max(Float.NaN, 5.0f));
    assertEquals(Float.NaN, FastMath.max(5.0f, Float.NaN));
    assertEquals(Float.NaN, FastMath.max(Float.NaN, Float.NaN));
    assertEquals(0.0f, FastMath.max(-0.0f, 0.0f));
    assertEquals(0.0f, FastMath.max(0.0f, -0.0f));
}
