@Test
public void testTranslate_decimalEntityWithSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#65;", 0, out);

    assertEquals(5, consumed);
    assertEquals("A", out.toString());
}

@Test
public void testTranslate_decimalEntityWithoutSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#66", 0, out);

    assertEquals(4, consumed);
    assertEquals("B", out.toString());
}

@Test
public void testTranslate_lowercaseHexEntity() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#x41;", 0, out);

    assertEquals(6, consumed);
    assertEquals("A", out.toString());
}

@Test
public void testTranslate_uppercaseHexEntity() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#X42;", 0, out);

    assertEquals(6, consumed);
    assertEquals("B", out.toString());
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
public void testTranslate_entityAtNonZeroIndex() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("abc&#67;xyz", 3, out);

    assertEquals(5, consumed);
    assertEquals("C", out.toString());
}

@Test
public void testTranslate_invalidDecimalEntity() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#;", 0, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}

@Test
public void testTranslate_hexPrefixWithoutDigits() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#x", 0, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}

@Test
public void testTranslate_nonNumericEntity() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&amp;", 0, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}

@Test
public void testTranslate_indexDoesNotPointToAmpersand() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("abc", 1, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}

@Test
public void testTranslate_stopsAtNonNumericCharacter() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#65xyz", 0, out);

    assertEquals(4, consumed);
    assertEquals("A", out.toString());
}
