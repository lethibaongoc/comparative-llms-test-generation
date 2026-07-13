@Test
void testCrossProduct_standardBasisVectors_i_j() {
    Vector3D v1 = new Vector3D(1, 0, 0);
    Vector3D v2 = new Vector3D(0, 1, 0);
    Vector3D expected = new Vector3D(0, 0, 1);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_standardBasisVectors_j_k() {
    Vector3D v1 = new Vector3D(0, 1, 0);
    Vector3D v2 = new Vector3D(0, 0, 1);
    Vector3D expected = new Vector3D(1, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_standardBasisVectors_k_i() {
    Vector3D v1 = new Vector3D(0, 0, 1);
    Vector3D v2 = new Vector3D(1, 0, 0);
    Vector3D expected = new Vector3D(0, 1, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_standardBasisVectors_j_i_anticommutativity() {
    Vector3D v1 = new Vector3D(0, 1, 0);
    Vector3D v2 = new Vector3D(1, 0, 0);
    Vector3D expected = new Vector3D(0, 0, -1);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_zeroVector_as_v1() {
    Vector3D v1 = new Vector3D(0, 0, 0);
    Vector3D v2 = new Vector3D(1, 2, 3);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_zeroVector_as_v2() {
    Vector3D v1 = new Vector3D(1, 2, 3);
    Vector3D v2 = new Vector3D(0, 0, 0);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_bothZeroVectors() {
    Vector3D v1 = new Vector3D(0, 0, 0);
    Vector3D v2 = new Vector3D(0, 0, 0);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_parallelVectors_positiveMultiple() {
    Vector3D v1 = new Vector3D(1, 2, 3);
    Vector3D v2 = new Vector3D(2, 4, 6);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_parallelVectors_negativeMultiple() {
    Vector3D v1 = new Vector3D(1, 2, 3);
    Vector3D v2 = new Vector3D(-1, -2, -3);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_selfCrossProduct() {
    Vector3D v1 = new Vector3D(5, -1, 7);
    Vector3D v2 = new Vector3D(5, -1, 7);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_arbitraryVectors_positiveComponents() {
    Vector3D v1 = new Vector3D(1, 2, 3);
    Vector3D v2 = new Vector3D(4, 5, 6);
    Vector3D expected = new Vector3D(-3, 6, -3);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_arbitraryVectors_mixedComponents() {
    Vector3D v1 = new Vector3D(1, -2, 3);
    Vector3D v2 = new Vector3D(-4, 5, -6);
    Vector3D expected = new Vector3D(-3, -6, -3);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_arbitraryVectors_negativeComponents() {
    Vector3D v1 = new Vector3D(-1, -2, -3);
    Vector3D v2 = new Vector3D(-4, -5, -6);
    Vector3D expected = new Vector3D(-3, 6, -3);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_perpendicularVectors_resultOrthogonal() {
    Vector3D v1 = new Vector3D(1, 0, 0);
    Vector3D v2 = new Vector3D(0, 1, 0);
    Vector3D expected = new Vector3D(0, 0, 1);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}
