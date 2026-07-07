package org.apache.commons.math.complex;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexTest {

    @Test
    void testAdd() {
        Complex c1 = new Complex(1.0, 2.0);
        Complex c2 = new Complex(3.0, 4.0);
        Complex result = c1.add(c2);
        assertEquals(4.0, result.getReal(), 1e-10);
        assertEquals(6.0, result.getImaginary(), 1e-10);
    }

    @Test
    void testAddNaN() {
        Complex c1 = new Complex(1.0, 2.0);
        Complex c2 = Complex.NaN;
        Complex result = c1.add(c2);
        assertTrue(result.isNaN);
    }

    @Test
    void testAddNull() {
        Complex c1 = new Complex(1.0, 2.0);
        assertThrows(NullArgumentException.class, () -> c1.add(null));
    }
}
