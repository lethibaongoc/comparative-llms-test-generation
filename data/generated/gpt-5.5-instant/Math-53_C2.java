@Test
void testAddWithRegularComplexNumbers() {
    Complex c1 = new Complex(1.5, 2.5);
    Complex c2 = new Complex(3.0, 4.0);

    Complex result = c1.add(c2);

    assertEquals(4.5, result.getReal(), 1e-10);
    assertEquals(6.5, result.getImaginary(), 1e-10);
}

@Test
void testAddWithNaNOperandReturnsNaN() {
    Complex c = new Complex(1.0, 2.0);

    assertTrue(c.add(Complex.NaN).isNaN());
    assertTrue(Complex.NaN.add(c).isNaN());
}

@Test
void testAddWithNullArgumentThrowsException() {
    Complex c = new Complex(1.0, 2.0);

    assertThrows(NullArgumentException.class, () -> c.add(null));
}
