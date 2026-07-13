@Test
public void testRevert_returnsLineWithSameOriginAndOppositeDirection() {
    Vector2D origin = new Vector2D(1.0, 2.0);
    Vector2D second = new Vector2D(4.0, 6.0);
    Line line = new Line(origin, second);

    Line reverted = line.revert();

    assertEquals(origin.distance(line.getOrigin()), reverted.getOrigin().distance(origin), 1e-10);
    assertEquals(line.getOrigin().distance(second), reverted.getOrigin().distance(reverted.getOrigin().subtract(reverted.getDirection())), 1e-10);
    assertEquals(-1.0, line.getDirection().dotProduct(reverted.getDirection()), 1e-10);
}

@Test
public void testRevert_twiceReturnsOriginalDirection() {
    Line line = new Line(new Vector2D(0.0, 0.0), new Vector2D(5.0, 0.0));

    Line revertedTwice = line.revert().revert();

    assertEquals(1.0, line.getDirection().dotProduct(revertedTwice.getDirection()), 1e-10);
    assertEquals(0.0, line.getOrigin().distance(revertedTwice.getOrigin()), 1e-10);
}

@Test
public void testRevert_returnsNewInstance() {
    Line line = new Line(new Vector2D(-2.0, 3.0), new Vector2D(1.0, 7.0));

    Line reverted = line.revert();

    assertNotSame(line, reverted);
}
