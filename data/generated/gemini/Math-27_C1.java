@Test
@DisplayName("Should return 100.0 for a fraction of 1/1")
void percentageValue_oneWhole_returnsOneHundred() {
    Fraction fraction = new Fraction(1, 1);
    double expected = 100.0;
    double actual = fraction.percentageValue();
    assertEquals(expected, actual, 0.0001);
}

@Test
@DisplayName("Should return 50.0 for a fraction of 1/2")
void percentageValue_oneHalf_returnsFifty() {
    Fraction fraction = new Fraction(1, 2);
    double expected = 50.0;
    double actual = fraction.percentageValue();
    assertEquals(expected, actual, 0.0001);
}

@Test
@DisplayName("Should return 33.3333... for a fraction of 1/3")
void percentageValue_oneThird_returnsThirtyThreeRepeating() {
    Fraction fraction = new Fraction(1, 3);
    double expected = 33.333333333333336;
    double actual = fraction.percentageValue();
    assertEquals(expected, actual, 0.0001);
}

@Test
@DisplayName("Should return 0.0 for a fraction of 0/1")
void percentageValue_zeroNumerator_returnsZero() {
    Fraction fraction = new Fraction(0, 1);
    double expected = 0.0;
    double actual = fraction.percentageValue();
    assertEquals(expected, actual, 0.0001);
}

@Test
@DisplayName("Should return negative percentage for negative fraction")
void percentageValue_negativeFraction_returnsNegativePercentage() {
    Fraction fraction = new Fraction(-1, 4);
    double expected = -25.0;
    double actual = fraction.percentageValue();
    assertEquals(expected, actual, 0.0001);
}

@Test
@DisplayName("Should return percentage greater than 100 for improper fractions")
void percentageValue_improperFraction_returnsOverOneHundred() {
    Fraction fraction = new Fraction(5, 2);
    double expected = 250.0;
    double actual = fraction.percentageValue();
    assertEquals(expected, actual, 0.0001);
}
