@Test
void testRevertCreatesLineWithNegatedDirection() {
    Vector3D zero = new Vector3D(1.0, 2.0, 3.0);
    Vector3D directionPoint = new Vector3D(4.0, 6.0, 8.0);
    Line line = new Line(zero, directionPoint);

    Line reverted = line.revert();

    assertEquals(zero, reverted.getOrigin());
    assertEquals(line.getDirection().negate(), reverted.getDirection());
}

@Test
void testRevertTwiceReturnsEquivalentDirection() {
    Vector3D zero = new Vector3D(1.0, 0.0, 0.0);
    Vector3D directionPoint = new Vector3D(2.0, 1.0, 0.0);
    Line line = new Line(zero, directionPoint);

    Line revertedTwice = line.revert().revert();

    assertEquals(line.getOrigin(), revertedTwice.getOrigin());
    assertEquals(line.getDirection(), revertedTwice.getDirection());
}
