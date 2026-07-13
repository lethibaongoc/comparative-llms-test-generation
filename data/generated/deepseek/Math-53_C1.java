@Test
void testAddPositiveNumbers() {
    Complex c1 = new Complex(1.0, 2.0);
    Complex c2 = new Complex(3.0, 4.0);
    Complex result = c1.add(c2);
    Assertions.assertEquals(4.0, result.getReal(), 1e-9);
    Assertions.assertEquals(6.0, result.getImaginary(), 1e-9);
}

@Test
void testAddNegativeNumbers() {
    Complex c1 = new Complex(-1.0, -2.0);
    Complex c2 = new Complex(-3.0, -4.0);
    Complex result = c1.add(c2);
    Assertions.assertEquals(-4.0, result.getReal(), 1e-9);
    Assertions.assertEquals(-6.0, result.getImaginary(), 1e-9);
}

@Test
void testAddMixedSigns() {
    Complex c1 = new Complex(5.0, -2.0);
    Complex c2 = new Complex(-3.0, 4.0);
    Complex result = c1.add(c2);
    Assertions.assertEquals(2.0, result.getReal(), 1e-9);
    Assertions.assertEquals(2.0, result.getImaginary(), 1e-9);
}

@Test
void testAddToZero() {
    Complex c1 = new Complex(0.0, 0.0);
    Complex c2 = new Complex(3.5, -2.5);
    Complex result = c1.add(c2);
    Assertions.assertEquals(3.5, result.getReal(), 1e-9);
    Assertions.assertEquals(-2.5, result.getImaginary(), 1e-9);
}

@Test
void testAddZeroToNumber() {
    Complex c1 = new Complex(3.5, -2.5);
    Complex c2 = new Complex(0.0, 0.0);
    Complex result = c1.add(c2);
    Assertions.assertEquals(3.5, result.getReal(), 1e-9);
    Assertions.assertEquals(-2.5, result.getImaginary(), 1e-9);
}

@Test
void testAddWithZeroRealPart() {
    Complex c1 = new Complex(0.0, 5.0);
    Complex c2 = new Complex(2.0, -3.0);
    Complex result = c1.add(c2);
    Assertions.assertEquals(2.0, result.getReal(), 1e-9);
    Assertions.assertEquals(2.0, result.getImaginary(), 1e-9);
}

@Test
void testAddWithZeroImaginaryPart() {
    Complex c1 = new Complex(5.0, 0.0);
    Complex c2 = new Complex(-2.0, 3.0);
    Complex result = c1.add(c2);
    Assertions.assertEquals(3.0, result.getReal(), 1e-9);
    Assertions.assertEquals(3.0, result.getImaginary(), 1e-9);
}

@Test
void testAddSelf() {
    Complex c1 = new Complex(1.5, -2.5);
    Complex result = c1.add(c1);
    Assertions.assertEquals(3.0, result.getReal(), 1e-9);
    Assertions.assertEquals(-5.0, result.getImaginary(), 1e-9);
}

@Test
void testAddLargeNumbers() {
    Complex c1 = new Complex(1e10, 2e10);
    Complex c2 = new Complex(3e10, 4e10);
    Complex result = c1.add(c2);
    Assertions.assertEquals(4e10, result.getReal(), 1e-9);
    Assertions.assertEquals(6e10, result.getImaginary(), 1e-9);
}

@Test
void testAddSmallNumbers() {
    Complex c1 = new Complex(1e-10, 2e-10);
    Complex c2 = new Complex(3e-10, 4e-10);
    Complex result = c1.add(c2);
    Assertions.assertEquals(4e-10, result.getReal(), 1e-9);
    Assertions.assertEquals(6e-10, result.getImaginary(), 1e-9);
}

@Test
void testAddWithDoublesPrecision() {
    Complex c1 = new Complex(0.1, 0.2);
    Complex c2 = new Complex(0.05, 0.003);
    Complex result = c1.add(c2);
    Assertions.assertEquals(0.15, result.getReal(), 1e-9);
    Assertions.assertEquals(0.203, result.getImaginary(), 1e-9);
}

@Test
void testAddWithNaN() {
    Complex c1 = new Complex(Double.NaN, 1.0);
    Complex c2 = new Complex(2.0, Double.NaN);
    Complex result = c1.add(c2);
    Assertions.assertTrue(Double.isNaN(result.getReal()));
    Assertions.assertTrue(Double.isNaN(result.getImaginary()));

    c1 = new Complex(3.0, Double.NaN);
    c2 = new Complex(4.0, 5.0);
    result = c1.add(c2);
    Assertions.assertEquals(7.0, result.getReal(), 1e-9);
    Assertions.assertTrue(Double.isNaN(result.getImaginary()));
}

@Test
void testAddWithInfinity() {
    Complex c1 = new Complex(Double.POSITIVE_INFINITY, 1.0);
    Complex c2 = new Complex(2.0, Double.NEGATIVE_INFINITY);
    Complex result = c1.add(c2);
    Assertions.assertEquals(Double.POSITIVE_INFINITY, result.getReal());
    Assertions.assertEquals(Double.NEGATIVE_INFINITY, result.getImaginary());

    c1 = new Complex(3.0, Double.POSITIVE_INFINITY);
    Complex c3 = new Complex(Double.NEGATIVE_INFINITY, 5.0);
    Complex result2 = c1.add(c3);
    Assertions.assertTrue(Double.isNaN(result2.getReal()));
    Assertions.assertEquals(Double.POSITIVE_INFINITY, result2.getImaginary());
}
