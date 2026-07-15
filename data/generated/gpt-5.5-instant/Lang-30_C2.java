@Test
void testContainsAny() {
    assertFalse(StringUtils.containsAny(null, new char[] {'a'}));
    assertFalse(StringUtils.containsAny("", new char[] {'a'}));
    assertFalse(StringUtils.containsAny("abc", null));
    assertFalse(StringUtils.containsAny("abc", new char[0]));

    assertTrue(StringUtils.containsAny("abc", new char[] {'x', 'b'}));
    assertFalse(StringUtils.containsAny("abc", new char[] {'x', 'y'}));
}

@Test
void testContainsAnyWithSurrogatePair() {
    String text = "😀";
    char highSurrogate = '\uD83D';
    char lowSurrogate = '\uDE00';

    assertTrue(StringUtils.containsAny(text,
            new char[] {highSurrogate, lowSurrogate}));
    assertFalse(StringUtils.containsAny(text,
            new char[] {highSurrogate, '\uDE01'}));
    assertTrue(StringUtils.containsAny(text,
            new char[] {'x', highSurrogate}));
}
