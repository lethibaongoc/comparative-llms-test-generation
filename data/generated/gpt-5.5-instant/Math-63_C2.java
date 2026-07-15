@Test
void testEquals() {
    assertTrue(MathUtils.equals(1.0, 1.0));
    assertTrue(MathUtils.equals(Double.NaN, Double.NaN));
    assertTrue(MathUtils.equals(0.0, -0.0));
    assertFalse(MathUtils.equals(1.0, 2.0));
}
