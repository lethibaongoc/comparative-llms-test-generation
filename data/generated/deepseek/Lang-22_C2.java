@Test
public void testGreatestCommonDivisor_bothPositive() {
    assertEquals(6, greatestCommonDivisor(12, 18));
}

@Test
public void testGreatestCommonDivisor_oneZero() {
    assertEquals(5, greatestCommonDivisor(5, 0));
}

@Test
public void testGreatestCommonDivisor_bothZero() {
    assertEquals(0, greatestCommonDivisor(0, 0));
}

@Test
public void testGreatestCommonDivisor_negativeNumbers() {
    assertEquals(4, greatestCommonDivisor(-8, -12));
}

@Test
public void testGreatestCommonDivisor_mixedSigns() {
    assertEquals(3, greatestCommonDivisor(-9, 6));
}

@Test
public void testGreatestCommonDivisor_equalNumbers() {
    assertEquals(7, greatestCommonDivisor(7, 7));
}

@Test
public void testGreatestCommonDivisor_oneIsOne() {
    assertEquals(1, greatestCommonDivisor(1, 15));
}

@Test
public void testGreatestCommonDivisor_primeNumbers() {
    assertEquals(1, greatestCommonDivisor(17, 19));
}

@Test
public void testGreatestCommonDivisor_largeNumbers() {
    assertEquals(12, greatestCommonDivisor(144, 60));
}

@Test
public void testGreatestCommonDivisor_minValueWithZero() {
    assertThrows(ArithmeticException.class, () -> {
        greatestCommonDivisor(Integer.MIN_VALUE, 0);
    });
}

@Test
public void testGreatestCommonDivisor_zeroWithMinValue() {
    assertThrows(ArithmeticException.class, () -> {
        greatestCommonDivisor(0, Integer.MIN_VALUE);
    });
}

@Test
public void testGreatestCommonDivisor_bothMinValue() {
    assertThrows(ArithmeticException.class, () -> {
        greatestCommonDivisor(Integer.MIN_VALUE, Integer.MIN_VALUE);
    });
}
