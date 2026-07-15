@Test
void testEscapeRegexEscapesSpecialCharacters() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
            "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder("prefix");
    String value = "?[](){}\\|*+^$.";

    StringBuilder result = (StringBuilder) method.invoke(
            null, regex, value, false);

    assertSame(regex, result);
    assertEquals("prefix\\?\\[\\]\\(\\)\\{\\}\\\\\\|\\*\\+\\^\\$\\.", result.toString());
}

@Test
void testEscapeRegexLeavesOrdinaryCharactersUnchanged() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
            "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder result = (StringBuilder) method.invoke(
            null, new StringBuilder(), "abc-123_/:", false);

    assertEquals("abc-123_/:", result.toString());
}

@Test
void testEscapeRegexPreservesQuotesWhenUnquoteIsFalse() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
            "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder result = (StringBuilder) method.invoke(
            null, new StringBuilder(), "'a?'", false);

    assertEquals("'a\\?'", result.toString());
}

@Test
void testEscapeRegexRemovesQuotesWhenUnquoteIsTrue() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
            "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder result = (StringBuilder) method.invoke(
            null, new StringBuilder(), "'a'b", true);

    assertEquals("ab", result.toString());
}

@Test
void testEscapeRegexReturnsImmediatelyForTrailingQuoteWhenUnquoting() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
            "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder("start");
    StringBuilder result = (StringBuilder) method.invoke(
            null, regex, "abc'", true);

    assertSame(regex, result);
    assertEquals("startabc", result.toString());
}

@Test
void testEscapeRegexEscapesCharacterFollowingQuoteWhenUnquoting() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
            "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder result = (StringBuilder) method.invoke(
            null, new StringBuilder(), "'?x", true);

    assertEquals("\\?x", result.toString());
}
