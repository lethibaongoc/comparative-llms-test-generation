@Test
void testPercentageValue_Half() {
    Fraction fraction = new Fraction(1, 2);
    double expected = 50.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_Quarter() {
    Fraction fraction = new Fraction(1, 4);
    double expected = 25.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_ThreeQuarters() {
    Fraction fraction = new Fraction(3, 4);
    double expected = 75.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_WholeNumberOne() {
    Fraction fraction = new Fraction(1, 1);
    double expected = 100.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_Zero() {
    Fraction fraction = new Fraction(0, 10);
    double expected = 0.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_NegativeHalf() {
    Fraction fraction = new Fraction(-1, 2);
    double expected = -50.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_NegativeWholeNumber() {
    Fraction fraction = new Fraction(-2, 1);
    double expected = -200.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_OneThird() {
    Fraction fraction = new Fraction(1, 3);
    double expected = (1.0 / 3.0) * 100.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_TwoThirds() {
    Fraction fraction = new Fraction(2, 3);
    double expected = (2.0 / 3.0) * 100.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_MixedNumberEquivalent() {
    Fraction fraction = new Fraction(5, 2);
    double expected = 250.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_LargeValue() {
    Fraction fraction = new Fraction(1000, 1);
    double expected = 1000.0 * 100.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_SmallPercentage() {
    Fraction fraction = new Fraction(1, 200);
    double expected = 0.5;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}

@Test
void testPercentageValue_FractionEquivalentToOnePercent() {
    Fraction fraction = new Fraction(1, 100);
    double expected = 1.0;
    assertEquals(expected, fraction.percentageValue(), 0.00001);
}
