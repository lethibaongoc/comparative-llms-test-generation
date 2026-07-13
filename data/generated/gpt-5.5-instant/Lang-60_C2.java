@Test
public void testContains_existingCharacter() {
    StrBuilder obj = new StrBuilder("abcdef");
    assertTrue(obj.contains('c'));
}

@Test
public void testContains_firstCharacter() {
    StrBuilder obj = new StrBuilder("abcdef");
    assertTrue(obj.contains('a'));
}

@Test
public void testContains_lastCharacter() {
    StrBuilder obj = new StrBuilder("abcdef");
    assertTrue(obj.contains('f'));
}

@Test
public void testContains_missingCharacter() {
    StrBuilder obj = new StrBuilder("abcdef");
    assertFalse(obj.contains('x'));
}

@Test
public void testContains_emptyBuilder() {
    StrBuilder obj = new StrBuilder();
    assertFalse(obj.contains('a'));
}

@Test
public void testContains_duplicateCharacter() {
    StrBuilder obj = new StrBuilder("aabbcc");
    assertTrue(obj.contains('b'));
}
