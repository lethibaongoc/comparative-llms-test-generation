@Test
void shouldEscapeNamedEntity() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    entities.escape(writer, "&");
    assertEquals("&amp;", writer.toString());
}

@Test
void shouldEscapeMultipleNamedEntities() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    entities.escape(writer, "&<");
    assertEquals("&amp;&lt;", writer.toString());
}

@Test
void shouldEscapeUnicodeSupplementaryCharacter() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    String supplementary = new StringBuilder().appendCodePoint(0x1F600).toString();
    entities.escape(writer, supplementary);
    assertEquals("&#128512;", writer.toString());
}

@Test
void shouldEscapeUnicodeSupplementaryCharacterWithFollowingChar() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    String supplementary = new StringBuilder().appendCodePoint(0x1F600).append('A').toString();
    entities.escape(writer, supplementary);
    assertEquals("&#128512;A", writer.toString());
}

@Test
void shouldEscapeHighUnicodeCharacter() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    entities.escape(writer, "€");
    assertEquals("&#8364;", writer.toString());
}

@Test
void shouldNotEscapeAsciiCharacters() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    entities.escape(writer, "Hello World");
    assertEquals("Hello World", writer.toString());
}

@Test
void shouldEscapeMixedContent() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    entities.escape(writer, "Hello & World €");
    assertEquals("Hello &amp; World &#8364;", writer.toString());
}

@Test
void shouldHandleEmptyString() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    entities.escape(writer, "");
    assertEquals("", writer.toString());
}

@Test
void shouldHandleNullEntityName() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    entities.escape(writer, "A");
    assertEquals("A", writer.toString());
}

@Test
void shouldEscapeSupplementaryCharacterCorrectlyIncrementIndex() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    String str = new StringBuilder().appendCodePoint(0x1F600).appendCodePoint(0x1F601).toString();
    entities.escape(writer, str);
    assertEquals("&#128512;&#128513;", writer.toString());
}

@Test
void shouldNotIncrementIndexForSupplementaryAtEnd() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    String str = new StringBuilder().appendCodePoint(0x1F600).toString();
    entities.escape(writer, str);
    assertEquals("&#128512;", writer.toString());
}

@Test
void shouldEscapeHighUnicodeBeforeSupplementary() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities();
    String str = "€" + new StringBuilder().appendCodePoint(0x1F600).toString();
    entities.escape(writer, str);
    assertEquals("&#8364;&#128512;", writer.toString());
}
