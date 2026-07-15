@Test
void testPercentageValueForPositiveFraction() {
    Fraction fraction = Fraction.getFraction(1, 2);
    assertEquals(50.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValueForWholeNumberFraction() {
    Fraction fraction = Fraction.getFraction(1, 1);
    assertEquals(100.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValueForNegativeFraction() {
    Fraction fraction = Fraction.getFraction(-1, 4);
    assertEquals(-25.0, fraction.percentageValue(), 0.000001);
}
