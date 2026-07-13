@Test
void testAdd_positiveNumbers() {
    Complex obj = new Complex(2.0, 3.0);
    Complex rhs = new Complex(1.0, 4.0);
    Complex expected = new Complex(3.0, 7.0);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_negativeNumbers() {
    Complex obj = new Complex(-2.0, -3.0);
    Complex rhs = new Complex(-1.0, -4.0);
    Complex expected = new Complex(-3.0, -7.0);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_mixedSigns() {
    Complex obj = new Complex(2.0, -3.0);
    Complex rhs = new Complex(-1.0, 4.0);
    Complex expected = new Complex(1.0, 1.0);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_withZeroRhs() {
    Complex obj = new Complex(5.0, -2.0);
    Complex rhs = new Complex(0.0, 0.0);
    Complex expected = new Complex(5.0, -2.0);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_zeroObj() {
    Complex obj = new Complex(0.0, 0.0);
    Complex rhs = new Complex(5.0, -2.0);
    Complex expected = new Complex(5.0, -2.0);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_purelyRealNumbers() {
    Complex obj = new Complex(5.0, 0.0);
    Complex rhs = new Complex(3.0, 0.0);
    Complex expected = new Complex(8.0, 0.0);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_purelyImaginaryNumbers() {
    Complex obj = new Complex(0.0, 5.0);
    Complex rhs = new Complex(0.0, -3.0);
    Complex expected = new Complex(0.0, 2.0);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_resultsInZero() {
    Complex obj = new Complex(2.5, -3.5);
    Complex rhs = new Complex(-2.5, 3.5);
    Complex expected = new Complex(0.0, 0.0);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_largeNumbers() {
    Complex obj = new Complex(1e100, 2e100);
    Complex rhs = new Complex(3e100, 4e100);
    Complex expected = new Complex(4e100, 6e100);
    assertEquals(expected, obj.add(rhs));
}

@Test
void testAdd_smallNumbers() {
    Complex obj = new Complex(1e-10, 2e-10);
    Complex rhs = new Complex(3e-10, 4e-10);
    Complex expected = new Complex(4e-10, 6e-10);
    assertEquals(expected, obj.add(rhs));
}
