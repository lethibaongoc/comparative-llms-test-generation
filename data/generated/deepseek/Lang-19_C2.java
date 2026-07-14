@Test
void testTranslateWithDecimalEntity() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#65;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(1, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateWithHexEntityLowercase() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#x41;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(1, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateWithHexEntityUppercase() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#X41;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(1, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateWithSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#65;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(5, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateWithoutSemicolon() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#65";
    int result = unescaper.translate(input, 0, out);
    assertEquals(4, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateWithUnicodeSupplementaryCharacter() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#x1F600;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(1, result);
    assertEquals("😀", out.toString());
}

@Test
void testTranslateWhenNotStartingWithAmpersand() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "ABC";
    int result = unescaper.translate(input, 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateWhenNotNumericEntity() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&amp;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateWhenTooShort() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#";
    int result = unescaper.translate(input, 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateWithOnlyXAfterHash() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#x";
    int result = unescaper.translate(input, 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateWithInvalidNumberFormat() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#xyz;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(0, result);
    assertEquals("", out.toString());
}

@Test
void testTranslateWithZero() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#0;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(1, result);
    assertEquals("\0", out.toString());
}

@Test
void testTranslateAtNonZeroIndex() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "ABC&#65;";
    int result = unescaper.translate(input, 3, out);
    assertEquals(5, result);
    assertEquals("A", out.toString());
}

@Test
void testTranslateWithHexDigitsOnly() throws IOException {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    StringWriter out = new StringWriter();
    String input = "&#x1F;";
    int result = unescaper.translate(input, 0, out);
    assertEquals(1, result);
    assertEquals("", out.toString());
}
