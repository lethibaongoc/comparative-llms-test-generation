@Test
public void testCrossProduct_orthogonalVectors() {
    Vector3D v1 = new Vector3D(1.0, 0.0, 0.0);
    Vector3D v2 = new Vector3D(0.0, 1.0, 0.0);

    Vector3D result = Vector3D.crossProduct(v1, v2);

    assertEquals(0.0, result.getX(), 1e-10);
    assertEquals(0.0, result.getY(), 1e-10);
    assertEquals(1.0, result.getZ(), 1e-10);
}

@Test
public void testCrossProduct_parallelVectors() {
    Vector3D v1 = new Vector3D(1.0, 2.0, 3.0);
    Vector3D v2 = new Vector3D(2.0, 4.0, 6.0);

    Vector3D result = Vector3D.crossProduct(v1, v2);

    assertEquals(0.0, result.getX(), 1e-10);
    assertEquals(0.0, result.getY(), 1e-10);
    assertEquals(0.0, result.getZ(), 1e-10);
}

@Test
public void testCrossProduct_generalVectors() {
    Vector3D v1 = new Vector3D(1.0, 2.0, 3.0);
    Vector3D v2 = new Vector3D(4.0, 5.0, 6.0);

    Vector3D result = Vector3D.crossProduct(v1, v2);

    assertEquals(-3.0, result.getX(), 1e-10);
    assertEquals(6.0, result.getY(), 1e-10);
    assertEquals(-3.0, result.getZ(), 1e-10);
}

@Test
public void testCrossProduct_withZeroVector() {
    Vector3D v1 = new Vector3D(0.0, 0.0, 0.0);
    Vector3D v2 = new Vector3D(7.0, -2.0, 5.0);

    Vector3D result = Vector3D.crossProduct(v1, v2);

    assertEquals(0.0, result.getX(), 1e-10);
    assertEquals(0.0, result.getY(), 1e-10);
    assertEquals(0.0, result.getZ(), 1e-10);
}

@Test
public void testCrossProduct_antiCommutativeProperty() {
    Vector3D v1 = new Vector3D(2.0, -1.0, 4.0);
    Vector3D v2 = new Vector3D(3.0, 5.0, -2.0);

    Vector3D cross12 = Vector3D.crossProduct(v1, v2);
    Vector3D cross21 = Vector3D.crossProduct(v2, v1);

    assertEquals(-cross12.getX(), cross21.getX(), 1e-10);
    assertEquals(-cross12.getY(), cross21.getY(), 1e-10);
    assertEquals(-cross12.getZ(), cross21.getZ(), 1e-10);
}
