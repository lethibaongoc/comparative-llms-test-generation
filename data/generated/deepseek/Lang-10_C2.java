@Test
public void testParse_patternMatchesAtStart() {
    String source = "abcdefg";
    ParsePosition pos = new ParsePosition(0);

    boolean result = obj.parse(source, pos);

    assertTrue(result);
    assertEquals(3, pos.getIndex());
}

@Test
public void testParse_patternMatchesAtOffset() {
    String source = "abcdefg";
    ParsePosition pos = new ParsePosition(3);

    boolean result = obj.parse(source, pos);

    assertTrue(result);
    assertEquals(6, pos.getIndex());
}

@Test
public void testParse_patternDoesNotMatchAtCurrentPosition() {
    String source = "abcdefg";
    ParsePosition pos = new ParsePosition(0);

    boolean result = obj.parse(source, pos);

    assertFalse(result);
    assertEquals(0, pos.getIndex());
}

@Test
public void testParse_patternMatchesLaterButNotAtStart() {
    String source = "abcdefg";
    ParsePosition pos = new ParsePosition(0);

    boolean result = obj.parse(source, pos);

    assertFalse(result);
    assertEquals(0, pos.getIndex());
}

@Test
public void testParse_patternMatchesEntireString() {
    String source = "hello";
    ParsePosition pos = new ParsePosition(0);

    boolean result = obj.parse(source, pos);

    assertTrue(result);
    assertEquals(5, pos.getIndex());
}

@Test
public void testParse_emptySource() {
    String source = "";
    ParsePosition pos = new ParsePosition(0);

    boolean result = obj.parse(source, pos);

    assertFalse(result);
    assertEquals(0, pos.getIndex());
}

@Test
public void testParse_sourceShorterThanPattern() {
    String source = "short";
    ParsePosition pos = new ParsePosition(0);

    boolean result = obj.parse(source, pos);

    assertFalse(result);
    assertEquals(0, pos.getIndex());
}

@Test
public void testParse_parsePositionAtEndOfSource() {
    String source = "test";
    ParsePosition pos = new ParsePosition(4);

    boolean result = obj.parse(source, pos);

    assertFalse(result);
    assertEquals(4, pos.getIndex());
}

@Test
public void testParse_sourceWithNonAsciiCharacters() {
    String source = "C'est l'été en France.";
    ParsePosition pos = new ParsePosition(9);

    boolean result = obj.parse(source, pos);

    assertTrue(result);
    assertEquals(12, pos.getIndex());
}

@Test
public void testParse_patternWithQuantifiers() {
    String source = "123hello456";
    ParsePosition pos = new ParsePosition(0);

    boolean result = obj.parse(source, pos);

    assertTrue(result);
    assertEquals(3, pos.getIndex());

    pos.setIndex(8);
    result = obj.parse(source, pos);

    assertTrue(result);
    assertEquals(11, pos.getIndex());
}

@Test
public void testParse_patternMatchesEmptyString_noIndexAdvance() {
    String source = "abc";
    ParsePosition pos = new ParsePosition(0);

    boolean result = obj.parse(source, pos);

    assertTrue(result);
    assertEquals(0, pos.getIndex());
}

@Test
public void testParse_sourceIsNull_throwsNPE() {
    String source = null;
    ParsePosition pos = new ParsePosition(0);

    assertThrows(NullPointerException.class, () -> obj.parse(source, pos));
    assertEquals(0, pos.getIndex());
}

@Test
public void testParse_parsePositionIsNull_throwsNPE() {
    String source = "test";
    ParsePosition pos = null;

    assertThrows(NullPointerException.class, () -> obj.parse(source, pos));
}
