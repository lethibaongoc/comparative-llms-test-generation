package org.apache.commons.math.geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3DTest {

    @Test
    void testCrossProduct() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = new Vector3D(0, 1, 0);
        Vector3D result = Vector3D.crossProduct(v1, v2);
        assertEquals(0, result.getX(), 1e-10);
        assertEquals(0, result.getY(), 1e-10);
        assertEquals(1, result.getZ(), 1e-10);
    }

    @Test
    void testCrossProductWithZeroVector() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = Vector3D.ZERO;
        Vector3D result = Vector3D.crossProduct(v1, v2);
        assertEquals(Vector3D.ZERO, result);
    }

    @Test
    void testCrossProductWithSameVector() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = new Vector3D(1, 0, 0);
        Vector3D result = Vector3D.crossProduct(v1, v2);
        assertEquals(Vector3D.ZERO, result);
    }

    @Test
    void testCrossProductWithOrthogonalVectors() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = new Vector3D(0, 1, 0);
        Vector3D result = Vector3D.crossProduct(v1, v2);
        assertEquals(0, result.getX(), 1e-10);
        assertEquals(0, result.getY(), 1e-10);
        assertEquals(1, result.getZ(), 1e-10);
    }

    @Test
    void testCrossProductWithParallelVectors() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = new Vector3D(2, 0, 0);
        Vector3D result = Vector3D.crossProduct(v1, v2);
        assertEquals(Vector3D.ZERO, result);
    }
}
