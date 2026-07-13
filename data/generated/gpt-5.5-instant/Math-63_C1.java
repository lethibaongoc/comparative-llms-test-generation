@Test
public void testEquals_sameValue() {
    assertTrue(MathUtils.equals(1.2345, 1.2345));
}

@Test
public void testEquals_adjacentDoubleValues() {
    double x = 1.0;
    double y = Math.nextUp(x);
    assertTrue(MathUtils.equals(x, y));
}

@Test
public void testEquals_valuesMoreThanOneUlpApart() {
    double x = 1.0;
    double y = Math.nextUp(Math.nextUp(x));
    assertFalse(MathUtils.equals(x, y));
}

@Test
public void testEquals_nanValues() {
    assertFalse(MathUtils.equals(Double.NaN, Double.NaN));
}

@Test
public void testEquals_positiveAndNegativeInfinity() {
    assertTrue(MathUtils.equals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertFalse(MathUtils.equals(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
}
