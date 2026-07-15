@Test
void testGreatestCommonDivisorForPositiveAndNegativeValues() throws Exception {
    java.lang.reflect.Method method = Fraction.class.getDeclaredMethod(
            "greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    assertEquals(6, method.invoke(null, 54, 24));
    assertEquals(6, method.invoke(null, -54, 24));
    assertEquals(6, method.invoke(null, 54, -24));
    assertEquals(6, method.invoke(null, -54, -24));
    assertEquals(1, method.invoke(null, 17, 13));
}

@Test
void testGreatestCommonDivisorWhenOneValueIsZero() throws Exception {
    java.lang.reflect.Method method = Fraction.class.getDeclaredMethod(
            "greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    assertEquals(8, method.invoke(null, 0, 8));
    assertEquals(8, method.invoke(null, 8, 0));
    assertEquals(8, method.invoke(null, 0, -8));
    assertEquals(0, method.invoke(null, 0, 0));
}

@Test
void testGreatestCommonDivisorWithIntegerMinValueAndZeroThrowsOverflow()
        throws Exception {
    java.lang.reflect.Method method = Fraction.class.getDeclaredMethod(
            "greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    java.lang.reflect.InvocationTargetException exception = assertThrows(
            java.lang.reflect.InvocationTargetException.class,
            () -> method.invoke(null, Integer.MIN_VALUE, 0));

    assertInstanceOf(ArithmeticException.class, exception.getCause());
}

@Test
void testGreatestCommonDivisorOfTwoIntegerMinValuesThrowsOverflow()
        throws Exception {
    java.lang.reflect.Method method = Fraction.class.getDeclaredMethod(
            "greatestCommonDivisor", int.class, int.class);
    method.setAccessible(true);

    java.lang.reflect.InvocationTargetException exception = assertThrows(
            java.lang.reflect.InvocationTargetException.class,
            () -> method.invoke(null, Integer.MIN_VALUE, Integer.MIN_VALUE));

    assertInstanceOf(ArithmeticException.class, exception.getCause());
}
