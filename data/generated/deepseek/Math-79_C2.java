@Test
void testDistance() {
    assertEquals(0.0, MathUtils.distance(new int[]{0, 0}, new int[]{0, 0}));
    assertEquals(1.0, MathUtils.distance(new int[]{0, 0}, new int[]{1, 0}));
    assertEquals(5.0, MathUtils.distance(new int[]{0, 0}, new int[]{3, 4}));
    assertEquals(2.0, MathUtils.distance(new int[]{1, 1}, new int[]{3, 1}));
    assertEquals(5.0, MathUtils.distance(new int[]{-1, -2}, new int[]{3, 1}));
}

@Test
void testDistanceWithDifferentLengths() {
    assertEquals(2.0, MathUtils.distance(new int[]{1, 2, 3}, new int[]{1, 0, 3}));
}

@Test
void testDistanceWithLargeValues() {
    double expected = Math.sqrt(2000000);
    assertEquals(expected, MathUtils.distance(new int[]{1000, 1000}, new int[]{0, 0}));
}

@Test
void testDistanceWithNegativeValues() {
    assertEquals(3.0, MathUtils.distance(new int[]{-1, -1}, new int[]{-1, 2}));
}
