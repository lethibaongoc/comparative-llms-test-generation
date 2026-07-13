@Test
public void testPercentageValue_positiveFraction() {
    Fraction obj = Fraction.getFraction(1, 2);

    assertEquals(50.0, obj.percentageValue(), 0.0000001);
}

@Test
public void testPercentageValue_negativeFraction() {
    Fraction obj = Fraction.getFraction(-3, 4);

    assertEquals(-75.0, obj.percentageValue(), 0.0000001);
}

@Test
public void testPercentageValue_zeroFraction() {
    Fraction obj = Fraction.ZERO;

    assertEquals(0.0, obj.percentageValue(), 0.0000001);
}

@Test
public void testPercentageValue_recurringDecimal() {
    Fraction obj = Fraction.getFraction(1, 3);

    assertEquals(33.333333333333336, obj.percentageValue(), 0.0000001);
}
