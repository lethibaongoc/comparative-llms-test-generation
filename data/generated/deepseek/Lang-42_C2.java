@Test
void testEscapeNullEntityNameAscii() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    entities.escape(writer, "abc");
    assertEquals("abc", writer.toString());
}

@Test
void testEscapeNullEntityNameAbove7F() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    entities.escape(writer, "à"); // à
    assertEquals("&#224;", writer.toString());
}

@Test
void testEscapeNullEntityNameSupplementary() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    entities.escape(writer, "𐀀"); // U+10000
    assertEquals("&#65536;", writer.toString());
}

@Test
void testEscapeNullEntityNameSupplementaryWithTrailingChar() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    entities.escape(writer, "𐀀x");
    assertEquals("&#65536;x", writer.toString());
}

@Test
void testEscapeWithEntityName() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return c == '&' ? "amp" : null;
        }
    };
    entities.escape(writer, "&");
    assertEquals("&amp;", writer.toString());
}

@Test
void testEscapeMixed() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return c == '&' ? "amp" : null;
        }
    };
    entities.escape(writer, "a&é𐀀");
    assertEquals("a&amp;&#233;&#65536;", writer.toString());
}

@Test
void testEscapeEmptyString() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    entities.escape(writer, "");
    assertEquals("", writer.toString());
}

@Test
void testEscapeThrowsIOException() {
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    Writer failingWriter = new Writer() {
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            throw new IOException("Test exception");
        }
        @Override
        public void flush() throws IOException {}
        @Override
        public void close() throws IOException {}
    };
    assertThrows(IOException.class, () -> entities.escape(failingWriter, "test"));
}

@Test
void testEscapeNullEntityNameBoundary7F() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    String input = String.valueOf((char) 0x7F);
    entities.escape(writer, input);
    assertEquals(input, writer.toString());
}

@Test
void testEscapeNullEntityNameBoundary80() throws IOException {
    StringWriter writer = new StringWriter();
    Entities entities = new Entities() {
        @Override
        protected String entityName(int c) {
            return null;
        }
    };
    String input = String.valueOf((char) 0x80);
    entities.escape(writer, input);
    assertEquals("&#128;", writer.toString());
}
