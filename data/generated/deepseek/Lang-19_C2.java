@Test
void testTranslateNoAmpersand() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    assertEquals(0, unescaper.translate("Hello World", 0, out));
    assertEquals("", out.toString());
}

@Test
void testTranslateAmpersandWithoutHash() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    assertEquals(0, unescaper.translate("&amp;", 0, out));
    assertEquals("", out.toString());
}

@Test
void testTranslateIncompleteEntityAtEnd() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    assertEquals(0, unescaper.translate("&#", 0, out));
    assertEquals("", out.toString());
}

@Test
void testTranslateHexEntityWithoutX() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x", 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateDecimalEntity() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#65;", 0, out);
    assertEquals(4, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateDecimalEntityWithoutSemicolon() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#65", 0, out);
    assertEquals(3, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateHexEntityLowercase() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x41;", 0, out);
    assertEquals(5, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateHexEntityUppercaseX() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#X41;", 0, out);
    assertEquals(5, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateHexEntityWithoutSemicolon() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x41", 0, out);
    assertEquals(4, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateHexEntityLowercaseLetters() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x7a;", 0, out);
    assertEquals(5, result);
    assertEquals("z", out.toString());
}

@Test
void testTranslateHexEntityUppercaseLetters() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x5A;", 0, out);
    assertEquals(5, result);
    assertEquals("Z", out.toString());
}

@Test
void testTranslateInvalidDecimalEntity() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#999999999999;", 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateInvalidHexEntity() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#xZZZ;", 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateSupplementaryCharacter() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x1F600;", 0, out);
    assertEquals(7, result);
    assertEquals("😀", out.toString());
}

@Test
void testTranslateSupplementaryCharacterWithoutSemicolon() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x1F600", 0, out);
    assertEquals(6, result);
    assertEquals("😀", out.toString());
}

@Test
void testTranslateWithContentBeforeEntity() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("abc&#65;", 0, out);
    assertEquals(4, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateWithContentAfterEntity() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#65;xyz", 0, out);
    assertEquals(4, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateHexEntityWithMixedCase() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x1F;", 0, out);
    assertEquals(5, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateMaximumValidCharacter() throws IOException {
    StringWriter out = new StringWriter();
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    int result = unescaper.translate("&#x10FFFF;", 0, out);
    assertEquals(8, result);
    assertEquals("􏿿", out.toString());
}
