package org.apache.commons.math.util;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
public class MathUtilsTest {

    @Test
    public void testDistance_SamePoint() {
        int[] p1 = {1, 2, 3};
        int[] p2 = {1, 2, 3};
        assertEquals(0.0, MathUtils.distance(p1, p2), 1e-6);
    }

    @Test
    public void testDistance_DifferentPoints() {
        int[] p1 = {1, 2, 3};
        int[] p2 = {4, 5, 6};
        assertEquals(Math.sqrt(27 * 3), MathUtils.distance(p1, p2), 1e-6);
    }

    @Test
    public void testDistance_DifferentDimensions() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtils.distance(new int[] {1, 2}, new int[] {1, 2, 3}));
    }

    @Test
    public void testDistance_NullPoints() {
        assertThrows(NullPointerException.class, () -> MathUtils.distance(null, new int[] {1, 2, 3}));
        assertThrows(NullPointerException.class, () -> MathUtils.distance(new int[] {1, 2, 3}, null));
    }

    @Test
    public void testDistance_EmptyPoints() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtils.distance(new int[] {}, new int[] {1, 2, 3}));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MathUtils.distance(new int[] {1, 2, 3}, new int[] {}));
    }
}
