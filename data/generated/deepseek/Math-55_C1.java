@Test
void testStandardCrossProduct() {
    Vector3D v1 = new Vector3D(1.0, 2.0, 3.0);
    Vector3D v2 = new Vector3D(4.0, 5.0, 6.0);
    Vector3D result = Vector3D.crossProduct(v1, v2);

    final double DELTA = 1e-9;
    assertEquals(-3.0, result.x, DELTA, "X component incorrect for (1,2,3) x (4,5,6)");
    assertEquals(6.0, result.y, DELTA, "Y component incorrect for (1,2,3) x (4,5,6)");
    assertEquals(-3.0, result.z, DELTA, "Z component incorrect for (1,2,3) x (4,5,6)");
}

@Test
void testPerpendicularUnitVectors() {
    final double DELTA = 1e-9;

    Vector3D i = new Vector3D(1.0, 0.0, 0.0);
    Vector3D j = new Vector3D(0.0, 1.0, 0.0);
    Vector3D k = new Vector3D(0.0, 0.0, 1.0);

    Vector3D result_ixj = Vector3D.crossProduct(i, j);
    assertEquals(k.x, result_ixj.x, DELTA, "i x j X component");
    assertEquals(k.y, result_ixj.y, DELTA, "i x j Y component");
    assertEquals(k.z, result_ixj.z, DELTA, "i x j Z component");

    Vector3D result_jxi = Vector3D.crossProduct(j, i);
    assertEquals(-k.x, result_jxi.x, DELTA, "j x i X component");
    assertEquals(-k.y, result_jxi.y, DELTA, "j x i Y component");
    assertEquals(-k.z, result_jxi.z, DELTA, "j x i Z component");

    Vector3D result_jxk = Vector3D.crossProduct(j, k);
    assertEquals(i.x, result_jxk.x, DELTA, "j x k X component");
    assertEquals(i.y, result_jxk.y, DELTA, "j x k Y component");
    assertEquals(i.z, result_jxk.z, DELTA, "j x k Z component");
}

@Test
void testCrossProductWithZeroVector() {
    Vector3D v1 = new Vector3D(10.0, -20.0, 30.0);
    Vector3D zeroVector = new Vector3D(0.0, 0.0, 0.0);
    final double DELTA = 1e-9;

    Vector3D result1 = Vector3D.crossProduct(v1, zeroVector);
    assertEquals(0.0, result1.x, DELTA, "v x 0 X component");
    assertEquals(0.0, result1.y, DELTA, "v x 0 Y component");
    assertEquals(0.0, result1.z, DELTA, "v x 0 Z component");

    Vector3D result2 = Vector3D.crossProduct(zeroVector, v1);
    assertEquals(0.0, result2.x, DELTA, "0 x v X component");
    assertEquals(0.0, result2.y, DELTA, "0 x v Y component");
    assertEquals(0.0, result2.z, DELTA, "0 x v Z component");

    Vector3D result3 = Vector3D.crossProduct(zeroVector, zeroVector);
    assertEquals(0.0, result3.x, DELTA, "0 x 0 X component");
    assertEquals(0.0, result3.y, DELTA, "0 x 0 Y component");
    assertEquals(0.0, result3.z, DELTA, "0 x 0 Z component");
}

@Test
void testParallelVectors() {
    Vector3D v1 = new Vector3D(1.0, 2.0, 3.0);
    Vector3D v2 = new Vector3D(2.0, 4.0, 6.0);
    Vector3D v3 = new Vector3D(-3.0, -6.0, -9.0);
    final double DELTA = 1e-9;

    Vector3D result1 = Vector3D.crossProduct(v1, v2);
    assertEquals(0.0, result1.x, DELTA, "Parallel vectors (v1 x 2v1) X component");
    assertEquals(0.0, result1.y, DELTA, "Parallel vectors (v1 x 2v1) Y component");
    assertEquals(0.0, result1.z, DELTA, "Parallel vectors (v1 x 2v1) Z component");

    Vector3D result2 = Vector3D.crossProduct(v1, v3);
    assertEquals(0.0, result2.x, DELTA, "Anti-parallel vectors (v1 x -3v1) X component");
    assertEquals(0.0, result2.y, DELTA, "Anti-parallel vectors (v1 x -3v1) Y component");
    assertEquals(0.0, result2.z, DELTA, "Anti-parallel vectors (v1 x -3v1) Z component");
}

@Test
void testSelfCrossProduct() {
    Vector3D v1 = new Vector3D(5.0, -10.0, 15.0);
    Vector3D result = Vector3D.crossProduct(v1, v1);
    final double DELTA = 1e-9;

    assertEquals(0.0, result.x, DELTA, "Self cross product X component");
    assertEquals(0.0, result.y, DELTA, "Self cross product Y component");
    assertEquals(0.0, result.z, DELTA, "Self cross product Z component");
}

@Test
void testAntiCommutativity() {
    Vector3D v1 = new Vector3D(1.0, 2.0, 3.0);
    Vector3D v2 = new Vector3D(4.0, 5.0, 6.0);
    final double DELTA = 1e-9;

    Vector3D v1_cross_v2 = Vector3D.crossProduct(v1, v2);
    Vector3D v2_cross_v1 = Vector3D.crossProduct(v2, v1);

    assertEquals(v1_cross_v2.x, -v2_cross_v1.x, DELTA, "Anti-commutativity X component");
    assertEquals(v1_cross_v2.y, -v2_cross_v1.y, DELTA, "Anti-commutativity Y component");
    assertEquals(v1_cross_v2.z, -v2_cross_v1.z, DELTA, "Anti-commutativity Z component");
}

@Test
void testVectorsWithNegativeComponents() {
    Vector3D v1 = new Vector3D(-1.0, 2.0, -3.0);
    Vector3D v2 = new Vector3D(4.0, -5.0, 6.0);
    Vector3D result = Vector3D.crossProduct(v1, v2);

    final double DELTA = 1e-9;
    assertEquals(-3.0, result.x, DELTA, "X component incorrect for (-1,2,-3) x (4,-5,6)");
    assertEquals(-6.0, result.y, DELTA, "Y component incorrect for (-1,2,-3) x (4,-5,6)");
    assertEquals(-3.0, result.z, DELTA, "Z component incorrect for (-1,2,-3) x (4,-5,6)");
}

@Test
void testZeroComponentsWithOthers() {
    Vector3D v1 = new Vector3D(1.0, 0.0, 2.0);
    Vector3D v2 = new Vector3D(0.0, 3.0, 0.0);
    Vector3D result = Vector3D.crossProduct(v1, v2);

    final double DELTA = 1e-9;
    assertEquals(-6.0, result.x, DELTA, "X component incorrect for (1,0,2) x (0,3,0)");
    assertEquals(0.0, result.y, DELTA, "Y component incorrect for (1,0,2) x (0,3,0)");
    assertEquals(3.0, result.z, DELTA, "Z component incorrect for (1,0,2) x (0,3,0)");
}
