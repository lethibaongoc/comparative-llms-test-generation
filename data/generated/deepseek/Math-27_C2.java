@Test
void testPercentageValue_half() {
    Fraction fraction = new Fraction(1, 2);
    assertEquals(50.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_quarter() {
    Fraction fraction = new Fraction(1, 4);
    assertEquals(25.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_wholeNumber() {
    Fraction fraction = new Fraction(1, 1);
    assertEquals(100.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_moreThanOne() {
    Fraction fraction = new Fraction(3, 2);
    assertEquals(150.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_zeroNumerator() {
    Fraction fraction = new Fraction(0, 5);
    assertEquals(0.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_negativeFraction() {
    Fraction fraction = new Fraction(-1, 2);
    assertEquals(-50.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_negativeFractionMoreThanOne() {
    Fraction fraction = new Fraction(-3, 2);
    assertEquals(-150.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_repeatingDecimal() {
    Fraction fraction = new Fraction(1, 3);
    assertEquals(33.333333, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_anotherRepeatingDecimal() {
    Fraction fraction = new Fraction(2, 3);
    assertEquals(66.666666, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_largeNumbers() {
    Fraction fraction = new Fraction(12345, 1000);
    assertEquals(1234.5, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_smallFraction() {
    Fraction fraction = new Fraction(1, 1000);
    assertEquals(0.1, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_denominatorNegative() {
    Fraction fraction = new Fraction(1, -2);
    assertEquals(-50.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_bothNumeratorAndDenominatorNegative() {
    Fraction fraction = new Fraction(-1, -2);
    assertEquals(50.0, fraction.percentageValue(), 0.000001);
}
