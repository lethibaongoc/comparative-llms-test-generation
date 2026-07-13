package org.apache.commons.math.util;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FastMathTest {

    @Test
    public void testMax_NaN() {
        assertEquals(Float.NaN, FastMath.max(Float.NaN, 1.0f));
        assertEquals(Float.NaN, FastMath.max(1.0f, Float.NaN));
        assertEquals(Float.NaN, FastMath.max(Float.NaN, Float.NaN));
    }

    @Test
    public void testMax_Positive() {
        assertEquals(2.0f, FastMath.max(1.0f, 2.0f));
        assertEquals(2.0f, FastMath.max(2.0f, 1.0f));
    }

    @Test
    public void testMax_Negative() {
        assertEquals(-1.0f, FastMath.max(-2.0f, -1.0f));
        assertEquals(-1.0f, FastMath.max(-1.0f, -2.0f));
    }

    @Test
    public void testMax_Mixed() {
        assertEquals(1.0f, FastMath.max(-1.0f, 1.0f));
        assertEquals(1.0f, FastMath.max(1.0f, -1.0f));
    }

    @Test
    public void testMax_Equal() {
        assertEquals(1.0f, FastMath.max(1.0f, 1.0f));
    }

    @Test
    public void testMax_Zero() {
        assertEquals(0.0f, FastMath.max(-1.0f, 0.0f));
        assertEquals(0.0f, FastMath.max(0.0f, -1.0f));
        assertEquals(0.0f, FastMath.max(0.0f, 0.0f));
    }
}
