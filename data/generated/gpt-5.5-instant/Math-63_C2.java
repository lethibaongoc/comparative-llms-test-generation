@Test
public void testEquals_sameValue() {
    assertTrue(MathUtils.equals(1.2345, 1.2345));
}

@Test
public void testEquals_differentValues() {
    assertFalse(MathUtils.equals(1.0, 2.0));
}

@Test
public void testEquals_adjacentFloatingPointValues() {
    double x = 1.0;
    double y = Math.nextUp(x);
    assertTrue(MathUtils.equals(x, y));
}

@Test
public void testEquals_nanValues() {
    assertTrue(MathUtils.equals(Double.NaN, Double.NaN));
}
