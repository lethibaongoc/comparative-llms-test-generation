@Test
public void testEqual_bothNull_returnsTrue() {
    assertTrue(ShapeUtilities.equal(null, null));
}

@Test
public void testEqual_firstNull_returnsFalse() {
    GeneralPath path = new GeneralPath();

    assertFalse(ShapeUtilities.equal(null, path));
}

@Test
public void testEqual_secondNull_returnsFalse() {
    GeneralPath path = new GeneralPath();

    assertFalse(ShapeUtilities.equal(path, null));
}

@Test
public void testEqual_emptyPathsWithSameWindingRule_returnsTrue() {
    GeneralPath path1 = new GeneralPath(GeneralPath.WIND_NON_ZERO);
    GeneralPath path2 = new GeneralPath(GeneralPath.WIND_NON_ZERO);

    assertTrue(ShapeUtilities.equal(path1, path2));
}

@Test
public void testEqual_differentWindingRules_returnsFalse() {
    GeneralPath path1 = new GeneralPath(GeneralPath.WIND_NON_ZERO);
    GeneralPath path2 = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

    assertFalse(ShapeUtilities.equal(path1, path2));
}

@Test
public void testEqual_identicalPaths_returnsTrue() {
    GeneralPath path1 = new GeneralPath();
    path1.moveTo(1.0f, 2.0f);
    path1.lineTo(3.0f, 4.0f);
    path1.quadTo(5.0f, 6.0f, 7.0f, 8.0f);
    path1.curveTo(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f);
    path1.closePath();

    GeneralPath path2 = new GeneralPath();
    path2.moveTo(1.0f, 2.0f);
    path2.lineTo(3.0f, 4.0f);
    path2.quadTo(5.0f, 6.0f, 7.0f, 8.0f);
    path2.curveTo(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f);
    path2.closePath();

    assertTrue(ShapeUtilities.equal(path1, path2));
}

@Test
public void testEqual_differentNumberOfSegments_returnsFalse() {
    GeneralPath path1 = new GeneralPath();
    path1.moveTo(1.0f, 2.0f);
    path1.lineTo(3.0f, 4.0f);

    GeneralPath path2 = new GeneralPath();
    path2.moveTo(1.0f, 2.0f);

    assertFalse(ShapeUtilities.equal(path1, path2));
}

@Test
public void testEqual_differentSegmentTypes_returnsFalse() {
    GeneralPath path1 = new GeneralPath();
    path1.moveTo(1.0f, 2.0f);
    path1.lineTo(3.0f, 4.0f);

    GeneralPath path2 = new GeneralPath();
    path2.moveTo(1.0f, 2.0f);
    path2.quadTo(3.0f, 4.0f, 0.0f, 0.0f);

    assertFalse(ShapeUtilities.equal(path1, path2));
}

@Test
public void testEqual_differentSegmentCoordinates_returnsFalse() {
    GeneralPath path1 = new GeneralPath();
    path1.moveTo(1.0f, 2.0f);
    path1.lineTo(3.0f, 4.0f);

    GeneralPath path2 = new GeneralPath();
    path2.moveTo(1.0f, 2.0f);
    path2.lineTo(3.0f, 5.0f);

    assertFalse(ShapeUtilities.equal(path1, path2));
}

@Test
public void testEqual_sameGeometryConstructedSeparately_returnsTrue() {
    GeneralPath path1 = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
    path1.moveTo(-1.5f, 2.5f);
    path1.lineTo(0.0f, 0.0f);
    path1.closePath();

    GeneralPath path2 = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
    path2.moveTo(-1.5f, 2.5f);
    path2.lineTo(0.0f, 0.0f);
    path2.closePath();

    assertTrue(ShapeUtilities.equal(path1, path2));
}
