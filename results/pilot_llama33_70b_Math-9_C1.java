package org.apache.commons.math3.geometry.euclidean.threed;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class LineTest {

    private Line line;
    private Vector3D zero;
    private Vector3D direction;

    @BeforeEach
    public void setUp() {
        zero = new Vector3D(1, 2, 3);
        direction = new Vector3D(4, 5, 6);
        line = new Line(new Line() {
            @Override
            public Vector3D getOrigin() {
                return zero;
            }

            @Override
            public Vector3D getDirection() {
                return direction;
            }
        });
    }

    @Test
    public void testRevertOrigin() {
        Line reverted = line.revert();
        assertEquals(zero, reverted.getOrigin());
    }

    @Test
    public void testRevertDirection() {
        Line reverted = line.revert();
        Vector3D expectedDirection = direction.negate();
        assertEquals(expectedDirection, reverted.getDirection());
    }

    @Test
    public void testRevertImmutability() {
        Line reverted = line.revert();
        assertNotSame(line, reverted);
    }
}
