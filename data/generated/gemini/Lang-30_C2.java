@Test
void testContainsAny_EmptyInputs() {
    assertFalse(StringUtils.containsAny(null, null));
    assertFalse(StringUtils.containsAny("", null));
    assertFalse(StringUtils.containsAny(null, new char[0]));
    assertFalse(StringUtils.containsAny("", new char[0]));
    assertFalse(StringUtils.containsAny("abc", new char[0]));
    assertFalse(StringUtils.containsAny("", new char[]{'a', 'b'}));
}

@Test
void testContainsAny_BasicCharacters() {
    assertFalse(StringUtils.containsAny("abc", new char[]{'d', 'e', 'f'}));
    assertTrue(StringUtils.containsAny("abc", new char[]{'c', 'd'}));
    assertTrue(StringUtils.containsAny("abc", new char[]{'a'}));
    assertTrue(StringUtils.containsAny("zzabcyy", new char[]{'b'}));
}

@Test
void testContainsAny_SurrogatePairs() {
    // Supplementary character (e.g., Emoji / non-BMP character like U+1F600, represented as high and low surrogate)
    char highSurrogate = '\uD83D';
    char lowSurrogate = '\uDE00';

    // Case 1: Search character contains high surrogate, but it's at the end of searchChars (missing low surrogate check match)
    assertTrue(StringUtils.containsAny(String.valueOf(highSurrogate), new char[]{highSurrogate}));

    // Case 2: Match with properly matched surrogate pair
    String surrogateStr = "" + highSurrogate + lowSurrogate;
    assertTrue(StringUtils.containsAny(surrogateStr, new char[]{highSurrogate, lowSurrogate}));

    // Case 3: High surrogate matches, but low surrogate does not match the one in the string (though the code has incomplete matching paths)
    assertTrue(StringUtils.containsAny(surrogateStr, new char[]{highSurrogate, '\uDE01'}));
}
