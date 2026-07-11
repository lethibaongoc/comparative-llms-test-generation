@Test
void testPercentageValue_half() {
    // Represents 1/2, which is 50%
    Fraction fraction = new Fraction(1, 2);
    assertEquals(50.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_quarter() {
    // Represents 1/4, which is 25%
    Fraction fraction = new Fraction(1, 4);
    assertEquals(25.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_wholeNumber() {
    // Represents 1/1, which is 100%
    Fraction fraction = new Fraction(1, 1);
    assertEquals(100.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_moreThanOne() {
    // Represents 3/2, which is 150%
    Fraction fraction = new Fraction(3, 2);
    assertEquals(150.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_zeroNumerator() {
    // Represents 0/5, which is 0%
    Fraction fraction = new Fraction(0, 5);
    assertEquals(0.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_negativeFraction() {
    // Represents -1/2, which is -50%
    Fraction fraction = new Fraction(-1, 2);
    assertEquals(-50.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_negativeFractionMoreThanOne() {
    // Represents -3/2, which is -150%
    Fraction fraction = new Fraction(-3, 2);
    assertEquals(-150.0, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_repeatingDecimal() {
    // Represents 1/3, which is 33.333...%
    Fraction fraction = new Fraction(1, 3);
    assertEquals(33.333333, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_anotherRepeatingDecimal() {
    // Represents 2/3, which is 66.666...%
    Fraction fraction = new Fraction(2, 3);
    assertEquals(66.666666, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_largeNumbers() {
    // Represents 12345/1000, which is 1234.5%
    Fraction fraction = new Fraction(12345, 1000);
    assertEquals(1234.5, fraction.percentageValue(), 0.000001);
}

@Test
void testPercentageValue_smallFraction() {
    // Represents 1/1000, which is 0.1%
    Fraction fraction = new Fraction(1, 1000);
    assertEquals(0.1, fraction.percentageValue(), 0.000001);
}

// Assuming Fraction constructor normalizes negative denominators (e.g., 1/-2 becomes -1/2)
@Test
void testPercentageValue_denominatorNegative() {
    // Represents 1/-2, which should be -50% if normalized to -1/2
    Fraction fraction = new Fraction(1, -2);
    assertEquals(-50.0, fraction.percentageValue(), 0.000001);
}

// Assuming Fraction constructor normalizes both negative numerator and denominator (e.g., -1/-2 becomes 1/2)
@Test
void testPercentageValue_bothNumeratorAndDenominatorNegative() {
    // Represents -1/-2, which should be 50% if normalized to 1/2
    Fraction fraction = new Fraction(-1, -2);
    assertEquals(50.0, fraction.percentageValue(), 0.000001);
}