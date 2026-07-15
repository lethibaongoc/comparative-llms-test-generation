@Test
void testEscapePlainAscii() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    entities.escape(writer, "abc");
    assertEquals("abc", writer.toString());
}

@Test
void testEscapeWithMappedEntity() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            if (c == '<') {
                return "lt";
            }
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    entities.escape(writer, "a<b");
    assertEquals("a&lt;b", writer.toString());
}

@Test
void testEscapeAboveAsciiRange() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    entities.escape(writer, "a¢b"); // Cent sign (¢), code point 162
    assertEquals("a&#162;b", writer.toString());
}

@Test
void testEscapeSupplementaryCharacter() throws IOException {
    Entities entities = new Entities() {
        @Override
        public String entityName(int c) {
            return null;
        }
    };
    StringWriter writer = new StringWriter();
    // U+1F600 (Grinning Face emoji) is represented as a surrogate pair
    String input = "a😀b";
    entities.escape(writer, input);
    assertEquals("a&#128512;b", writer.toString());
}
