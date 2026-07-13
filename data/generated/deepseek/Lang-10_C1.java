@Test
void parse_success_matchAtStart() {
    Pattern pattern = Pattern.compile("abc");
    FastDateParser parser = new FastDateParser(pattern); 
    String source = "abcdefg";
    ParsePosition pos = new ParsePosition(0);

    boolean result = parser.parse(source, pos);

    assertTrue(result);
    assertEquals(3, pos.getIndex());
}

@Test
void parse_success_matchWithOffset() {
    Pattern pattern = Pattern.compile("xyz");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "123xyzABC";
    ParsePosition pos = new ParsePosition(3);

    boolean result = parser.parse(source, pos);

    assertTrue(result);
    assertEquals(6, pos.getIndex());
}

@Test
void parse_noMatch_atStartOfSubstring() {
    Pattern pattern = Pattern.compile("abc");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "xyzabc";
    ParsePosition pos = new ParsePosition(0);

    boolean result = parser.parse(source, pos);

    assertFalse(result);
    assertEquals(0, pos.getIndex());
}

@Test
void parse_noMatch_patternLaterInString() {
    Pattern pattern = Pattern.compile("def");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "abcdefg";
    ParsePosition pos = new ParsePosition(0);

    boolean result = parser.parse(source, pos);

    assertFalse(result);
    assertEquals(0, pos.getIndex());
}

@Test
void parse_noMatch_emptySourceString() {
    Pattern pattern = Pattern.compile("abc");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "";
    ParsePosition pos = new ParsePosition(0);

    boolean result = parser.parse(source, pos);

    assertFalse(result);
    assertEquals(0, pos.getIndex());
}

@Test
void parse_noMatch_posAtEndOfSource() {
    Pattern pattern = Pattern.compile("abc");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "xyz";
    ParsePosition pos = new ParsePosition(3);

    boolean result = parser.parse(source, pos);

    assertFalse(result);
    assertEquals(3, pos.getIndex());
}

@Test
void parse_success_emptyPatternMatch() {
    Pattern pattern = Pattern.compile("");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "abc";
    ParsePosition pos = new ParsePosition(0);

    boolean result = parser.parse(source, pos);

    assertTrue(result);
    assertEquals(0, pos.getIndex());
}

@Test
void parse_success_quantifierMatch() {
    Pattern pattern = Pattern.compile("a+b");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "aaabcc";
    ParsePosition pos = new ParsePosition(0);

    boolean result = parser.parse(source, pos);

    assertTrue(result);
    assertEquals(4, pos.getIndex());
}

@Test
void parse_noMatch_patternLongerThanRemainingString() {
    Pattern pattern = Pattern.compile("abcde");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "abcd";
    ParsePosition pos = new ParsePosition(0);

    boolean result = parser.parse(source, pos);

    assertFalse(result);
    assertEquals(0, pos.getIndex());
}

@Test
void parse_throwsNullPointerException_whenSourceIsNull() {
    Pattern pattern = Pattern.compile("test");
    FastDateParser parser = new FastDateParser(pattern);
    String source = null;
    ParsePosition pos = new ParsePosition(0);

    assertThrows(NullPointerException.class, () -> {
        parser.parse(source, pos);
    });
    assertEquals(0, pos.getIndex());
}

@Test
void parse_throwsIndexOutOfBoundsException_whenPosIsTooLarge() {
    Pattern pattern = Pattern.compile("test");
    FastDateParser parser = new FastDateParser(pattern);
    String source = "hello";
    ParsePosition pos = new ParsePosition(10);

    assertThrows(IndexOutOfBoundsException.class, () -> {
        parser.parse(source, pos);
    });
    assertEquals(10, pos.getIndex());
}
