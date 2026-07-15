@Test
void testMaxWithDifferentValues() {
    assertEquals(5.0f, FastMath.max(3.0f, 5.0f));
    assertEquals(5.0f, FastMath.max(5.0f, 3.0f));
}

@Test
void testMaxWithEqualValues() {
    assertEquals(4.0f, FastMath.max(4.0f, 4.0f));
}

@Test
void testMaxWithNaNArguments() {
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, 1.0f)));
    assertTrue(Float.isNaN(FastMath.max(1.0f, Float.NaN)));
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, Float.NaN)));
}

@Test
void testMaxWithNegativeValues() {
    assertEquals(-2.0f, FastMath.max(-5.0f, -2.0f));
}
