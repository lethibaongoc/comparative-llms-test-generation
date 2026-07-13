@Test
public void testContains_characterPresent() {
    StrBuilder builder = new StrBuilder();
    builder.append("hello");

    assertTrue(builder.contains('e'));
}

@Test
public void testContains_characterAbsent() {
    StrBuilder builder = new StrBuilder();
    builder.append("hello");

    assertFalse(builder.contains('z'));
}

@Test
public void testContains_emptyBuilder() {
    StrBuilder builder = new StrBuilder();

    assertFalse(builder.contains('a'));
}

@Test
public void testContains_firstCharacter() {
    StrBuilder builder = new StrBuilder();
    builder.append("abc");

    assertTrue(builder.contains('a'));
}

@Test
public void testContains_lastCharacter() {
    StrBuilder builder = new StrBuilder();
    builder.append("abc");

    assertTrue(builder.contains('c'));
}

@Test
public void testContains_duplicateCharacter() {
    StrBuilder builder = new StrBuilder();
    builder.append("banana");

    assertTrue(builder.contains('a'));
}
