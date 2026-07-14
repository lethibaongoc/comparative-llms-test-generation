@Test
void testGreatestCommonDivisorWithZeroAndNonZero() {
    assertEquals(5, Fraction.greatestCommonDivisor(0, 5));
    assertEquals(5, Fraction.greatestCommonDivisor(5, 0));
    assertEquals(0, Fraction.greatestCommonDivisor(0, 0));
    assertEquals(10, Fraction.greatestCommonDivisor(0, -10));
    assertEquals(10, Fraction.greatestCommonDivisor(-10, 0));
}

@Test
void testGreatestCommonDivisorWithPositiveNumbers() {
    assertEquals(1, Fraction.greatestCommonDivisor(1, 1));
    assertEquals(2, Fraction.greatestCommonDivisor(2, 4));
    assertEquals(3, Fraction.greatestCommonDivisor(6, 9));
    assertEquals(5, Fraction.greatestCommonDivisor(10, 15));
    assertEquals(7, Fraction.greatestCommonDivisor(14, 21));
}

@Test
void testGreatestCommonDivisorWithNegativeNumbers() {
    assertEquals(1, Fraction.greatestCommonDivisor(-1, 1));
    assertEquals(2, Fraction.greatestCommonDivisor(-2, 4));
    assertEquals(3, Fraction.greatestCommonDivisor(6, -9));
    assertEquals(5, Fraction.greatestCommonDivisor(-10, -15));
}

@Test
void testGreatestCommonDivisorWithLargeNumbers() {
    assertEquals(1, Fraction.greatestCommonDivisor(1000000, 999999));
    assertEquals(100, Fraction.greatestCommonDivisor(1000, 10000));
    assertEquals(1024, Fraction.greatestCommonDivisor(1024, 2048));
}

@Test
void testGreatestCommonDivisorWithEqualNumbers() {
    assertEquals(10, Fraction.greatestCommonDivisor(10, 10));
    assertEquals(1, Fraction.greatestCommonDivisor(1, 1));
    assertEquals(42, Fraction.greatestCommonDivisor(42, 42));
}

@Test
void testGreatestCommonDivisorWithOverflowThrowsException() {
    assertThrows(ArithmeticException.class,
        () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 0));
    assertThrows(ArithmeticException.class,
        () -> Fraction.greatestCommonDivisor(0, Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class,
        () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class,
        () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 1));
    assertThrows(ArithmeticException.class,
        () -> Fraction.greatestCommonDivisor(1, Integer.MIN_VALUE));
}

@Test
void testGreatestCommonDivisorWithOverflowAtKEquals31() {
    assertThrows(ArithmeticException.class,
        () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE / 2, Integer.MIN_VALUE / 2));
}

@Test
void testGreatestCommonDivisorWithPrimeNumbers() {
    assertEquals(1, Fraction.greatestCommonDivisor(17, 19));
    assertEquals(1, Fraction.greatestCommonDivisor(31, 37));
    assertEquals(1, Fraction.greatestCommonDivisor(2, 3));
}

@Test
void testGreatestCommonDivisorWithPowersOfTwo() {
    assertEquals(1, Fraction.greatestCommonDivisor(1, 2));
    assertEquals(2, Fraction.greatestCommonDivisor(2, 4));
    assertEquals(4, Fraction.greatestCommonDivisor(4, 8));
    assertEquals(8, Fraction.greatestCommonDivisor(8, 16));
    assertEquals(16, Fraction.greatestCommonDivisor(16, 32));
}

@Test
void testGreatestCommonDivisorWithMixedSignsAndZero() {
    assertEquals(7, Fraction.greatestCommonDivisor(-7, 0));
    assertEquals(7, Fraction.greatestCommonDivisor(0, -7));
    assertEquals(13, Fraction.greatestCommonDivisor(0, 13));
    assertEquals(13, Fraction.greatestCommonDivisor(-13, 0));
}
