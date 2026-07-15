@Test
void testTranslate() throws Exception {
    NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
    java.io.StringWriter out = new java.io.StringWriter();

    // Test decimal entity with semicolon
    int consumedDecimal = unescaper.translate("&#65;", 0, out);
    assertEquals(5, consumedDecimal);
    assertEquals("A", out.toString());

    // Test hex entity with semicolon
    out = new java.io.StringWriter();
    int consumedHex = unescaper.translate("&#x41;", 0, out);
    assertEquals(6, consumedHex);
    assertEquals("A", out.toString());

    // Test entity without semicolon (supported by the implementation)
    out = new java.io.StringWriter();
    int consumedNoSemi = unescaper.translate("&#65abc", 0, out);
    assertEquals(4, consumedNoSemi);
    assertEquals("A", out.toString());

    // Test no match (does not start with &#)
    out = new java.io.StringWriter();
    int consumedNoMatch = unescaper.translate("abc", 0, out);
    assertEquals(0, consumedNoMatch);
    assertEquals("", out.toString());

    // Test supplementary character (outside BMP)
    out = new java.io.StringWriter();
    int consumedSupplementary = unescaper.translate("&#x1F600;", 0, out);
    assertEquals(9, consumedSupplementary);
    assertEquals("😀", out.toString());
}
