@Test
public void testMax_firstLessThanSecond() {
    assertEquals(5.5f, FastMath.max(3.2f, 5.5f), 0.0f);
}

@Test
public void testMax_firstGreaterThanSecond() {
    assertEquals(7.8f, FastMath.max(7.8f, 2.1f), 0.0f);
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
public void testMax_negativeZeroAndPositiveZero() {
    assertEquals(0.0f, FastMath.max(-0.0f, 0.0f), 0.0f);
}

@Test
public void testMax_negativeNumbers() {
    assertEquals(-2.0f, FastMath.max(-2.0f, -5.0f), 0.0f);
}
