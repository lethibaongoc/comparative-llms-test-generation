@Test
public void testEscape_asciiWithoutEntity_writtenUnchanged() throws IOException {
    Entities entities = new Entities();
    StringWriter writer = new StringWriter();

    entities.escape(writer, "Hello 123!");

    assertEquals("Hello 123!", writer.toString());
}

@Test
public void testEscape_namedEntity_writtenAsEntityReference() throws IOException {
    Entities entities = new Entities();
    entities.addEntity("amp", '&');
    entities.addEntity("lt", '<');
    entities.addEntity("gt", '>');
    StringWriter writer = new StringWriter();

    entities.escape(writer, "A&B<C>");

    assertEquals("A&amp;B&lt;C&gt;", writer.toString());
}

@Test
public void testEscape_nonAsciiCharacterWithoutEntity_writtenAsDecimalReference() throws IOException {
    Entities entities = new Entities();
    StringWriter writer = new StringWriter();

    entities.escape(writer, "café");

    assertEquals("caf&#233;", writer.toString());
}

@Test
public void testEscape_supplementaryCharacter_writtenAsSingleDecimalReference() throws IOException {
    Entities entities = new Entities();
    StringWriter writer = new StringWriter();

    entities.escape(writer, "A😀B");

    assertEquals("A&#128512;B", writer.toString());
}

@Test
public void testEscape_namedNonAsciiEntity_preferredOverNumericReference() throws IOException {
    Entities entities = new Entities();
    entities.addEntity("copy", 169);
    StringWriter writer = new StringWriter();

    entities.escape(writer, "©");

    assertEquals("&copy;", writer.toString());
}

@Test
public void testEscape_emptyString_writesNothing() throws IOException {
    Entities entities = new Entities();
    StringWriter writer = new StringWriter();

    entities.escape(writer, "");

    assertEquals("", writer.toString());
}

@Test
public void testEscape_writerIOException_isPropagated() {
    Entities entities = new Entities();
    Writer writer = new Writer() {
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            throw new IOException("write failed");
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    };

    IOException exception = assertThrows(
        IOException.class,
        () -> entities.escape(writer, "A")
    );

    assertEquals("write failed", exception.getMessage());
}
