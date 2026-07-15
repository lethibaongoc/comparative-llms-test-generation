@Test
@DisplayName("Should return 0.0 when calculating distance between the same point")
void testDistanceToItself() {
    int[] p1 = {1, 2, 3};
    int[] p2 = {1, 2, 3};
    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should correctly calculate 2D Euclidean distance")
void testDistance2D() {
    int[] p1 = {0, 0};
    int[] p2 = {3, 4};
    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should correctly calculate 3D Euclidean distance")
void testDistance3D() {
    int[] p1 = {1, 2, 3};
    int[] p2 = {4, 6, 15};
    // Expected: sqrt((4-1)^2 + (6-2)^2 + (15-3)^2) = sqrt(9 + 16 + 144) = sqrt(169) = 13.0
    assertEquals(13.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should handle negative coordinates correctly")
void testDistanceWithNegativeCoordinates() {
    int[] p1 = {-1, -2};
    int[] p2 = {2, 2};
    // Expected: sqrt((2 - (-1))^2 + (2 - (-2))^2) = sqrt(9 + 16) = 5.0
    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should return 0.0 for empty arrays")
void testDistanceEmptyArrays() {
    int[] p1 = {};
    int[] p2 = {};
    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
@DisplayName("Should throw NullPointerException if either array is null")
void testDistanceNullArrays() {
    int[] p = {1, 2};
    assertThrows(NullPointerException.class, () -> MathUtils.distance(null, p));
    assertThrows(NullPointerException.class, () -> MathUtils.distance(p, null));
}

@Test
@DisplayName("Should throw ArrayIndexOutOfBoundsException if second array is shorter than first")
void testDistanceMismatchedLengths() {
    int[] p1 = {1, 2, 3};
    int[] p2 = {1, 2};
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtils.distance(p1, p2));
}
