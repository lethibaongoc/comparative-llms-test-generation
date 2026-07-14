@Test
void contains_shouldReturnTrue_whenCharacterExists() {
    StrBuilder strBuilder = new StrBuilder("hello");
    assertTrue(strBuilder.contains('e'));
}

@Test
void contains_shouldReturnFalse_whenCharacterDoesNotExist() {
    StrBuilder strBuilder = new StrBuilder("hello");
    assertFalse(strBuilder.contains('z'));
}

@Test
void contains_shouldReturnFalse_whenStringIsEmpty() {
    StrBuilder strBuilder = new StrBuilder("");
    assertFalse(strBuilder.contains('a'));
}

@Test
void contains_shouldReturnTrue_whenCharacterIsFirst() {
    StrBuilder strBuilder = new StrBuilder("java");
    assertTrue(strBuilder.contains('j'));
}

@Test
void contains_shouldReturnTrue_whenCharacterIsLast() {
    StrBuilder strBuilder = new StrBuilder("java");
    assertTrue(strBuilder.contains('a'));
}

@Test
void contains_shouldHandleSpecialCharacters() {
    StrBuilder strBuilder = new StrBuilder("!@#$");
    assertTrue(strBuilder.contains('@'));
    assertFalse(strBuilder.contains('&'));
}

@Test
void contains_shouldHandleWhitespaceCharacters() {
    StrBuilder strBuilder = new StrBuilder("hello world");
    assertTrue(strBuilder.contains(' '));
    assertFalse(strBuilder.contains('\t'));
}
