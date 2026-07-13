@Test
public void testEscape_namedEntity() throws IOException {
    Entities entities = new Entities();
    entities.addEntity("amp", '&');
    StringWriter writer = new StringWriter();

    entities.escape(writer, "A&B");

    assertEquals("A&amp;B", writer.toString());
}

@Test
public void testEscape_asciiWithoutEntity() throws IOException {
    Entities entities = new Entities();
    StringWriter writer = new StringWriter();

    entities.escape(writer, "Hello 123!");

    assertEquals("Hello 123!", writer.toString());
}

@Test
public void testEscape_nonAsciiWithoutEntityAsNumericReference() throws IOException {
    Entities entities = new Entities();
    StringWriter writer = new StringWriter();

    entities.escape(writer, "café");

    assertEquals("caf&#233;", writer.toString());
}

@Test
public void testEscape_supplementaryCodePointAsNumericReference() throws IOException {
    Entities entities = new Entities();
    StringWriter writer = new StringWriter();

    entities.escape(writer, "😀");

    assertEquals("&#128512;", writer.toString());
}

@Test
public void testEscape_multipleEntityTypes() throws IOException {
    Entities entities = new Entities();
    entities.addEntity("lt", '<');
    entities.addEntity("gt", '>');
    entities.addEntity("quot", '"');
    StringWriter writer = new StringWriter();

    entities.escape(writer, "<\"é\">");

    assertEquals("&lt;&quot;&#233;&quot;&gt;", writer.toString());
}

@Test
public void testEscape_emptyString() throws IOException {
    Entities entities = new Entities();
    StringWriter writer = new StringWriter();

    entities.escape(writer, "");

    assertEquals("", writer.toString());
}
