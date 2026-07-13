@Test
public void testGreatestCommonDivisor_coprimeNumbers() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, 17, 13);

    assertEquals(1, result);
}

@Test
public void testGreatestCommonDivisor_commonDivisor() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, 54, 24);

    assertEquals(6, result);
}

@Test
public void testGreatestCommonDivisor_zeroAndPositive() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, 0, 9);

    assertEquals(9, result);
}

@Test
public void testGreatestCommonDivisor_zeroAndNegative() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, -12, 0);

    assertEquals(12, result);
}

@Test
public void testGreatestCommonDivisor_bothZero() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, 0, 0);

    assertEquals(0, result);
}

@Test
public void testGreatestCommonDivisor_negativeInputs() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, -18, -24);

    assertEquals(6, result);
}

@Test
public void testGreatestCommonDivisor_minValueWithZero_throwsArithmeticException() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    InvocationTargetException ex = assertThrows(InvocationTargetException.class,
        () -> method.invoke(null, Integer.MIN_VALUE, 0));

    assertTrue(ex.getCause() instanceof ArithmeticException);
    assertEquals("overflow: gcd is 2^31", ex.getCause().getMessage());
}

@Test
public void testGreatestCommonDivisor_bothMinValue_throwsArithmeticException() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    InvocationTargetException ex = assertThrows(InvocationTargetException.class,
        () -> method.invoke(null, Integer.MIN_VALUE, Integer.MIN_VALUE));

    assertTrue(ex.getCause() instanceof ArithmeticException);
    assertEquals("overflow: gcd is 2^31", ex.getCause().getMessage());
}

@Test
public void testGreatestCommonDivisor_powerOfTwoOverflow_throwsArithmeticException() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    InvocationTargetException ex = assertThrows(InvocationTargetException.class,
        () -> method.invoke(null, Integer.MIN_VALUE, Integer.MIN_VALUE));

    assertTrue(ex.getCause() instanceof ArithmeticException);
    assertEquals("overflow: gcd is 2^31", ex.getCause().getMessage());
}
