@Test
@DisplayName("revert() should return a new Line with the same zero point and the subtracted direction")
void testRevertConstructsExpectedLine() {
    // Arrange
    // Assuming 'zero' and 'direction' are of a type that supports .subtract(), like a Vector or Point class
    Vector zeroMock = mock(Vector.class);
    Vector directionMock = mock(Vector.class);
    Vector subtractedDirectionMock = mock(Vector.class);

    when(zeroMock.subtract(directionMock)).thenReturn(subtractedDirectionMock);

    Line line = new Line(zeroMock, directionMock);

    // Act
    Line reverted = line.revert();

    // Assert
    assertNotNull(reverted, "Reverted line should not be null");
    assertEquals(zeroMock, reverted.getZero(), "The origin (zero) point should remain identical");
    assertEquals(subtractedDirectionMock, reverted.getDirection(), "The new direction should be the result of zero.subtract(direction)");
}

@Test
@DisplayName("revert() should create a distinct Line instance and not modify the original")
void testRevertImmutability() {
    // Arrange
    Vector zeroMock = mock(Vector.class);
    Vector directionMock = mock(Vector.class);
    Vector subtractedDirectionMock = mock(Vector.class);

    when(zeroMock.subtract(directionMock)).thenReturn(subtractedDirectionMock);

    Line originalLine = new Line(zeroMock, directionMock);

    // Act
    Line revertedLine = originalLine.revert();

    // Assert
    assertNotSame(originalLine, revertedLine, "revert() must return a brand new Line instance");
    assertEquals(zeroMock, originalLine.getZero(), "Original line's zero point must remain unchanged");
    assertEquals(directionMock, originalLine.getDirection(), "Original line's direction must remain unchanged");
}
