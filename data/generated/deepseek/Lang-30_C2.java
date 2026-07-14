@Test
void testContainsAny_ShouldReturnFalse_WhenStringIsNull() {
    assertFalse(StringUtils.containsAny(null, new char[]{'a', 'b'}));
}

@Test
void testContainsAny_ShouldReturnFalse_WhenStringIsEmpty() {
    assertFalse(StringUtils.containsAny("", new char[]{'a', 'b'}));
}

@Test
void testContainsAny_ShouldReturnFalse_WhenSearchCharsIsNull() {
    assertFalse(StringUtils.containsAny("abc", null));
}

@Test
void testContainsAny_ShouldReturnFalse_WhenSearchCharsIsEmpty() {
    assertFalse(StringUtils.containsAny("abc", new char[]{}));
}

@Test
void testContainsAny_ShouldReturnTrue_WhenSingleCharMatches() {
    assertTrue(StringUtils.containsAny("abc", new char[]{'b'}));
}

@Test
void testContainsAny_ShouldReturnFalse_WhenNoCharMatches() {
    assertFalse(StringUtils.containsAny("abc", new char[]{'x', 'y', 'z'}));
}

@Test
void testContainsAny_ShouldReturnTrue_WhenMultipleCharsAndOneMatches() {
    assertTrue(StringUtils.containsAny("hello", new char[]{'x', 'e', 'z'}));
}

@Test
void testContainsAny_ShouldReturnTrue_WhenHighSurrogateMatchesWithLowSurrogate() {
    String surrogateString = "𐀀";
    assertTrue(StringUtils.containsAny(surrogateString, new char[]{'\uD800', '\uDC00'}));
}

@Test
void testContainsAny_ShouldReturnTrue_WhenHighSurrogateMatchesWithoutLowSurrogate() {
    String surrogateString = "\uD800X";
    assertTrue(StringUtils.containsAny(surrogateString, new char[]{'\uD800'}));
}

@Test
void testContainsAny_ShouldReturnFalse_WhenHighSurrogateInSearchButNotInString() {
    String surrogateString = "𐀀";
    assertFalse(StringUtils.containsAny(surrogateString, new char[]{'\uD801'}));
}
