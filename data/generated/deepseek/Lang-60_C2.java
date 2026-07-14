@Test
void testContainsWithCharPresent() {
    StrBuilder sb = new StrBuilder("hello");
    assertTrue(sb.contains('e'));
    assertTrue(sb.contains('h'));
    assertTrue(sb.contains('o'));
}

@Test
void testContainsWithCharNotPresent() {
    StrBuilder sb = new StrBuilder("hello");
    assertFalse(sb.contains('z'));
    assertFalse(sb.contains('a'));
}

@Test
void testContainsWithEmptyBuilder() {
    StrBuilder sb = new StrBuilder();
    assertFalse(sb.contains('a'));
    assertFalse(sb.contains(' '));
}

@Test
void testContainsWithSingleCharBuilder() {
    StrBuilder sb = new StrBuilder("x");
    assertTrue(sb.contains('x'));
    assertFalse(sb.contains('y'));
}

@Test
void testContainsWithWhitespace() {
    StrBuilder sb = new StrBuilder("hello world");
    assertTrue(sb.contains(' '));
    assertTrue(sb.contains('d'));
}

@Test
void testContainsWithSpecialCharacters() {
    StrBuilder sb = new StrBuilder("!@#$%");
    assertTrue(sb.contains('@'));
    assertTrue(sb.contains('#'));
    assertFalse(sb.contains('&'));
}

@Test
void testContainsWithDuplicateCharacters() {
    StrBuilder sb = new StrBuilder("banana");
    assertTrue(sb.contains('a'));
    assertTrue(sb.contains('n'));
    assertFalse(sb.contains('x'));
}
