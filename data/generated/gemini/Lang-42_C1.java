@Test
void testEscapeNormalAsciiCharacters() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            return null; // No named entities
        }
    };
    StringWriter writer = new StringWriter();
    entities.escape(writer, "Hello World!");
    assertEquals("Hello World!", writer.toString());
}

@Test
void testEscapeWithNamedEntity() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            if (c == '<') return "lt";
            if (c == '>') return "gt";
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    entities.escape(writer, "a<b");
    assertEquals("a&lt;b", writer.toString());
}

@Test
void testEscapeWithNonAsciiCharacterAbove0x7F() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    entities.escape(writer, "a©b"); // '©' is U+00A9
    assertEquals("a&#169;b", writer.toString());
}

@Test
void testEscapeWithSupplementaryCharacter() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    // Use a supplementary character: U+1F600 (Grinning Face emoji, encoded as surrogate pair)
    String input = "a🗠b";
    int codePoint = Character.codePointAt(input, 1); // 0x1F600 is 128512 in decimal

    entities.escape(writer, input);
    assertEquals("a&#" + codePoint + ";b", writer.toString());
}

@Test
void testEscapeWithSupplementaryCharacterAtTheVeryEnd() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    // If a supplementary character starts at the last index (malformed surrogate pair),
    // it won't satisfy "i < len - 1", so it falls through to standard or high ASCII rendering.
    String input = "a\uD83D";
    entities.escape(writer, input);

    // Character.codePointAt will just return the high surrogate value (55357)
    assertEquals("a&#55357;", writer.toString());
}

@Test
void testEscapeEmptyString() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    entities.escape(writer, "");
    assertEquals("", writer.toString());
}
