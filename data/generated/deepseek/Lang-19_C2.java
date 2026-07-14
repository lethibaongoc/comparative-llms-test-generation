@Test
public void testTranslate_basicDecimalEntity() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#65;", 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(4, result);
}

@Test
public void testTranslate_basicHexEntity() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#x41;", 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(5, result);
}

@Test
public void testTranslate_uppercaseHexX() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#X41;", 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(5, result);
}

@Test
public void testTranslate_withoutSemicolon() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#65", 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(3, result);
}

@Test
public void testTranslate_hexWithoutSemicolon() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#x41", 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(4, result);
}

@Test
public void testTranslate_supplementaryCharacter() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#x1F600;", 0, writer);
    assertEquals("😀", writer.toString());
    assertEquals(8, result);
}

@Test
public void testTranslate_noMatchNotAmpersand() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("ABC", 0, writer);
    assertEquals("", writer.toString());
    assertEquals(0, result);
}

@Test
public void testTranslate_ampersandWithoutHash() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&ABC", 0, writer);
    assertEquals("", writer.toString());
    assertEquals(0, result);
}

@Test
public void testTranslate_incompleteEntityTooShort() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#", 0, writer);
    assertEquals("", writer.toString());
    assertEquals(0, result);
}

@Test
public void testTranslate_hexWithJustX() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#x;", 0, writer);
    assertEquals("", writer.toString());
    assertEquals(0, result);
}

@Test
public void testTranslate_invalidNumberFormat() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#ABC;", 0, writer);
    assertEquals("", writer.toString());
    assertEquals(0, result);
}

@Test
public void testTranslate_withSemicolonAfterNumber() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#65;ABC", 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(4, result);
}

@Test
public void testTranslate_indexNotAtStart() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("ABC&#65;", 3, writer);
    assertEquals("A", writer.toString());
    assertEquals(4, result);
}

@Test
public void testTranslate_zeroValueEntity() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#0;", 0, writer);
    assertEquals("\0", writer.toString());
    assertEquals(4, result);
}

@Test
public void testTranslate_hexWithLowercaseLetters() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#x7a;", 0, writer);
    assertEquals("z", writer.toString());
    assertEquals(5, result);
}

@Test
public void testTranslate_hexWithUppercaseLetters() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    Writer writer = new StringWriter();
    int result = unescaper.translate("&#x5A;", 0, writer);
    assertEquals("Z", writer.toString());
    assertEquals(5, result);
}
