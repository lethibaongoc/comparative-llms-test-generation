package org.apache.commons.math3.geometry.euclidean.threed;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LineTest {

    @Test
    void testRevert() {
        Vector3D zero = new Vector3D(0, 0, 0);
        Vector3D direction = new Vector3D(1, 2, 3);
        Line line = new Line(new Line() {
            @Override
            public Vector3D getOrigin() {
                return zero;
            }

            @Override
            public Vector3D getDirection() {
                return direction;
            }
        });

        Line revertedLine = line.revert();
        assertEquals(zero, revertedLine.getOrigin());
        assertEquals(direction.negate(), revertedLine.getDirection());
    }

    @Test
    void testRevertIdentity() {
        Vector3D zero = new Vector3D(0, 0, 0);
        Vector3D direction = new Vector3D(0, 0, 0);
        Line line = new Line(new Line() {
            @Override
            public Vector3D getOrigin() {
                return zero;
            }

            @Override
            public Vector3D getDirection() {
                return direction;
            }
        });

        Line revertedLine = line.revert();
        assertEquals(zero, revertedLine.getOrigin());
        assertEquals(direction, revertedLine.getDirection());
    }

    @Test
    void testRevertRevert() {
        Vector3D zero = new Vector3D(0, 0, 0);
        Vector3D direction = new Vector3D(1, 2, 3);
        Line line = new Line(new Line() {
            @Override
            public Vector3D getOrigin() {
                return zero;
            }

            @Override
            public Vector3D getDirection() {
                return direction;
            }
        });

        Line revertedLine = line.revert().revert();
        assertEquals(zero, revertedLine.getOrigin());
        assertEquals(direction, revertedLine.getDirection());
    }
}
