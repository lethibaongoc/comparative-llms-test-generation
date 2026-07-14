@Test
void testEqualsWithSameValues() {
    assertTrue(MathUtils.equals(5.0, 5.0));
}

@Test
void testEqualsWithDifferentValues() {
    assertFalse(MathUtils.equals(5.0, 10.0));
}

@Test
void testEqualsWithPositiveAndNegativeZero() {
    assertTrue(MathUtils.equals(0.0, -0.0));
}

@Test
void testEqualsWithPositiveInfinity() {
    assertTrue(MathUtils.equals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
}

@Test
void testEqualsWithNegativeInfinity() {
    assertTrue(MathUtils.equals(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
}

@Test
void testEqualsWithPositiveAndNegativeInfinity() {
    assertFalse(MathUtils.equals(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
}

@Test
void testEqualsWithNaN() {
    assertFalse(MathUtils.equals(Double.NaN, Double.NaN));
}

@Test
void testEqualsWithNaNAndValue() {
    assertFalse(MathUtils.equals(Double.NaN, 5.0));
}

@Test
void testEqualsWithValueAndNaN() {
    assertFalse(MathUtils.equals(5.0, Double.NaN));
}

@Test
void testEqualsWithVerySmallDifference() {
    assertTrue(MathUtils.equals(1.0000000000000002, 1.0));
}

@Test
void testEqualsWithLargeDifference() {
    assertFalse(MathUtils.equals(1.0, 2.0));
}

@Test
void testEqualsWithMaxValue() {
    assertTrue(MathUtils.equals(Double.MAX_VALUE, Double.MAX_VALUE));
}

@Test
void testEqualsWithMinValue() {
    assertTrue(MathUtils.equals(Double.MIN_VALUE, Double.MIN_VALUE));
}

@Test
void testEqualsWithMaxAndMinValue() {
    assertFalse(MathUtils.equals(Double.MAX_VALUE, Double.MIN_VALUE));
}
