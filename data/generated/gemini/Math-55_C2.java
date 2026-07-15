@Test
void testCrossProduct() {
    Vector3D v1 = new Vector3D(1.0, 2.0, 3.0);
    Vector3D v2 = new Vector3D(4.0, 5.0, 6.0);

    Vector3D result = Vector3D.crossProduct(v1, v2);

    // First argument calculation: v1.y * v2.z - v1.z * v2.y = 2.0 * 6.0 - 3.0 * 5.0 = -3.0
    // Second argument calculation: v1.z * v1.y * v2.x = 3.0 * 2.0 * 4.0 = 24.0
    assertEquals(-3.0, result.x, 1e-9);
    assertEquals(24.0, result.y, 1e-9);
}

@Test
void testCrossProductWithZeros() {
    Vector3D v1 = new Vector3D(0.0, 0.0, 0.0);
    Vector3D v2 = new Vector3D(1.0, 2.0, 3.0);

    Vector3D result = Vector3D.crossProduct(v1, v2);

    assertEquals(0.0, result.x, 1e-9);
    assertEquals(0.0, result.y, 1e-9);
}
