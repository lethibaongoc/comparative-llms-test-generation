package org.apache.commons.math.util;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FastMathTest {

    @Test
    void testMax_NaN() {
        assertTrue(Float.isNaN(FastMath.max(Float.NaN, 1.0f)));
        assertTrue(Float.isNaN(FastMath.max(1.0f, Float.NaN)));
    }

    @Test
    void testMax_Normal() {
        assertEquals(2.0f, FastMath.max(1.0f, 2.0f));
        assertEquals(2.0f, FastMath.max(2.0f, 1.0f));
        assertEquals(2.0f, FastMath.max(2.0f, 2.0f));
    }

    @Test
    void testMax_Equal() {
        assertEquals(2.0f, FastMath.max(2.0f, 2.0f));
    }

    @Test
    void testMax_Negative() {
        assertEquals(-1.0f, FastMath.max(-2.0f, -1.0f));
        assertEquals(-1.0f, FastMath.max(-1.0f, -2.0f));
    }
}
