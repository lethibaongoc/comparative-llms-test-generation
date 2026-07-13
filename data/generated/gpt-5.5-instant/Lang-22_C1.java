@Test
public void testGreatestCommonDivisor_bothZero() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, 0, 0);

    assertEquals(0, result);
}

@Test
public void testGreatestCommonDivisor_oneZero() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result1 = (Integer) method.invoke(null, 0, -12);
    int result2 = (Integer) method.invoke(null, 15, 0);

    assertEquals(12, result1);
    assertEquals(15, result2);
}

@Test
public void testGreatestCommonDivisor_commonDivisor() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, 54, 24);

    assertEquals(6, result);
}

@Test
public void testGreatestCommonDivisor_coprimeNumbers() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, 17, 13);

    assertEquals(1, result);
}

@Test
public void testGreatestCommonDivisor_negativeInputs() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    int result = (Integer) method.invoke(null, -42, -56);

    assertEquals(14, result);
}

@Test
public void testGreatestCommonDivisor_zeroAndMinValue_throwsArithmeticException() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    InvocationTargetException ex = assertThrows(
        InvocationTargetException.class,
        () -> method.invoke(null, Integer.MIN_VALUE, 0));

    assertTrue(ex.getCause() instanceof ArithmeticException);
    assertEquals("overflow: gcd is 2^31", ex.getCause().getMessage());
}

@Test
public void testGreatestCommonDivisor_powerOfTwoOverflow_throwsArithmeticException() throws Exception {
    Method method = Fraction.class.getDeclaredMethod("greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    InvocationTargetException ex = assertThrows(
        InvocationTargetException.class,
        () -> method.invoke(null, Integer.MIN_VALUE, Integer.MIN_VALUE));

    assertTrue(ex.getCause() instanceof ArithmeticException);
    assertEquals("overflow: gcd is 2^31", ex.getCause().getMessage());
}
