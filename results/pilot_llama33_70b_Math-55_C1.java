package org.apache.commons.math.geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3DTest {

    @Test
    public void testCrossProductZeroVectors() {
        Vector3D v1 = Vector3D.ZERO;
        Vector3D v2 = Vector3D.ZERO;
        Vector3D result = Vector3D.crossProduct(v1, v2);
        assertEquals(Vector3D.ZERO, result);
    }

    @Test
    public void testCrossProductOrthogonalVectors() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = new Vector3D(0, 1, 0);
        Vector3D result = Vector3D.crossProduct(v1, v2);
        assertEquals(new Vector3D(0, 0, 1), result);
    }

    @Test
    public void testCrossProductParallelVectors() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = new Vector3D(2, 0, 0);
        Vector3D result = Vector3D.crossProduct(v1, v2);
        assertEquals(Vector3D.ZERO, result);
    }

    @Test
    public void testCrossProductAntiCommutativity() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = new Vector3D(0, 1, 0);
        Vector3D result1 = Vector3D.crossProduct(v1, v2);
        Vector3D result2 = Vector3D.crossProduct(v2, v1);
        assertEquals(result1, result2.negate());
    }

    @Test
    public void testCrossProductDistributivity() {
        Vector3D v1 = new Vector3D(1, 0, 0);
        Vector3D v2 = new Vector3D(0, 1, 0);
        Vector3D v3 = new Vector3D(0, 0, 1);
        Vector3D result1 = Vector3D.crossProduct(v1, Vector3D.crossProduct(v2, v3));
        Vector3D result2 = Vector3D.crossProduct(Vector3D.crossProduct(v1, v2), v3);
        assertEquals(result1, result2);
    }
}
