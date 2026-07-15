@Test
void testEqualWithNullPaths() {
    assertTrue(ShapeUtilities.equal(null, null));

    GeneralPath path = new GeneralPath();
    assertFalse(ShapeUtilities.equal(path, null));
    assertFalse(ShapeUtilities.equal(null, path));
}

@Test
void testEqualWithIdenticalPaths() {
    GeneralPath path1 = new GeneralPath();
    path1.moveTo(1.0f, 2.0f);
    path1.lineTo(3.0f, 4.0f);
    path1.quadTo(5.0f, 6.0f, 7.0f, 8.0f);
    path1.closePath();

    GeneralPath path2 = new GeneralPath();
    path2.moveTo(1.0f, 2.0f);
    path2.lineTo(3.0f, 4.0f);
    path2.quadTo(5.0f, 6.0f, 7.0f, 8.0f);
    path2.closePath();

    assertTrue(ShapeUtilities.equal(path1, path2));
}

@Test
void testEqualReturnsFalseForDifferentWindingRules() {
    GeneralPath path1 = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
    path1.moveTo(0.0f, 0.0f);
    path1.lineTo(1.0f, 1.0f);

    GeneralPath path2 = new GeneralPath(GeneralPath.WIND_NON_ZERO);
    path2.moveTo(0.0f, 0.0f);
    path2.lineTo(1.0f, 1.0f);

    assertFalse(ShapeUtilities.equal(path1, path2));
}

@Test
void testEqualReturnsFalseForDifferentPathSegments() {
    GeneralPath path1 = new GeneralPath();
    path1.moveTo(0.0f, 0.0f);
    path1.lineTo(2.0f, 2.0f);

    GeneralPath path2 = new GeneralPath();
    path2.moveTo(0.0f, 0.0f);
    path2.quadTo(1.0f, 1.0f, 2.0f, 2.0f);

    assertFalse(ShapeUtilities.equal(path1, path2));
}

@Test
void testEqualReturnsFalseForDifferentCoordinatesOrLengths() {
    GeneralPath path1 = new GeneralPath();
    path1.moveTo(0.0f, 0.0f);
    path1.lineTo(2.0f, 2.0f);

    GeneralPath path2 = new GeneralPath();
    path2.moveTo(0.0f, 0.0f);
    path2.lineTo(3.0f, 2.0f);

    GeneralPath path3 = new GeneralPath();
    path3.moveTo(0.0f, 0.0f);
    path3.lineTo(2.0f, 2.0f);
    path3.closePath();

    assertFalse(ShapeUtilities.equal(path1, path2));
    assertFalse(ShapeUtilities.equal(path1, path3));
}
