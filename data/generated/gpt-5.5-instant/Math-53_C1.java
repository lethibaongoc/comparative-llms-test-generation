@Test
public void testAdd_regularComplexNumbers() {
    Complex c1 = new Complex(1.5, 2.5);
    Complex c2 = new Complex(3.0, -1.0);

    Complex result = c1.add(c2);

    assertEquals(4.5, result.getReal(), 1e-10);
    assertEquals(1.5, result.getImaginary(), 1e-10);
}

@Test
public void testAdd_zeroComplex() {
    Complex c1 = new Complex(2.0, -3.0);
    Complex zero = Complex.ZERO;

    Complex result = c1.add(zero);

    assertEquals(2.0, result.getReal(), 1e-10);
    assertEquals(-3.0, result.getImaginary(), 1e-10);
}

@Test
public void testAdd_leftOperandNaN() {
    Complex result = Complex.NaN.add(new Complex(1.0, 2.0));

    assertTrue(result.isNaN());
}

@Test
public void testAdd_rightOperandNaN() {
    Complex result = new Complex(1.0, 2.0).add(Complex.NaN);

    assertTrue(result.isNaN());
}

@Test
public void testAdd_bothOperandsNaN() {
    Complex result = Complex.NaN.add(Complex.NaN);

    assertTrue(result.isNaN());
}

@Test
public void testAdd_nullArgument() {
    Complex c = new Complex(1.0, 2.0);

    assertThrows(NullArgumentException.class, () -> c.add(null));
}
