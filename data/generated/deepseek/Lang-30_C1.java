@Test
void testContainsAny_WithNullString_ReturnsFalse() {
    assertFalse(StringUtils.containsAny(null, new char[]{'a', 'b'}));
}

@Test
void testContainsAny_WithEmptyString_ReturnsFalse() {
    assertFalse(StringUtils.containsAny("", new char[]{'a', 'b'}));
}

@Test
void testContainsAny_WithNullSearchChars_ReturnsFalse() {
    assertFalse(StringUtils.containsAny("abc", null));
}

@Test
void testContainsAny_WithEmptySearchChars_ReturnsFalse() {
    assertFalse(StringUtils.containsAny("abc", new char[]{}));
}

@Test
void testContainsAny_WithMatchingChar_ReturnsTrue() {
    assertTrue(StringUtils.containsAny("abc", new char[]{'b', 'd'}));
}

@Test
void testContainsAny_WithMatchingCharAtBeginning_ReturnsTrue() {
    assertTrue(StringUtils.containsAny("abc", new char[]{'a', 'z'}));
}

@Test
void testContainsAny_WithMatchingCharAtEnd_ReturnsTrue() {
    assertTrue(StringUtils.containsAny("abc", new char[]{'c', 'z'}));
}

@Test
void testContainsAny_WithNoMatchingChars_ReturnsFalse() {
    assertFalse(StringUtils.containsAny("abc", new char[]{'x', 'y', 'z'}));
}

@Test
void testContainsAny_WithHighSurrogateAndMatchingLowSurrogate_ReturnsTrue() {
    String text = "abc𐀀def";
    assertTrue(StringUtils.containsAny(text, new char[]{'\uD800', '\uDC00'}));
}

@Test
void testContainsAny_WithHighSurrogateAndNoLowSurrogateInSearch_ReturnsTrue() {
    String text = "abc\uD800def";
    assertTrue(StringUtils.containsAny(text, new char[]{'\uD800', 'x'}));
}

@Test
void testContainsAny_WithHighSurrogateAtEndAndNoLowSurrogate_ReturnsTrue() {
    String text = "abc\uD800";
    assertTrue(StringUtils.containsAny(text, new char[]{'\uD800', 'x'}));
}

@Test
void testContainsAny_WithHighSurrogateButNoLowSurrogateInString_ReturnsFalse() {
    String text = "abc\uD800def";
    assertFalse(StringUtils.containsAny(text, new char[]{'\uD800', '\uDC01'}));
}

@Test
void testContainsAny_WithHighSurrogateAndMatchingLowSurrogateInString_ReturnsTrue() {
    String text = "abc𐀀def";
    assertTrue(StringUtils.containsAny(text, new char[]{'\uD800', '\uDC00'}));
}

@Test
void testContainsAny_WithDuplicateSearchChars_ReturnsTrue() {
    assertTrue(StringUtils.containsAny("abc", new char[]{'x', 'b', 'b', 'y'}));
}

@Test
void testContainsAny_WithSingleCharStringAndMatching_ReturnsTrue() {
    assertTrue(StringUtils.containsAny("a", new char[]{'a'}));
}

@Test
void testContainsAny_WithSingleCharStringAndNotMatching_ReturnsFalse() {
    assertFalse(StringUtils.containsAny("a", new char[]{'b'}));
}

@Test
void testContainsAny_WithUnicodeBeyondBMP_ReturnsTrue() {
    String text = "abc𐀀def";
    assertTrue(StringUtils.containsAny(text, new char[]{'\uD800', '\uDC00'}));
}
