@Test
void testEscapeRegex_NoSpecialCharacters() {
    StringBuilder sb = new StringBuilder();
    FastDateParser.escapeRegex(sb, "hello", false);
    assertEquals("hello", sb.toString());
}

@Test
void testEscapeRegex_WithRegexSpecialCharacters() {
    StringBuilder sb = new StringBuilder();
    FastDateParser.escapeRegex(sb, "a.b*c+d?e^f$g(h)i[j]k{l}m|n\\o", false);
    assertEquals("a\\.b\\*c\\+d\\?e\\^f\\$g\\(h\\)i\\[j\\]k\\{l\\}m\\|n\\\\o", sb.toString());
}

@Test
void testEscapeRegex_SingleQuoteWithoutUnquote() {
    StringBuilder sb = new StringBuilder();
    FastDateParser.escapeRegex(sb, "a'b", false);
    assertEquals("a'b", sb.toString());
}

@Test
void testEscapeRegex_SingleQuoteWithUnquote() {
    StringBuilder sb = new StringBuilder();
    FastDateParser.escapeRegex(sb, "a''b", true);
    assertEquals("a'b", sb.toString());
}

@Test
void testEscapeRegex_TrailingSingleQuoteWithUnquote() {
    StringBuilder sb = new StringBuilder();
    FastDateParser.escapeRegex(sb, "a'", true);
    assertEquals("a", sb.toString());
}
