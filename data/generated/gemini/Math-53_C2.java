@Test
void testAddSuccess() {
    Complex c1 = new Complex(1.0, 2.0);
    Complex c2 = new Complex(3.0, 4.0);
    Complex result = c1.add(c2);
    assertEquals(4.0, result.getReal(), 1e-15);
    assertEquals(6.0, result.getImaginary(), 1e-15);
}

@Test
void testAddNaN() {
    Complex c1 = new Complex(1.0, 2.0);
    Complex c2 = Complex.NaN;
    assertTrue(c1.add(c2).isNaN());
    assertTrue(c2.add(c1).isNaN());
}

@Test
void testAddNullThrowsException() {
    Complex c = new Complex(1.0, 2.0);
    assertThrows(NullArgumentException.class, () -> c.add(null));
}
