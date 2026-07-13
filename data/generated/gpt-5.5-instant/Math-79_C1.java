@Test
public void testDistance_samePoints() {
    int[] p1 = {1, 2, 3};
    int[] p2 = {1, 2, 3};

    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
public void testDistance_twoDimensional() {
    int[] p1 = {0, 0};
    int[] p2 = {3, 4};

    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
public void testDistance_threeDimensional() {
    int[] p1 = {1, 2, 3};
    int[] p2 = {4, 6, 3};

    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
public void testDistance_withNegativeCoordinates() {
    int[] p1 = {-1, -2};
    int[] p2 = {2, 2};

    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
public void testDistance_emptyArrays() {
    int[] p1 = {};
    int[] p2 = {};

    assertEquals(0.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
public void testDistance_isSymmetric() {
    int[] p1 = {2, -1, 5};
    int[] p2 = {-2, 3, 1};

    assertEquals(
        MathUtils.distance(p1, p2),
        MathUtils.distance(p2, p1),
        1e-10
    );
}
