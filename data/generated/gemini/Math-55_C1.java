@Test
@DisplayName("Cross product of standard basis vectors i and j should yield k")
void testCrossProductOfBasisVectors() {
    Vector3D v1 = new Vector3D(1.0, 0.0, 0.0); // i
    Vector3D v2 = new Vector3D(0.0, 1.0, 0.0); // j

    Vector3D result = Vector3D.crossProduct(v1, v2);

    assertEquals(0.0, result.x, 1e-9);
    assertEquals(0.0, result.y, 1e-9);
    assertEquals(1.0, result.z, 1e-9); // k
}

@Test
@DisplayName("Cross product of parallel vectors should yield the zero vector")
void testCrossProductParallelVectors() {
    Vector3D v1 = new Vector3D(1.0, 2.0, 3.0);
    Vector3D v2 = new Vector3D(2.0, 4.0, 6.0); // 2 * v1

    Vector3D result = Vector3D.crossProduct(v1, v2);

    assertEquals(0.0, result.x, 1e-9);
    assertEquals(0.0, result.y, 1e-9);
    assertEquals(0.0, result.z, 1e-9);
}

@Test
@DisplayName("Cross product is anticommutative: a x b = -(b x a)")
void testCrossProductAnticommutative() {
    Vector3D v1 = new Vector3D(3.0, -1.0, 4.0);
    Vector3D v2 = new Vector3D(1.0, 5.0, -2.0);

    Vector3D r1 = Vector3D.crossProduct(v1, v2);
    Vector3D r2 = Vector3D.crossProduct(v2, v1);

    assertEquals(r1.x, -r2.x, 1e-9);
    assertEquals(r1.y, -r2.y, 1e-9);
    assertEquals(r1.z, -r2.z, 1e-9);
}

@Test
@DisplayName("Cross product involving negative coordinates")
void testCrossProductNegativeCoordinates() {
    Vector3D v1 = new Vector3D(-1.0, -2.0, -3.0);
    Vector3D v2 = new Vector3D(4.0, -5.0, 6.0);

    // Expected components:
    // x = (-2 * 6) - (-3 * -5) = -12 - 15 = -27
    // y = (-3 * 4) - (-1 * 6)  = -12 - (-6) = -6
    // z = (-1 * -5) - (-2 * 4)  = 5 - (-8) = 13
    Vector3D result = Vector3D.crossProduct(v1, v2);

    assertEquals(-27.0, result.x, 1e-9);
    assertEquals(-6.0, result.y, 1e-9);
    assertEquals(13.0, result.z, 1e-9);
}

@Test
@DisplayName("Cross product with zero vector yields the zero vector")
void testCrossProductWithZeroVector() {
    Vector3D v1 = new Vector3D(5.0, 12.0, -3.0);
    Vector3D zero = new Vector3D(0.0, 0.0, 0.0);

    Vector3D result = Vector3D.crossProduct(v1, zero);

    assertEquals(0.0, result.x, 1e-9);
    assertEquals(0.0, result.y, 1e-9);
    assertEquals(0.0, result.z, 1e-9);
}
