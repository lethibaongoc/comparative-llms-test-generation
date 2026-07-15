@Test
void testNoSpecialCharacters() {
    StringBuilder regex = new StringBuilder();
    StringBuilder result = invokeEscapeRegex(regex, "abc123", false);
    assertEquals("abc123", result.toString());
}

@Test
void testRegexSpecialCharactersAreEscaped() {
    String specialChars = "?[](){}\\|*+^$.";
    for (int i = 0; i < specialChars.length(); i++) {
        char c = specialChars.charAt(i);
        StringBuilder regex = new StringBuilder();
        StringBuilder result = invokeEscapeRegex(regex, String.valueOf(c), false);
        assertEquals("\\" + c, result.toString());
    }
}

@Test
void testSingleQuoteWithoutUnquote() {
    StringBuilder regex = new StringBuilder();
    StringBuilder result = invokeEscapeRegex(regex, "a'b", false);
    assertEquals("a'b", result.toString());
}

@Test
void testSingleQuoteWithUnquoteActive() {
    StringBuilder regex = new StringBuilder();
    // The single quote should be skipped, and the following character ('b') is treated normally.
    StringBuilder result = invokeEscapeRegex(regex, "a'b", true);
    assertEquals("ab", result.toString());
}

@Test
void testSingleQuoteAtEndWithUnquoteActive() {
    StringBuilder regex = new StringBuilder();
    // The single quote is at the very end. The method increments the pointer,
    // matches value.length(), and returns immediately.
    StringBuilder result = invokeEscapeRegex(regex, "a'", true);
    assertEquals("a", result.toString());
}

@Test
void testEscapedRegexSpecialCharacterFollowedBySingleQuoteUnquoted() {
    StringBuilder regex = new StringBuilder();
    // In "a'.", the '.' is skipped because of the unquote logic, leaving just "a."
    // However, the '.' is now the char assigned to 'c', so it goes to the switch block.
    // It should be escaped as "\\.".
    StringBuilder result = invokeEscapeRegex(regex, "a'.", true);
    assertEquals("a\\.", result.toString());
}

@Test
void testConsecutiveSingleQuotesWithUnquoteActive() {
    StringBuilder regex = new StringBuilder();
    // First single quote skips. Next char is also a single quote, which gets treated as literal.
    // It does not hit the single quote switch block logic recursively because the outer loop counter 'i' is advanced.
    StringBuilder result = invokeEscapeRegex(regex, "a''b", true);
    assertEquals("a'b", result.toString());
}

@Test
void testAppendsToExistingStringBuilderContent() {
    StringBuilder regex = new StringBuilder("prefix_");
    StringBuilder result = invokeEscapeRegex(regex, "a.b", false);
    assertEquals("prefix_a\\.b", result.toString());
}

// Helper method to access the private static method via reflection
private static StringBuilder invokeEscapeRegex(StringBuilder regex, String value, boolean unquote) {
    try {
        java.lang.reflect.Method method = FastDateParser.class.getDeclaredMethod(
            "escapeRegex", StringBuilder.class, String.class, boolean.class
        );
        method.setAccessible(true);
        return (StringBuilder) method.invoke(null, regex, value, unquote);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
