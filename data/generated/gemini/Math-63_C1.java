@Test
void testEquals_ExactlyEqual() {
    assertTrue(MathUtils.equals(1.0, 1.0));
    assertTrue(MathUtils.equals(0.0, 0.0));
    assertTrue(MathUtils.equals(-123.456, -123.456));
}

@Test
void testEquals_DistinctValues() {
    assertFalse(MathUtils.equals(1.0, 2.0));
    assertFalse(MathUtils.equals(-1.0, 1.0));
    assertFalse(MathUtils.equals(0.0, 1e-9));
}

@Test
void testEquals_PositiveAndNegativeZero() {
    assertTrue(MathUtils.equals(-0.0, 0.0));
}

@Test
void testEquals_OneUlpDifference() {
    double x = 1.0;
    double y = Math.nextUp(x); // Exactly 1 ULP away

    assertTrue(MathUtils.equals(x, y), "Values 1 ULP apart should be considered equal");
}

@Test
void testEquals_MoreThanOneUlpDifference() {
    double x = 1.0;
    double y = Math.nextUp(Math.nextUp(x)); // 2 ULPs away

    assertFalse(MathUtils.equals(x, y), "Values more than 1 ULP apart should not be considered equal");
}

@Test
void testEquals_Infinities() {
    assertTrue(MathUtils.equals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertTrue(MathUtils.equals(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertFalse(MathUtils.equals(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
}

@Test
void testEquals_NaN() {
    assertFalse(MathUtils.equals(Double.NaN, Double.NaN));
    assertFalse(MathUtils.equals(1.0, Double.NaN));
    assertFalse(MathUtils.equals(Double.NaN, 1.0));
}
