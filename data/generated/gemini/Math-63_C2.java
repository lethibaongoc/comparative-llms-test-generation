@Test
void testEqualsDouble() {
    // Standard equal values
    assertTrue(MathUtils.equals(123.456, 123.456));
    assertTrue(MathUtils.equals(-0.0, 0.0));

    // Clearly distinct values
    assertFalse(MathUtils.equals(1.0, 2.0));
    assertFalse(MathUtils.equals(1.0, 1.0000001));

    // Special cases (In standard IEEE 754 floating point, NaN comparisons are usually false unless handled explicitly)
    assertFalse(MathUtils.equals(Double.NaN, Double.NaN));
    assertFalse(MathUtils.equals(Double.NaN, 1.0));
    assertTrue(MathUtils.equals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertFalse(MathUtils.equals(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));

    // Adjacent values (ULPs of 1)
    double x = 1.0;
    double y = Math.nextUp(x);
    assertTrue(MathUtils.equals(x, y));

    // Values separated by more than 1 ULP
    double z = Math.nextUp(y);
    assertFalse(MathUtils.equals(x, z));
}
