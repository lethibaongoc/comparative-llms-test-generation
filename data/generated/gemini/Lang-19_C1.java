@Test
void testNoMatchAtStart() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    int result = unescaper.translate("abc", 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testIncompletePrefix() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    // Too short (index < seqEnd - 2 condition fails)
    int result = unescaper.translate("&#", 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testHexWithoutDigits() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    // Starts with &#x but nothing follows
    int result = unescaper.translate("&#x", 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testDecimalWithSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    // 65 is 'A'
    int result = unescaper.translate("&#65;", 0, out);
    assertEquals(5, result); // 2 (&#) + 2 (65) + 1 (;) = 5
    assertEquals("A", out.toString());
}

@Test
void testDecimalWithoutSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    int result = unescaper.translate("&#65", 0, out);
    assertEquals(4, result); // 2 (&#) + 2 (65) = 4
    assertEquals("A", out.toString());
}

@Test
void testHexWithSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    // 41 hex is 65 ('A')
    int result = unescaper.translate("&#x41;", 0, out);
    assertEquals(6, result); // 2 (&#) + 1 (x) + 2 (41) + 1 (;) = 6
    assertEquals("A", out.toString());
}

@Test
void testHexCaseInsensitive() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    // Big X and mixed-case hex digits
    int result = unescaper.translate("&#X4a;", 0, out);
    assertEquals(6, result); // 2 (&#) + 1 (X) + 2 (4a) + 1 (;) = 6
    assertEquals("J", out.toString());
}

@Test
void testSupplementaryUnicodeCharacter() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    // 119070 in decimal is U+1D11E (G-clef musical symbol, outside BMP)
    int result = unescaper.translate("&#119070;", 0, out);
    assertEquals(9, result); // 2 (&#) + 6 (119070) + 1 (;) = 9
    assertEquals("𝄞", out.toString());
}

@Test
void testInvalidNumberFormat() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    // Invalid characters directly after prefix (fails to parse)
    int result = unescaper.translate("&#xyz", 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}
