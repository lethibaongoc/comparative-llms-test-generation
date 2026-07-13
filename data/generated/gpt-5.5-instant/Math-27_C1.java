@Test
public void testPercentageValue_positiveFraction() {
    Fraction fraction = Fraction.getFraction(1, 2);

    assertEquals(50.0, fraction.percentageValue(), 0.000001);
}

@Test
public void testPercentageValue_negativeFraction() {
    Fraction fraction = Fraction.getFraction(-1, 4);

    assertEquals(-25.0, fraction.percentageValue(), 0.000001);
}

@Test
public void testPercentageValue_zeroFraction() {
    Fraction fraction = Fraction.ZERO;

    assertEquals(0.0, fraction.percentageValue(), 0.000001);
}

@Test
public void testPercentageValue_wholeNumberFraction() {
    Fraction fraction = Fraction.ONE;

    assertEquals(100.0, fraction.percentageValue(), 0.000001);
}

@Test
public void testPercentageValue_improperFraction() {
    Fraction fraction = Fraction.getFraction(3, 2);

    assertEquals(150.0, fraction.percentageValue(), 0.000001);
}
