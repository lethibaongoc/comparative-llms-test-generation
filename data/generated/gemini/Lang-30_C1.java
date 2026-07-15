@ParameterizedTest
@NullAndEmptySource
void containsAny_ShouldReturnFalse_WhenCharSequenceIsEmptyOrNull(String cs) {
    assertFalse(StringUtils.containsAny(cs, new char[]{'a', 'b'}));
}

@Test
void containsAny_ShouldReturnFalse_WhenSearchCharsIsNull() {
    assertFalse(StringUtils.containsAny("abc", null));
}

@Test
void containsAny_ShouldReturnFalse_WhenSearchCharsIsEmpty() {
    assertFalse(StringUtils.containsAny("abc", new char[]{}));
}

@Test
void containsAny_ShouldReturnFalse_WhenNoMatchFound() {
    assertFalse(StringUtils.containsAny("abc", new char[]{'z', 'y'}));
}

@Test
void containsAny_ShouldReturnTrue_WhenStandardCharMatches() {
    assertTrue(StringUtils.containsAny("abc", new char[]{'z', 'b'}));
}

@Test
void containsAny_ShouldReturnTrue_WhenHighSurrogateMatchesAtEndOfSearchChars() {
    // \uD83D is a high surrogate character
    assertTrue(StringUtils.containsAny("😀", new char[]{'a', '\uD83D'}));
}

@Test
void containsAny_ShouldReturnTrue_WhenHighSurrogateMatchesAndIsFollowedByLowSurrogate() {
    // Tests the branch where i < csLast and surrogate conditions are evaluated
    assertTrue(StringUtils.containsAny("😀", new char[]{'\uD83D', '\uDE00'}));
}

@Test
void containsAny_ShouldReturnFalse_WhenNoCharactersMatchInLongerString() {
    assertFalse(StringUtils.containsAny("abcdefghijklmnopqrstuvwxyz", new char[]{'1', '2', '3'}));
}
