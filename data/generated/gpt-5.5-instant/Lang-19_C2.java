@Test
void testTranslateDecimalEntity() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#65;", 0, out);

    assertEquals(5, consumed);
    assertEquals("A", out.toString());
}

@Test
void testTranslateHexadecimalEntities() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

    StringWriter lowercaseOut = new StringWriter();
    int lowercaseConsumed = unescaper.translate("&#x41;", 0, lowercaseOut);

    StringWriter uppercaseOut = new StringWriter();
    int uppercaseConsumed = unescaper.translate("&#X42;", 0, uppercaseOut);

    assertEquals(6, lowercaseConsumed);
    assertEquals("A", lowercaseOut.toString());
    assertEquals(6, uppercaseConsumed);
    assertEquals("B", uppercaseOut.toString());
}

@Test
void testTranslateEntityWithoutSemicolon() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#65text", 0, out);

    assertEquals(4, consumed);
    assertEquals("A", out.toString());
}

@Test
void testTranslateSupplementaryUnicodeCharacter() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#128512;", 0, out);

    assertEquals(9, consumed);
    assertEquals("😀", out.toString());
}

@Test
void testTranslateReturnsZeroForNonNumericEntity() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&amp;", 0, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}

@Test
void testTranslateReturnsZeroForInvalidHexEntity() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();

    int consumed = unescaper.translate("&#x;", 0, out);

    assertEquals(0, consumed);
    assertEquals("", out.toString());
}
