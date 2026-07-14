@Test
void testEqualsWithSameValues() {
    assertTrue(MathUtils.equals(1.0, 1.0));
    assertTrue(MathUtils.equals(-5.5, -5.5));
    assertTrue(MathUtils.equals(0.0, 0.0));
    assertTrue(MathUtils.equals(Double.MAX_VALUE, Double.MAX_VALUE));
}

@Test
void testEqualsWithDifferentValues() {
    assertFalse(MathUtils.equals(1.0, 2.0));
    assertFalse(MathUtils.equals(-5.5, -5.6));
    assertFalse(MathUtils.equals(0.0, 0.0001));
}

@Test
void testEqualsWithZeroAndNegativeZero() {
    assertTrue(MathUtils.equals(0.0, -0.0));
    assertTrue(MathUtils.equals(-0.0, 0.0));
}

@Test
void testEqualsWithNaNValues() {
    assertFalse(MathUtils.equals(Double.NaN, Double.NaN));
    assertFalse(MathUtils.equals(Double.NaN, 1.0));
    assertFalse(MathUtils.equals(1.0, Double.NaN));
}

@Test
void testEqualsWithInfiniteValues() {
    assertTrue(MathUtils.equals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertTrue(MathUtils.equals(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertFalse(MathUtils.equals(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertFalse(MathUtils.equals(Double.POSITIVE_INFINITY, 1.0));
}

@Test
void testEqualsWithValuesWithinDelta() {
    assertTrue(MathUtils.equals(1.0000000001, 1.0000000002));
    assertTrue(MathUtils.equals(0.123456789, 0.123456788));
}

@Test
void testEqualsWithValuesOutsideDelta() {
    assertFalse(MathUtils.equals(1.1, 1.2));
    assertFalse(MathUtils.equals(0.1, 0.2));
}

@Test
void testEqualsWithLargeValues() {
    assertTrue(MathUtils.equals(1e15, 1e15 + 0.5));
    assertFalse(MathUtils.equals(1e15, 1e15 + 2.0));
}
