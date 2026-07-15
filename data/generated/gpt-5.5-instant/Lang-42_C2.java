@Test
void testEscapeNamedEntitiesAndAsciiCharacters() throws IOException {
    StringWriter writer = new StringWriter();

    Entities.HTML40.escape(writer, "<tag attr=\"value\">Tom & Jerry</tag>");

    assertEquals("&lt;tag attr=&quot;value&quot;&gt;Tom &amp; Jerry&lt;/tag&gt;",
            writer.toString());
}

@Test
void testEscapeNonAsciiCharacterAsNumericEntity() throws IOException {
    StringWriter writer = new StringWriter();

    Entities.HTML40.escape(writer, "café");

    assertEquals("caf&#233;", writer.toString());
}

@Test
void testEscapeSupplementaryUnicodeCharacterAsSingleNumericEntity()
        throws IOException {
    StringWriter writer = new StringWriter();

    Entities.HTML40.escape(writer, "A😀B");

    assertEquals("A&#128512;B", writer.toString());
}

@Test
void testEscapeEmptyString() throws IOException {
    StringWriter writer = new StringWriter();

    Entities.HTML40.escape(writer, "");

    assertEquals("", writer.toString());
}

@Test
void testEscapePropagatesWriterIOException() {
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

    assertThrows(IOException.class,
            () -> Entities.HTML40.escape(writer, "&"));
}
