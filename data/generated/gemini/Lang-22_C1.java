@Test
void testGcdWithZero() {
    assertEquals(5, Fraction.greatestCommonDivisor(0, 5));
    assertEquals(5, Fraction.greatestCommonDivisor(5, 0));
    assertEquals(5, Fraction.greatestCommonDivisor(0, -5));
    assertEquals(5, Fraction.greatestCommonDivisor(-5, 0));
    assertEquals(0, Fraction.greatestCommonDivisor(0, 0));
}

@Test
void testGcdWithZeroAndMinIntegerThrowsOverflow() {
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(0, Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 0));
}

@Test
void testGcdCommonCases() {
    assertEquals(1, Fraction.greatestCommonDivisor(1, 1));
    assertEquals(6, Fraction.greatestCommonDivisor(12, 18));
    assertEquals(6, Fraction.greatestCommonDivisor(-12, 18));
    assertEquals(6, Fraction.greatestCommonDivisor(12, -18));
    assertEquals(6, Fraction.greatestCommonDivisor(-12, -18));
}

@Test
void testGcdPrimeNumbers() {
    assertEquals(1, Fraction.greatestCommonDivisor(13, 17));
    assertEquals(1, Fraction.greatestCommonDivisor(-13, 17));
}

@Test
void testGcdOneIsMultipleOfOther() {
    assertEquals(5, Fraction.greatestCommonDivisor(5, 15));
    assertEquals(5, Fraction.greatestCommonDivisor(15, 5));
}

@Test
void testGcdPowerOfTwoFactors() {
    // Exercises the binary GCD common factors division loop (k)
    assertEquals(4, Fraction.greatestCommonDivisor(8, 12));
    assertEquals(8, Fraction.greatestCommonDivisor(40, 24));
}

@Test
void testGcdExtremeValues() {
    assertEquals(1, Fraction.greatestCommonDivisor(Integer.MAX_VALUE, 1));
    // gcd(Integer.MIN_VALUE, Integer.MIN_VALUE) or similar cases that evaluate to 2^31
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, Integer.MIN_VALUE));
}
