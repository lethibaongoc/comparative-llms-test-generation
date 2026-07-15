@Test
void testDistanceWithPositiveCoordinates() {
    int[] p1 = {1, 2, 3};
    int[] p2 = {4, 6, 3};

    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
void testDistanceWithIdenticalPoints() {
    int[] p1 = {5, -2, 8};
    int[] p2 = {5, -2, 8};

    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
void testDistanceWithNegativeCoordinates() {
    int[] p1 = {-1, -2};
    int[] p2 = {2, 2};

    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
void testDistanceWithEmptyArrays() {
    int[] p1 = {};
    int[] p2 = {};

    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-10);
}
