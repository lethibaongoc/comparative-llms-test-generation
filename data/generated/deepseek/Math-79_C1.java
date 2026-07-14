@Test
@DisplayName("Should return 0 for identical points")
void testDistanceIdenticalPoints() {
    int[] p1 = {0, 0};
    int[] p2 = {0, 0};
    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should calculate distance in 1D correctly")
void testDistance1D() {
    int[] p1 = {3};
    int[] p2 = {0};
    assertEquals(3.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should calculate distance in 2D correctly")
void testDistance2D() {
    int[] p1 = {3, 4};
    int[] p2 = {0, 0};
    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should calculate distance in 3D correctly")
void testDistance3D() {
    int[] p1 = {1, 2, 3};
    int[] p2 = {4, 6, 8};
    assertEquals(7.0710678118654755, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should handle negative coordinates")
void testDistanceNegativeCoordinates() {
    int[] p1 = {-1, -2};
    int[] p2 = {-4, -6};
    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should handle mixed positive and negative coordinates")
void testDistanceMixedCoordinates() {
    int[] p1 = {-1, 3};
    int[] p2 = {2, -1};
    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should calculate distance with large values")
void testDistanceLargeValues() {
    int[] p1 = {1000000, 2000000};
    int[] p2 = {0, 0};
    double expected = Math.sqrt(1e12 + 4e12);
    assertEquals(expected, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should return 0 for zero-length array")
void testDistanceZeroLengthArray() {
    int[] p1 = {};
    int[] p2 = {};
    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-9);
}
