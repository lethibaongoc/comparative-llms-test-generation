@Test
public void testContainsAny_matchingBasicCharacter() {
    assertTrue(StringUtils.containsAny("hello", new char[]{'x', 'e'}));
}

@Test
public void testContainsAny_noMatchingCharacter() {
    assertFalse(StringUtils.containsAny("hello", new char[]{'x', 'y'}));
}

@Test
public void testContainsAny_emptyString() {
    assertFalse(StringUtils.containsAny("", new char[]{'a'}));
}

@Test
public void testContainsAny_nullString() {
    assertFalse(StringUtils.containsAny(null, new char[]{'a'}));
}

@Test
public void testContainsAny_emptySearchArray() {
    assertFalse(StringUtils.containsAny("hello", new char[]{}));
}

@Test
public void testContainsAny_nullSearchArray() {
    assertFalse(StringUtils.containsAny("hello", null));
}

@Test
public void testContainsAny_highSurrogateWithMatchingLowSurrogate() {
    String value = "😀";
    assertTrue(StringUtils.containsAny(value, new char[]{'\uD83D', '\uDE00'}));
}

@Test
public void testContainsAny_highSurrogateWithNonMatchingLowSurrogate() {
    String value = "😀";
    assertFalse(StringUtils.containsAny(value, new char[]{'\uD83D', '\uDE01'}));
}

@Test
public void testContainsAny_highSurrogateAsLastSearchCharacter() {
    String value = "😀";
    assertTrue(StringUtils.containsAny(value, new char[]{'\uD83D'}));
}

@Test
public void testContainsAny_matchesLowSurrogateIndependently() {
    String value = "😀";
    assertTrue(StringUtils.containsAny(value, new char[]{'\uDE00'}));
}
