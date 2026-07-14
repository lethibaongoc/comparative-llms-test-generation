@Test
void testGcdWithBothZero() {
  assertEquals(0, greatestCommonDivisor(0, 0));
}

@Test
void testGcdWithZeroAndPositive() {
  assertEquals(5, greatestCommonDivisor(0, 5));
  assertEquals(5, greatestCommonDivisor(5, 0));
}

@Test
void testGcdWithZeroAndNegative() {
  assertEquals(5, greatestCommonDivisor(0, -5));
  assertEquals(5, greatestCommonDivisor(-5, 0));
}

@Test
void testGcdWithZeroAndMinValue() {
  assertThrows(ArithmeticException.class, () -> greatestCommonDivisor(0, Integer.MIN_VALUE));
  assertThrows(ArithmeticException.class, () -> greatestCommonDivisor(Integer.MIN_VALUE, 0));
}

@Test
void testGcdWithPositiveNumbers() {
  assertEquals(4, greatestCommonDivisor(12, 8));
  assertEquals(6, greatestCommonDivisor(18, 12));
  assertEquals(1, greatestCommonDivisor(17, 13));
}

@Test
void testGcdWithNegativeNumbers() {
  assertEquals(4, greatestCommonDivisor(-12, -8));
  assertEquals(6, greatestCommonDivisor(-18, -12));
  assertEquals(1, greatestCommonDivisor(-17, -13));
}

@Test
void testGcdWithMixedSigns() {
  assertEquals(4, greatestCommonDivisor(-12, 8));
  assertEquals(6, greatestCommonDivisor(18, -12));
  assertEquals(1, greatestCommonDivisor(-17, 13));
}

@Test
void testGcdWithPrimeNumbers() {
  assertEquals(1, greatestCommonDivisor(17, 19));
  assertEquals(1, greatestCommonDivisor(23, 29));
}

@Test
void testGcdWithEqualNumbers() {
  assertEquals(7, greatestCommonDivisor(7, 7));
  assertEquals(12, greatestCommonDivisor(12, 12));
}

@Test
void testGcdWithOne() {
  assertEquals(1, greatestCommonDivisor(1, 100));
  assertEquals(1, greatestCommonDivisor(100, 1));
  assertEquals(1, greatestCommonDivisor(-1, 100));
}

@Test
void testGcdWithMinValueAndPositive() {
  assertThrows(ArithmeticException.class, () -> greatestCommonDivisor(Integer.MIN_VALUE, 2));
}

@Test
void testGcdWithMinValueAndNegative() {
  assertThrows(ArithmeticException.class, () -> greatestCommonDivisor(Integer.MIN_VALUE, -2));
}

@Test
void testGcdWithMinValueAndOddNumber() {
  assertThrows(ArithmeticException.class, () -> greatestCommonDivisor(Integer.MIN_VALUE, 3));
}

@Test
void testGcdWithLargeNumbers() {
  assertEquals(1, greatestCommonDivisor(2147483647, 2147483646));
  assertEquals(2, greatestCommonDivisor(2147483646, 2147483644));
}

@Test
void testGcdWithPowersOfTwo() {
  assertEquals(4, greatestCommonDivisor(12, 8));
  assertEquals(16, greatestCommonDivisor(32, 16));
  assertEquals(8, greatestCommonDivisor(64, 24));
}
