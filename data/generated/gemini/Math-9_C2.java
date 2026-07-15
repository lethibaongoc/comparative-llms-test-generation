@Test
void testRevertDirection() {
    Vector3D p1 = new Vector3D(1.0, 2.0, 3.0);
    Vector3D p2 = new Vector3D(4.0, 5.0, 6.0);
    Line line = new Line(p1, p2, 1.0e-10);
    Line reverted = line.revert();

    // The direction of the reverted line must be opposite to the original direction
    assertEquals(0.0, line.getDirection().add(reverted.getDirection()).getNorm(), 1.0e-15);
}

@Test
void testRevertOriginAndPoints() {
    Vector3D p1 = new Vector3D(-1.5, 2.5, 3.0);
    Vector3D p2 = new Vector3D(1.5, -2.5, 4.0);
    Line line = new Line(p1, p2, 1.0e-10);
    Line reverted = line.revert();

    // The origin (the point on the line closest to the coordinate origin) must remain the same
    assertEquals(0.0, line.getOrigin().distance(reverted.getOrigin()), 1.0e-15);

    // The reverted line must still geometrically contain the original points
    assertTrue(reverted.contains(p1));
    assertTrue(reverted.contains(p2));
}
