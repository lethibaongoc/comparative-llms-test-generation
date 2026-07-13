@Test
public void testEscapeRegex_escapesAllSpecialCharacters() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder();
    String value = "?[](){}\\|*+^$.";

    StringBuilder result = (StringBuilder) method.invoke(null, regex, value, false);

    assertSame(regex, result);
    assertEquals("\\?\\[\\]\\(\\)\\{\\}\\\\\\|\\*\\+\\^\\$\\.", result.toString());
}

@Test
public void testEscapeRegex_appendsNormalCharactersWithoutEscaping() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder("prefix-");

    StringBuilder result = (StringBuilder) method.invoke(null, regex, "abc123", false);

    assertSame(regex, result);
    assertEquals("prefix-abc123", result.toString());
}

@Test
public void testEscapeRegex_preservesQuoteWhenUnquoteIsFalse() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder();

    StringBuilder result = (StringBuilder) method.invoke(null, regex, "ab'cd", false);

    assertEquals("ab'cd", result.toString());
}

@Test
public void testEscapeRegex_unquotesCharacterFollowingQuote() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder();

    StringBuilder result = (StringBuilder) method.invoke(null, regex, "ab'cd", true);

    assertEquals("abcd", result.toString());
}

@Test
public void testEscapeRegex_unquotedSpecialCharacterIsNotEscaped() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder();

    StringBuilder result = (StringBuilder) method.invoke(null, regex, "'?", true);

    assertEquals("?", result.toString());
}

@Test
public void testEscapeRegex_trailingQuoteWithUnquoteReturnsImmediately() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder("start");

    StringBuilder result = (StringBuilder) method.invoke(null, regex, "abc'", true);

    assertSame(regex, result);
    assertEquals("startabc", result.toString());
}

@Test
public void testEscapeRegex_emptyValueLeavesBuilderUnchanged() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder("existing");

    StringBuilder result = (StringBuilder) method.invoke(null, regex, "", true);

    assertSame(regex, result);
    assertEquals("existing", result.toString());
}
