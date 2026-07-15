@Test
void testBothNull() {
    assertTrue(ShapeUtilities.equal(null, null));
}

@Test
void testFirstNullSecondNotNull() {
    GeneralPath p2 = new GeneralPath();
    assertFalse(ShapeUtilities.equal(null, p2));
}

@Test
void testFirstNotNullSecondNull() {
    GeneralPath p1 = new GeneralPath();
    assertFalse(ShapeUtilities.equal(p1, null));
}

@Test
void testDifferentWindingRules() {
    GeneralPath p1 = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
    GeneralPath p2 = new GeneralPath(GeneralPath.WIND_NON_ZERO);
    assertFalse(ShapeUtilities.equal(p1, p2));
}

@Test
void testEmptyPathsEqual() {
    GeneralPath p1 = new GeneralPath();
    GeneralPath p2 = new GeneralPath();
    assertTrue(ShapeUtilities.equal(p1, p2));
}

@Test
void testIdenticalPathsEqual() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(10.0f, 10.0f);
    p1.closePath();

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);
    p2.lineTo(10.0f, 10.0f);
    p2.closePath();

    assertTrue(ShapeUtilities.equal(p1, p2));
}

@Test
void testDifferentSegmentTypes() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(10.0f, 10.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);
    p2.quadTo(5.0f, 5.0f, 10.0f, 10.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
}

@Test
void testDifferentSegmentValues() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(10.0f, 10.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);
    p2.lineTo(10.0f, 20.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
}

@Test
void testDifferentPathLengths() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(10.0f, 10.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);
    p2.lineTo(10.0f, 10.0f);
    p2.lineTo(20.0f, 20.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
}
