package org.apache.commons.math4.complex;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexTest {

    @Test
    public void testAdd_NaN() {
        Complex complex = new Complex(1.0, 2.0);
        Complex nan = Complex.NaN;
        assertEquals(Complex.NaN, complex.add(nan));
    }

    @Test
    public void testAdd_Normal() {
        Complex complex1 = new Complex(1.0, 2.0);
        Complex complex2 = new Complex(3.0, 4.0);
        Complex result = complex1.add(complex2);
        assertEquals(4.0, result.getReal(), 1e-10);
        assertEquals(6.0, result.getImaginary(), 1e-10);
    }

    @Test
    public void testAdd_Zero() {
        Complex complex = new Complex(1.0, 2.0);
        Complex zero = new Complex(0.0, 0.0);
        Complex result = complex.add(zero);
        assertEquals(1.0, result.getReal(), 1e-10);
        assertEquals(2.0, result.getImaginary(), 1e-10);
    }

    @Test
    public void testAdd_SameInstance() {
        Complex complex = new Complex(1.0, 2.0);
        Complex result = complex.add(complex);
        assertEquals(2.0, result.getReal(), 1e-10);
        assertEquals(4.0, result.getImaginary(), 1e-10);
    }
}
