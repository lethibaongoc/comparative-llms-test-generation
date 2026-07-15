@Test
void testPercentageValuePositive() {
    Fraction fraction = new Fraction(1, 4); // 0.25
    assertEquals(25.0, fraction.percentageValue(), 0.0001);
}

@Test
void testPercentageValueNegative() {
    Fraction fraction = new Fraction(-1, 2); // -0.5
    assertEquals(-50.0, fraction.percentageValue(), 0.0001);
}

@Test
void testPercentageValueZero() {
    Fraction fraction = new Fraction(0, 1); // 0
    assertEquals(0.0, fraction.percentageValue(), 0.0001);
}

@Test
void testPercentageValueGreaterWithImproperFraction() {
    Fraction fraction = new Fraction(5, 2); // 2.5
    assertEquals(250.0, fraction.percentageValue(), 0.0001);
}
