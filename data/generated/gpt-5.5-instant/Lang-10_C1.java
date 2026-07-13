@Test
public void testEscapeRegex_escapesRegexMetacharacters() throws Exception {
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
public void testEscapeRegex_keepsNormalCharactersUnchanged() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder();
    StringBuilder result = (StringBuilder) method.invoke(null, regex, "abc123-_", false);

    assertEquals("abc123-_", result.toString());
}

@Test
public void testEscapeRegex_unquoteTrue_removesQuoteAndProcessesNextCharacter() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder();
    StringBuilder result = (StringBuilder) method.invoke(null, regex, "'?abc", true);

    assertEquals("\\?abc", result.toString());
}

@Test
public void testEscapeRegex_unquoteTrue_trailingQuoteStopsProcessing() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder("prefix");
    StringBuilder result = (StringBuilder) method.invoke(null, regex, "abc'", true);

    assertSame(regex, result);
    assertEquals("prefixabc", result.toString());
}

@Test
public void testEscapeRegex_unquoteFalse_preservesSingleQuote() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder();
    StringBuilder result = (StringBuilder) method.invoke(null, regex, "a'b", false);

    assertEquals("a'b", result.toString());
}

@Test
public void testEscapeRegex_appendsToExistingBuilderContent() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder("^");
    StringBuilder result = (StringBuilder) method.invoke(null, regex, "a.b", false);

    assertEquals("^a\\.b", result.toString());
}

@Test
public void testEscapeRegex_emptyValueReturnsSameBuilder() throws Exception {
    java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
        "escapeRegex", StringBuilder.class, String.class, boolean.class);
    method.setAccessible(true);

    StringBuilder regex = new StringBuilder("existing");
    StringBuilder result = (StringBuilder) method.invoke(null, regex, "", true);

    assertSame(regex, result);
    assertEquals("existing", result.toString());
}
