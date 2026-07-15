@Test
void testContainsCharPresentAtStart() {
    StrBuilder sb = new StrBuilder("apple");
    assertTrue(sb.contains('a'));
}

@Test
void testContainsCharPresentInMiddle() {
    StrBuilder sb = new StrBuilder("apple");
    assertTrue(sb.contains('p'));
}

@Test
void testContainsCharPresentAtEnd() {
    StrBuilder sb = new StrBuilder("apple");
    assertTrue(sb.contains('e'));
}

@Test
void testContainsCharNotPresent() {
    StrBuilder sb = new StrBuilder("apple");
    assertFalse(sb.contains('z'));
}

@Test
void testContainsCharInEmptyBuilder() {
    StrBuilder sb = new StrBuilder("");
    assertFalse(sb.contains('a'));
}

@Test
void testContainsCaseSensitivity() {
    StrBuilder sb = new StrBuilder("Apple");
    assertTrue(sb.contains('A'));
    assertFalse(sb.contains('a'));
}

@Test
void testContainsWithSpecialCharacters() {
    StrBuilder sb = new StrBuilder("hello-world!");
    assertTrue(sb.contains('-'));
    assertTrue(sb.contains('!'));
}
