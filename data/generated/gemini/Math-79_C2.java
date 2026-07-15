@Test
void testDistanceStandardDimensions() {
    int[] p1 = {0, 0};
    int[] p2 = {3, 4};
    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
void testDistanceThreeDimensions() {
    int[] p1 = {1, 2, 3};
    int[] p2 = {4, 6, 15};
    // sqrt((3)^2 + (4)^2 + (12)^2) = sqrt(9 + 16 + 144) = sqrt(169) = 13.0
    assertEquals(13.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
void testDistanceSamePoint() {
    int[] p1 = {5, -5, 10};
    int[] p2 = {5, -5, 10};
    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
void testDistanceOneDimension() {
    int[] p1 = {10};
    int[] p2 = {3};
    assertEquals(7.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
void testDistanceNegativeCoordinates() {
    int[] p1 = {-1, -1};
    int[] p2 = {-4, -5};
    // sqrt((-3)^2 + (-4)^2) = 5.0
    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
void testDistanceEmptyArrays() {
    int[] p1 = {};
    int[] p2 = {};
    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-9);
}

@Test
void testDistanceThrowsExceptionForDifferentLengths() {
    int[] p1 = {1, 2};
    int[] p2 = {1, 2, 3};
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtils.distance(p1, p2));
}

@Test
void testDistanceThrowsExceptionForNullFirstParameter() {
    int[] p2 = {1, 2};
    assertThrows(NullPointerException.class, () -> MathUtils.distance(null, p2));
}

@Test
void testDistanceThrowsExceptionForNullSecondParameter() {
    int[] p1 = {1, 2};
    assertThrows(NullPointerException.class, () -> MathUtils.distance(p1, null));
}
