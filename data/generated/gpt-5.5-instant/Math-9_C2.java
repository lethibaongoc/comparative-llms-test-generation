@Test
public void testRevert_reversesDirection() {
    Vector2D zero = new Vector2D(1.0, 2.0);
    Vector2D direction = new Vector2D(3.0, 4.0);
    Line line = new Line(zero, direction);

    Line reverted = line.revert();

    assertEquals(zero, reverted.getZero());
    assertEquals(zero.subtract(direction), reverted.getDirection());
}

@Test
public void testRevert_twiceReturnsOriginalLine() {
    Vector2D zero = new Vector2D(-2.0, 5.0);
    Vector2D direction = new Vector2D(7.0, -1.0);
    Line line = new Line(zero, direction);

    Line revertedTwice = line.revert().revert();

    assertEquals(line.getZero(), revertedTwice.getZero());
    assertEquals(line.getDirection(), revertedTwice.getDirection());
}
