@Test
void testGreatestCommonDivisorWithZeroAndPositive() {
    assertEquals(5, Fraction.greatestCommonDivisor(0, 5));
    assertEquals(5, Fraction.greatestCommonDivisor(5, 0));
    assertEquals(1, Fraction.greatestCommonDivisor(1, 0));
    assertEquals(1, Fraction.greatestCommonDivisor(0, 1));
}

@Test
void testGreatestCommonDivisorWithZeroAndNegative() {
    assertEquals(5, Fraction.greatestCommonDivisor(0, -5));
    assertEquals(5, Fraction.greatestCommonDivisor(-5, 0));
    assertEquals(1, Fraction.greatestCommonDivisor(-1, 0));
    assertEquals(1, Fraction.greatestCommonDivisor(0, -1));
}

@Test
void testGreatestCommonDivisorWithZeroAndMinValue() {
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(0, Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 0));
}

@Test
void testGreatestCommonDivisorWithPositiveNumbers() {
    assertEquals(6, Fraction.greatestCommonDivisor(12, 18));
    assertEquals(1, Fraction.greatestCommonDivisor(7, 13));
    assertEquals(4, Fraction.greatestCommonDivisor(8, 12));
    assertEquals(1, Fraction.greatestCommonDivisor(1, 1));
}

@Test
void testGreatestCommonDivisorWithNegativeNumbers() {
    assertEquals(6, Fraction.greatestCommonDivisor(-12, -18));
    assertEquals(1, Fraction.greatestCommonDivisor(-7, -13));
    assertEquals(4, Fraction.greatestCommonDivisor(-8, -12));
}

@Test
void testGreatestCommonDivisorWithMixedSigns() {
    assertEquals(6, Fraction.greatestCommonDivisor(-12, 18));
    assertEquals(6, Fraction.greatestCommonDivisor(12, -18));
    assertEquals(1, Fraction.greatestCommonDivisor(-7, 13));
}

@Test
void testGreatestCommonDivisorWithPowersOfTwo() {
    assertEquals(8, Fraction.greatestCommonDivisor(16, 24));
    assertEquals(16, Fraction.greatestCommonDivisor(32, 48));
    assertEquals(2, Fraction.greatestCommonDivisor(2, 4));
}

@Test
void testGreatestCommonDivisorWithLargeNumbers() {
    assertEquals(1000, Fraction.greatestCommonDivisor(100000, 1000));
    assertEquals(7, Fraction.greatestCommonDivisor(49, 21));
    assertEquals(12, Fraction.greatestCommonDivisor(144, 60));
}

@Test
void testGreatestCommonDivisorWithOverflowCase() {
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(1, Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class, () -> Fraction.greatestCommonDivisor(Integer.MIN_VALUE, 1));
}

@Test
void testGreatestCommonDivisorWithEqualNumbers() {
    assertEquals(5, Fraction.greatestCommonDivisor(5, 5));
    assertEquals(7, Fraction.greatestCommonDivisor(-7, -7));
    assertEquals(3, Fraction.greatestCommonDivisor(3, -3));
}

@Test
void testGreatestCommonDivisorWithPrimitiveGcd() {
    assertEquals(1, Fraction.greatestCommonDivisor(17, 19));
    assertEquals(11, Fraction.greatestCommonDivisor(121, 11));
    assertEquals(13, Fraction.greatestCommonDivisor(169, 13));
}
