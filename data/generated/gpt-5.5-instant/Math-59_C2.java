@Test
public void testMax_firstGreater() {
    assertEquals(5.5f, FastMath.max(5.5f, 2.3f), 0.0f);
}

@Test
public void testMax_secondGreater() {
    assertEquals(7.2f, FastMath.max(3.4f, 7.2f), 0.0f);
}

@Test
public void testMax_equalValues() {
    assertEquals(4.0f, FastMath.max(4.0f, 4.0f), 0.0f);
}

@Test
public void testMax_firstNaN() {
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, 1.0f)));
}

@Test
public void testMax_secondNaN() {
    assertTrue(Float.isNaN(FastMath.max(1.0f, Float.NaN)));
}

@Test
public void testMax_bothNaN() {
    assertTrue(Float.isNaN(FastMath.max(Float.NaN, Float.NaN)));
}

@Test
public void testMax_negativeValues() {
    assertEquals(-2.0f, FastMath.max(-5.0f, -2.0f), 0.0f);
}

@Test
public void testMax_positiveInfinity() {
    assertEquals(Float.POSITIVE_INFINITY, FastMath.max(Float.POSITIVE_INFINITY, 100.0f), 0.0f);
}
