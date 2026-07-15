@Test
void testEquals() {
    MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
    MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();

    // Test reflexive equality
    assertTrue(r1.equals(r1));

    // Test symmetric equality with default values
    assertTrue(r1.equals(r2));
    assertTrue(r2.equals(r1));

    // Test comparison with null and non-matching classes
    assertFalse(r1.equals(null));
    assertFalse(r1.equals("not-a-renderer"));

    // Test plotLines inequality
    r1.setDrawLines(true);
    r2.setDrawLines(false);
    assertFalse(r1.equals(r2));
    r2.setDrawLines(true);
    assertTrue(r1.equals(r2));

    // Test groupPaint inequality
    r1.setGroupPaint(new java.awt.Color(1, 2, 3));
    assertFalse(r1.equals(r2));
    r2.setGroupPaint(new java.awt.Color(1, 2, 3));
    assertTrue(r1.equals(r2));

    // Test groupStroke inequality
    r1.setGroupStroke(new java.awt.BasicStroke(2.0f));
    assertFalse(r1.equals(r2));
    r2.setGroupStroke(new java.awt.BasicStroke(2.0f));
    assertTrue(r1.equals(r2));
}
