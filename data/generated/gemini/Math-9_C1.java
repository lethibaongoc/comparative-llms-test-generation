```java
@Test
void testRevertWithOriginZeroAndPositiveDirection() {
    // Assuming Point(double x, double y) constructor and Point.subtract(Point) method exist
    // Assuming Line(Point zero, Point direction) constructor and Line.getZero(), Line.getDirection() methods exist
    Point zero = new Point(0, 0);
    Point direction = new Point(1, 0);
    Line line = new Line(zero, direction);

    Line revertedLine = line.revert();

    assertNotNull(revertedLine, "Reverted line should not be null");
    assertEquals(zero, revertedLine.getZero(), "The 'zero' point of the reverted line should be unchanged");
    
    // Expected direction: zero.subtract(direction) = (0-1, 0-0) = (-1, 0)
    Point expectedRevertedDirection = new Point(-1, 0); 
    assertEquals(expectedRevertedDirection, revertedLine.getDirection(), "Reverted direction is incorrect for origin zero");
}

@Test
void testRevertWithNonOriginZeroAndPositiveDirection() {
    Point zero = new Point(1, 1);
    Point direction = new Point(2, 0);
    Line line = new Line(zero, direction);

    Line revertedLine = line.revert();

    assertNotNull(revertedLine, "Reverted line should not be null");
    assertEquals(zero, revertedLine.getZero(), "The 'zero' point of the reverted line should be unchanged");
    
    // Expected direction: zero.subtract(direction) = (1-2, 1-0) = (-1, 1)
    Point expectedRevertedDirection = new Point(-1, 1);
    assertEquals(expectedRevertedDirection, revertedLine.getDirection(), "Reverted direction is incorrect for non-origin zero");
}

@Test
void testRevertWithNegativeDirection() {
    Point zero = new Point(0, 0);
    Point direction = new Point(-1, -1);
    Line line = new Line(zero, direction);

    Line revertedLine = line.revert();

    assertNotNull(revertedLine, "Reverted line should not be null");
    assertEquals(zero, revertedLine.getZero(), "The 'zero' point of the reverted line should be unchanged");
    
    // Expected direction: zero.subtract(direction) = (0 - (-1), 0 - (-1)) = (1, 1)
    Point expectedRevertedDirection = new Point(1, 1);
    assertEquals(expectedRevertedDirection, revertedLine.getDirection(), "Reverted direction is incorrect for negative direction");
}

@Test
void testRevertWithMixedCoordinates() {
    Point zero = new Point(2, -3);
    Point direction = new Point(-1, 4);
    Line line = new Line(zero, direction);

    Line revertedLine = line.revert();

    assertNotNull(revertedLine, "Reverted line should not be null");
    assertEquals(zero, revertedLine.getZero(), "The 'zero' point of the reverted line should be unchanged");
    
    // Expected direction: zero.subtract(direction) = (2 - (-1), -3 - 4) = (3, -7)
    Point expectedRevertedDirection = new Point(3, -7);
    assertEquals(expectedRevertedDirection, revertedLine.getDirection(), "Reverted direction is incorrect for mixed coordinates");
}

@Test
void testRevertWithZeroDirectionVector() {
    Point zero = new Point(5, 5);
    Point direction = new Point(0, 0);
    Line line = new Line(zero, direction);

    Line revertedLine = line.revert();

    assertNotNull(revertedLine, "Reverted line should not be null");
    assertEquals(zero, revertedLine.getZero(), "The 'zero' point of the reverted line should be unchanged");
    
    // Expected direction: zero.subtract(direction) = (5-0, 5-0) = (5, 5)
    Point expectedRevertedDirection = new Point(5, 5);
    assertEquals(expectedRevertedDirection, revertedLine.getDirection(), "Reverted direction is incorrect for zero direction vector");
}

@Test
void testRevertReturnsNewInstance() {
    Point zero = new Point(0, 0);
    Point direction = new Point(1, 0);
    Line originalLine = new Line(zero, direction);

    Line revertedLine = originalLine.revert();

    assertNotNull(revertedLine, "Revert should return a new Line instance");
    assertNotSame(originalLine, revertedLine, "Revert should return a new Line instance, not the original one");
}

@Test
void testRevertDoesNotModifyOriginalLine() {
    Point zero = new Point(0, 0);
    Point direction = new Point(1, 0);
    Line originalLine = new Line(zero, direction);
    
    // Store original state before calling revert
    Point originalZero = originalLine.getZero();
    Point originalDirection = originalLine.getDirection();

    originalLine.revert(); // Call revert, but don't store the result (it should be immutable)

    assertEquals(originalZero, originalLine.getZero(), "Original line's zero point should not be modified after revert");
    assertEquals(originalDirection, originalLine.getDirection(), "Original line's direction should not be modified after revert");
}
```