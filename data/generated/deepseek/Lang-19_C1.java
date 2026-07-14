@Test
void shouldTranslateSimpleDecimalEntity() throws IOException {
    String input = "&#65;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(4, result); // & # 6 5 ;
}

@Test
void shouldTranslateDecimalEntityWithoutSemicolon() throws IOException {
    String input = "&#65";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(3, result); // & # 6 5
}

@Test
void shouldTranslateSimpleHexEntity() throws IOException {
    String input = "&#x41;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(5, result); // & # x 4 1 ;
}

@Test
void shouldTranslateHexEntityWithoutSemicolon() throws IOException {
    String input = "&#x41";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(4, result); // & # x 4 1
}

@Test
void shouldTranslateHexEntityWithLowercaseX() throws IOException {
    String input = "&#x61;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("a", writer.toString());
    assertEquals(5, result);
}

@Test
void shouldTranslateHexEntityWithUppercaseX() throws IOException {
    String input = "&#X41;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(5, result);
}

@Test
void shouldTranslateEntityWithMixedHexDigits() throws IOException {
    String input = "&#x1F;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("", writer.toString());
    assertEquals(5, result);
}

@Test
void shouldTranslateSupplementaryCharacter() throws IOException {
    String input = "&#x1F600;"; // Grinning face emoji
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("😀", writer.toString());
    assertEquals(8, result);
}

@Test
void shouldReturnZeroWhenNotStartingWithAmpersand() throws IOException {
    String input = "ABC";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals(0, result);
    assertEquals("", writer.toString());
}

@Test
void shouldReturnZeroWhenInputTooShort() throws IOException {
    String input = "&#";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals(0, result);
    assertEquals("", writer.toString());
}

@Test
void shouldReturnZeroWhenHexXIsLastChar() throws IOException {
    String input = "&#x";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals(0, result);
    assertEquals("", writer.toString());
}

@Test
void shouldReturnZeroWhenNoDigitsAfterNumberSign() throws IOException {
    String input = "&#;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals(0, result);
    assertEquals("", writer.toString());
}

@Test
void shouldReturnZeroForInvalidNumberFormat() throws IOException {
    String input = "&#ABC;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals(0, result);
    assertEquals("", writer.toString());
}

@Test
void shouldHandleEntityWithTrailingSemicolonInCalculation() throws IOException {
    String input = "&#65; ";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(4, result);
}

@Test
void shouldHandleHexEntityWithTrailingSemicolonInCalculation() throws IOException {
    String input = "&#x41; ";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("A", writer.toString());
    assertEquals(5, result);
}

@Test
void shouldProcessFromSpecificIndex() throws IOException {
    String input = "abc&#65;def";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 3, writer);
    assertEquals("A", writer.toString());
    assertEquals(4, result);
}

@Test
void shouldReturnZeroWhenStartingAtIndexNearEnd() throws IOException {
    String input = "abc&#65;def";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 7, writer);
    assertEquals(0, result);
    assertEquals("", writer.toString());
}

@Test
void shouldHandleUppercaseHexLetters() throws IOException {
    String input = "&#xFF;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("ÿ", writer.toString());
    assertEquals(5, result);
}

@Test
void shouldHandleLowercaseHexLetters() throws IOException {
    String input = "&#xff;";
    Writer writer = new StringWriter();
    int result = unescaper.translate(input, 0, writer);
    assertEquals("ÿ", writer.toString());
    assertEquals(5, result);
}
