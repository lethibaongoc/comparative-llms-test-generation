@Test
public void testTranslate_decimalWithSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#65;", 0, out);

    assertEquals(5, consumed);
    assertEquals("A", out.toString());
}

@Test
public void testTranslate_hexadecimalLowercaseWithSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#x41;", 0, out);

    assertEquals(6, consumed);
    assertEquals("A", out.toString());
}

@Test
public void testTranslate_hexadecimalUppercasePrefix() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#X42;", 0, out);

    assertEquals(6, consumed);
    assertEquals("B", out.toString());
}

@Test
public void testTranslate_decimalWithoutSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#67", 0, out);

    assertEquals(4, consumed);
    assertEquals("C", out.toString());
}

@Test
public void testTranslate_stopsAtFirstNonNumericCharacter() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#68abc", 0, out);

    assertEquals(4, consumed);
    assertEquals("D", out.toString());
}

@Test
public void testTranslate_supplementaryUnicodeCharacter() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#128512;", 0, out);

    assertEquals(9, consumed);
    assertEquals("😀", out.toString());
}

@Test
public void testTranslate_invalidNumericEntityReturnsZero() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#xyz;", 0, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}

@Test
public void testTranslate_hexPrefixWithoutDigitsReturnsZero() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#x", 0, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}

@Test
public void testTranslate_nonEntityReturnsZero() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("hello", 0, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}

@Test
public void testTranslate_entityAtNonZeroIndex() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("x&#69;", 1, out);

    assertEquals(5, consumed);
    assertEquals("E", out.toString());
}
