@Test
void testGreatestCommonDivisorWithZeros() {
    assertEquals(5, Fraction.greatestCommonDivisor(0, 5));
    assertEquals(5, Fraction.greatestCommonDivisor(5, 0));
    assertEquals(5, Fraction.greatestCommonDivisor(0, -5));
    assertEquals(5, Fraction.greatestCommonDivisor(-5, 0));
    assertEquals(0, Fraction.greatestCommonDivisor(0, 0));
}

@Test
void testGreatestCommonDivisorZeroAndOverflow() {
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(0, Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 0));
}

@Test
void testGreatestCommonDivisorStandardValues() {
    assertEquals(1, Fraction.greatestCommonDivisor(1, 1));
    assertEquals(1, Fraction.greatestCommonDivisor(1, -1));
    assertEquals(1, Fraction.greatestCommonDivisor(-1, 1));
    assertEquals(1, Fraction.greatestCommonDivisor(-1, -1));
    assertEquals(4, Fraction.greatestCommonDivisor(12, 8));
    assertEquals(4, Fraction.greatestCommonDivisor(-12, 8));
    assertEquals(4, Fraction.greatestCommonDivisor(12, -8));
    assertEquals(4, Fraction.greatestCommonDivisor(-12, -8));
}

@Test
void testGreatestCommonDivisorPrimes() {
    assertEquals(1, Fraction.greatestCommonDivisor(13, 17));
    assertEquals(1, Fraction.greatestCommonDivisor(13, -17));
}

@Test
void testGreatestCommonDivisorPowersOfTwo() {
    assertEquals(8, Fraction.greatestCommonDivisor(8, 16));
    assertEquals(1073741824, Fraction.greatestCommonDivisor(1073741824, 1073741824));
}

@Test
void testGreatestCommonDivisorOverflowEvenDivision() {
    // Both variables are Integer.MIN_VALUE, which translates to a gcd of 2^31, causing an overflow
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, Integer.MIN_VALUE));
}
