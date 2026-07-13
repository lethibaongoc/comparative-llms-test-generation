@Test
void testRevert_positiveDirectionComponents() {
    Point p1 = new Point(1, 2);
    Point p2 = new Point(4, 6);
    Line obj = new Line(p1, p2);

    Line revertedLine = obj.revert();

    assertEquals(Point.zero, revertedLine.getStart());

    Point expectedEnd = new Point(-3, -4);
    assertEquals(expectedEnd, revertedLine.getEnd());

    Vector expectedDirection = new Vector(-3, -4);
    assertEquals(expectedDirection, revertedLine.getDirection());
}

@Test
void testRevert_negativeDirectionComponents() {
    Point p1 = new Point(5, 5);
    Point p2 = new Point(2, 1);
    Line obj = new Line(p1, p2);

    Line revertedLine = obj.revert();

    assertEquals(Point.zero, revertedLine.getStart());

    Point expectedEnd = new Point(3, 4);
    assertEquals(expectedEnd, revertedLine.getEnd());

    Vector expectedDirection = new Vector(3, 4);
    assertEquals(expectedDirection, revertedLine.getDirection());
}

@Test
void testRevert_mixedDirectionComponents() {
    Point p1 = new Point(1, 1);
    Point p2 = new Point(3, -1);
    Line obj = new Line(p1, p2);

    Line revertedLine = obj.revert();

    assertEquals(Point.zero, revertedLine.getStart());

    Point expectedEnd = new Point(-2, 2);
    assertEquals(expectedEnd, revertedLine.getEnd());

    Vector expectedDirection = new Vector(-2, 2);
    assertEquals(expectedDirection, revertedLine.getDirection());
}

@Test
void testRevert_zeroDirection_pointLine() {
    Point p1 = new Point(1, 2);
    Point p2 = new Point(1, 2);
    Line obj = new Line(p1, p2);

    Line revertedLine = obj.revert();

    assertEquals(Point.zero, revertedLine.getStart());

    Point expectedEnd = new Point(0, 0);
    assertEquals(expectedEnd, revertedLine.getEnd());
    assertEquals(Point.zero, revertedLine.getEnd());

    Vector expectedDirection = new Vector(0, 0);
    assertEquals(expectedDirection, revertedLine.getDirection());
}

@Test
void testRevert_originalLineStartsAtOrigin() {
    Point p1 = Point.zero;
    Point p2 = new Point(3, 4);
    Line obj = new Line(p1, p2);

    Line revertedLine = obj.revert();

    assertEquals(Point.zero, revertedLine.getStart());

    Point expectedEnd = new Point(-3, -4);
    assertEquals(expectedEnd, revertedLine.getEnd());

    Vector expectedDirection = new Vector(-3, -4);
    assertEquals(expectedDirection, revertedLine.getDirection());
}
