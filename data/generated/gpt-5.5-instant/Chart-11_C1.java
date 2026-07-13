@Test
public void testEqual_bothNull() {
    assertTrue(ShapeUtilities.equal(null, null));
}

@Test
public void testEqual_firstNull() {
    GeneralPath path = new GeneralPath();
    assertFalse(ShapeUtilities.equal(null, path));
}

@Test
public void testEqual_secondNull() {
    GeneralPath path = new GeneralPath();
    assertFalse(ShapeUtilities.equal(path, null));
}

@Test
public void testEqual_emptyPaths() {
    GeneralPath p1 = new GeneralPath();
    GeneralPath p2 = new GeneralPath();

    assertTrue(ShapeUtilities.equal(p1, p2));
}

@Test
public void testEqual_identicalPaths() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(1.0f, 2.0f);
    p1.lineTo(3.0f, 4.0f);
    p1.quadTo(5.0f, 6.0f, 7.0f, 8.0f);
    p1.curveTo(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f);
    p1.closePath();

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(1.0f, 2.0f);
    p2.lineTo(3.0f, 4.0f);
    p2.quadTo(5.0f, 6.0f, 7.0f, 8.0f);
    p2.curveTo(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f);
    p2.closePath();

    assertTrue(ShapeUtilities.equal(p1, p2));
}

@Test
public void testEqual_differentWindingRules() {
    GeneralPath p1 = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
    GeneralPath p2 = new GeneralPath(GeneralPath.WIND_NON_ZERO);

    assertFalse(ShapeUtilities.equal(p1, p2));
}

@Test
public void testEqual_differentNumberOfSegments() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(1.0f, 1.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
}

@Test
public void testEqual_differentSegmentTypes() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(1.0f, 1.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);
    p2.quadTo(1.0f, 1.0f, 2.0f, 2.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
}

@Test
public void testEqual_differentCoordinates() {
    GeneralPath p1 = new GeneralPath();
    p1.moveTo(0.0f, 0.0f);
    p1.lineTo(1.0f, 1.0f);

    GeneralPath p2 = new GeneralPath();
    p2.moveTo(0.0f, 0.0f);
    p2.lineTo(2.0f, 1.0f);

    assertFalse(ShapeUtilities.equal(p1, p2));
}
