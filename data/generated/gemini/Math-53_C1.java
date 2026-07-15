@Test
void testAdd_withNullArgument_throwsNullArgumentException() {
    Complex c = new Complex(1.0, 2.0);
    assertThrows(NullArgumentException.class, () -> c.add(null));
}

@Test
void testAdd_whenThisIsNaN_returnsNaN() {
    Complex c1 = Complex.NaN;
    Complex c2 = new Complex(1.0, 2.0);

    Complex result = c1.add(c2);

    assertTrue(result.isNaN());
    assertEquals(Complex.NaN, result);
}

@Test
void testAdd_whenRhsIsNaN_returnsNaN() {
    Complex c1 = new Complex(1.0, 2.0);
    Complex c2 = Complex.NaN;

    Complex result = c1.add(c2);

    assertTrue(result.isNaN());
    assertEquals(Complex.NaN, result);
}

@Test
void testAdd_whenBothAreNaN_returnsNaN() {
    Complex c1 = Complex.NaN;
    Complex c2 = Complex.NaN;

    Complex result = c1.add(c2);

    assertTrue(result.isNaN());
    assertEquals(Complex.NaN, result);
}

@Test
void testAdd_withValidComplexNumbers_returnsCorrectSum() {
    Complex c1 = new Complex(2.5, 3.5);
    Complex c2 = new Complex(1.5, -1.5);

    Complex result = c1.add(c2);

    assertEquals(4.0, result.getReal(), 1e-9);
    assertEquals(2.0, result.getImaginary(), 1e-9);
}

@Test
void testAdd_withNegativeValues_returnsCorrectSum() {
    Complex c1 = new Complex(-2.0, -4.0);
    Complex c2 = new Complex(-3.0, -1.5);

    Complex result = c1.add(c2);

    assertEquals(-5.0, result.getReal(), 1e-9);
    assertEquals(-5.5, result.getImaginary(), 1e-9);
}
