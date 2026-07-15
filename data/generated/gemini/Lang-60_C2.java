@Test
void testContainsCharPresent() {
    StrBuilder sb = new StrBuilder("hello");
    assertTrue(sb.contains('e'));
    assertTrue(sb.contains('h'));
    assertTrue(sb.contains('o'));
}

@Test
void testContainsCharAbsent() {
    StrBuilder sb = new StrBuilder("hello");
    assertFalse(sb.contains('z'));
    assertFalse(sb.contains('a'));
}

@Test
void testContainsEmptyBuilder() {
    StrBuilder sb = new StrBuilder();
    assertFalse(sb.contains('a'));
}

@Test
void testContainsAfterSizeDecrease() {
    StrBuilder sb = new StrBuilder("hello");
    sb.minimizeCapacity(); // Or any action that changes size, but keeping it direct:
    sb.setLength(2); // now "he", 'l' is in buffer but past size
    assertTrue(sb.contains('e'));
    assertFalse(sb.contains('l'));
}
