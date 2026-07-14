@Test
void testContainsAnyWithNullString() {
    assertFalse(StringUtils.containsAny(null, new char[]{'a'}));
}

@Test
void testContainsAnyWithEmptyString() {
    assertFalse(StringUtils.containsAny("", new char[]{'a'}));
}

@Test
void testContainsAnyWithNullSearchChars() {
    assertFalse(StringUtils.containsAny("test", null));
}

@Test
void testContainsAnyWithEmptySearchChars() {
    assertFalse(StringUtils.containsAny("test", new char[]{}));
}

@Test
void testContainsAnyWithSingleCharMatch() {
    assertTrue(StringUtils.containsAny("hello", new char[]{'e'}));
}

@Test
void testContainsAnyWithSingleCharNoMatch() {
    assertFalse(StringUtils.containsAny("hello", new char[]{'z'}));
}

@Test
void testContainsAnyWithMultipleCharsMatch() {
    assertTrue(StringUtils.containsAny("hello", new char[]{'a', 'e', 'i'}));
}

@Test
void testContainsAnyWithMultipleCharsNoMatch() {
    assertFalse(StringUtils.containsAny("hello", new char[]{'a', 'b', 'c'}));
}

@Test
void testContainsAnyWithBasicMultilingualPlaneCharacters() {
    assertTrue(StringUtils.containsAny("abc123", new char[]{'1', '2', '3'}));
    assertTrue(StringUtils.containsAny("abc123", new char[]{'a', 'b', 'c'}));
}

@Test
void testContainsAnyWithSurrogatePairMatch() {
    String surrogateString = "a😀b";
    assertTrue(StringUtils.containsAny(surrogateString, new char[]{'\uD83D', '\uDE00'}));
}

@Test
void testContainsAnyWithHighSurrogateOnly() {
    String stringWithHighSurrogate = "a\uD83Db";
    assertTrue(StringUtils.containsAny(stringWithHighSurrogate, new char[]{'\uD83D'}));
}

@Test
void testContainsAnyWithHighSurrogateAndMissingLowSurrogate() {
    String stringWithHighSurrogate = "a\uD83Db";
    assertTrue(StringUtils.containsAny(stringWithHighSurrogate, new char[]{'\uD83D', 'x'}));
}

@Test
void testContainsAnyWithSurrogatePairAndSearchCharMatchAtEnd() {
    String surrogateString = "a😀";
    assertTrue(StringUtils.containsAny(surrogateString, new char[]{'\uD83D', '\uDE00'}));
}

@Test
void testContainsAnyWithIncompleteSurrogatePairAtEnd() {
    String incompleteSurrogateString = "a\uD83D";
    assertTrue(StringUtils.containsAny(incompleteSurrogateString, new char[]{'\uD83D'}));
}
