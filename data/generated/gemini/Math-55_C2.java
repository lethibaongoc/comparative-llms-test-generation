```java
@Test
void testCrossProduct_standardBasisVectors_i_j() {
    // i x j = k
    Vector3D v1 = new Vector3D(1, 0, 0);
    Vector3D v2 = new Vector3D(0, 1, 0);
    Vector3D expected = new Vector3D(0, 0, 1);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_standardBasisVectors_j_k() {
    // j x k = i
    Vector3D v1 = new Vector3D(0, 1, 0);
    Vector3D v2 = new Vector3D(0, 0, 1);
    Vector3D expected = new Vector3D(1, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_standardBasisVectors_k_i() {
    // k x i = j
    Vector3D v1 = new Vector3D(0, 0, 1);
    Vector3D v2 = new Vector3D(1, 0, 0);
    Vector3D expected = new Vector3D(0, 1, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_standardBasisVectors_j_i_anticommutativity() {
    // j x i = -k
    Vector3D v1 = new Vector3D(0, 1, 0);
    Vector3D v2 = new Vector3D(1, 0, 0);
    Vector3D expected = new Vector3D(0, 0, -1);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_zeroVector_as_v1() {
    // (0,0,0) x (any) = (0,0,0)
    Vector3D v1 = new Vector3D(0, 0, 0);
    Vector3D v2 = new Vector3D(1, 2, 3);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_zeroVector_as_v2() {
    // (any) x (0,0,0) = (0,0,0)
    Vector3D v1 = new Vector3D(1, 2, 3);
    Vector3D v2 = new Vector3D(0, 0, 0);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_bothZeroVectors() {
    // (0,0,0) x (0,0,0) = (0,0,0)
    Vector3D v1 = new Vector3D(0, 0, 0);
    Vector3D v2 = new Vector3D(0, 0, 0);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_parallelVectors_positiveMultiple() {
    // v x (k*v) = (0,0,0)
    Vector3D v1 = new Vector3D(1, 2, 3);
    Vector3D v2 = new Vector3D(2, 4, 6); // v2 = 2 * v1
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_parallelVectors_negativeMultiple() {
    // v x (-k*v) = (0,0,0)
    Vector3D v1 = new Vector3D(1, 2, 3);
    Vector3D v2 = new Vector3D(-1, -2, -3); // v2 = -1 * v1
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_selfCrossProduct() {
    // v x v = (0,0,0)
    Vector3D v1 = new Vector3D(5, -1, 7);
    Vector3D v2 = new Vector3D(5, -1, 7);
    Vector3D expected = new Vector3D(0, 0, 0);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_arbitraryVectors_positiveComponents() {
    Vector3D v1 = new Vector3D(1, 2, 3);
    Vector3D v2 = new Vector3D(4, 5, 6);
    // (2*6 - 3*5, 3*4 - 1*6, 1*5 - 2*4)
    // (12 - 15, 12 - 6, 5 - 8)
    // (-3, 6, -3)
    Vector3D expected = new Vector3D(-3, 6, -3);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_arbitraryVectors_mixedComponents() {
    Vector3D v1 = new Vector3D(1, -2, 3);
    Vector3D v2 = new Vector3D(-4, 5, -6);
    // x = (-2)*(-6) - 3*5 = 12 - 15 = -3
    // y = 3*(-4) - 1*(-6) = -12 - (-6) = -6
    // z = 1*5 - (-2)*(-4) = 5 - 8 = -3
    Vector3D expected = new Vector3D(-3, -6, -3);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_arbitraryVectors_negativeComponents() {
    Vector3D v1 = new Vector3D(-1, -2, -3);
    Vector3D v2 = new Vector3D(-4, -5, -6);
    // x = (-2)*(-6) - (-3)*(-5) = 12 - 15 = -3
    // y = (-3)*(-4) - (-1)*(-6) = 12 - 6 = 6
    // z = (-1)*(-5) - (-2)*(-4) = 5 - 8 = -3
    Vector3D expected = new Vector3D(-3, 6, -3);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}

@Test
void testCrossProduct_perpendicularVectors_resultOrthogonal() {
    // For v1=(1,0,0) and v2=(0,1,0), result is (0,0,1).
    // The test ensures the calculation is correct. An additional check
    // for orthogonality (dot product of result with v1 and v2 should be zero)
    // would require a dotProduct method or manual calculation inside the test.
    // For now, we only verify the direct computation.
    Vector3D v1 = new Vector3D(1, 0, 0);
    Vector3D v2 = new Vector3D(0, 1, 0);
    Vector3D expected = new Vector3D(0, 0, 1);
    assertEquals(expected, Vector3D.crossProduct(v1, v2));
}
```