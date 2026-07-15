@Test
void testEqualWithNulls() {
    assertTrue(ShapeUtilities.equal((GeneralPath) null, (GeneralPath) null));
    assertFalse(ShapeUtilities.equal(new GeneralPath(), (GeneralPath) null));
    assertFalse(ShapeUtilities.equal((GeneralPath) null, new GeneralPath()));
}

@Test
void testEqualDifferentWindingRules() {
    GeneralPath p1 = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
    GeneralPath p2 = new GeneralPath(GeneralPath.WIND_NON_ZERO);
    assertFalse(ShapeUtilities.equal(p1, p2));
}

@Test
void testEqualEmptyPaths() {
    GeneralPath p1 = new GeneralPath();
    GeneralPath p2 = new GeneralPath();
    assertTrue(ShapeUtilities.equal(p1, p2));
}

@Test
void testEqualIdenticalPaths() {
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
void testEqualDifferentSegmentCounts() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(10.0f, 10.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
    assertFalse(ShapeUtilities.equal(p2, p1));
}

@Test
void testEqualDifferentSegmentTypes() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(10.0f, 10.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);
    p2.quadTo(5.0f, 5.0f, 10.0f, 10.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
}

@Test
void testEqualDifferentCoordinates() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(10.0f, 10.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);
    p2.lineTo(10.0f, 20.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
}
