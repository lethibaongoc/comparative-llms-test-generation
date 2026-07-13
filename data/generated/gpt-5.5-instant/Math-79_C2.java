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
public void testDistance_negativeCoordinates() {
    int[] p1 = {-1, -2};
    int[] p2 = {2, 2};

    assertEquals(5.0, MathUtils.distance(p1, p2), 1e-10);
}

@Test
public void testDistance_symmetric() {
    int[] p1 = {5, -1, 2};
    int[] p2 = {1, 3, -2};

    assertEquals(
        MathUtils.distance(p1, p2),
        MathUtils.distance(p2, p1),
        1e-10
    );
}
