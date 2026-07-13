@Test
public void testContainsAny_nullString_returnsFalse() {
    assertFalse(StringUtils.containsAny(null, new char[] {'a'}));
}

@Test
public void testContainsAny_emptyString_returnsFalse() {
    assertFalse(StringUtils.containsAny("", new char[] {'a'}));
}

@Test
public void testContainsAny_nullSearchChars_returnsFalse() {
    assertFalse(StringUtils.containsAny("abc", null));
}

@Test
public void testContainsAny_emptySearchChars_returnsFalse() {
    assertFalse(StringUtils.containsAny("abc", new char[0]));
}

@Test
public void testContainsAny_matchingBasicCharacter_returnsTrue() {
    assertTrue(StringUtils.containsAny("hello", new char[] {'x', 'e'}));
}

@Test
public void testContainsAny_noMatchingCharacter_returnsFalse() {
    assertFalse(StringUtils.containsAny("hello", new char[] {'x', 'y', 'z'}));
}

@Test
public void testContainsAny_matchAtLastCharacter_returnsTrue() {
    assertTrue(StringUtils.containsAny("hello", new char[] {'o'}));
}

@Test
public void testContainsAny_highSurrogateAsLastSearchCharacter_returnsTrue() {
    char highSurrogate = '\uD83D';
    assertTrue(StringUtils.containsAny("😀", new char[] {highSurrogate}));
}

@Test
public void testContainsAny_completeSurrogatePair_returnsTrue() {
    assertTrue(StringUtils.containsAny(
        "😀",
        new char[] {'\uD83D', '\uDE00'}));
}

@Test
public void testContainsAny_highSurrogateWithWrongLowSurrogate_returnsFalse() {
    assertFalse(StringUtils.containsAny(
        "😀",
        new char[] {'\uD83D', '\uDE01'}));
}

@Test
public void testContainsAny_highSurrogateAtEndOfInputWithExpectedLowSurrogate_returnsFalse() {
    assertFalse(StringUtils.containsAny(
        "a\uD83D",
        new char[] {'\uD83D', '\uDE00'}));
}

@Test
public void testContainsAny_lowSurrogateAlone_returnsTrue() {
    assertTrue(StringUtils.containsAny(
        "😀",
        new char[] {'\uDE00'}));
}
