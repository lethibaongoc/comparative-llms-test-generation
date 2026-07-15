@Test
void testContainsCharacterPresent() {
    StrBuilder builder = new StrBuilder();
    builder.append("hello");

    assertTrue(builder.contains('h'));
    assertTrue(builder.contains('e'));
    assertTrue(builder.contains('o'));
}

@Test
void testContainsCharacterAbsent() {
    StrBuilder builder = new StrBuilder();
    builder.append("hello");

    assertFalse(builder.contains('x'));
}

@Test
void testContainsOnEmptyBuilder() {
    StrBuilder builder = new StrBuilder();

    assertFalse(builder.contains('a'));
}

@Test
void testContainsDuplicateCharacter() {
    StrBuilder builder = new StrBuilder();
    builder.append("banana");

    assertTrue(builder.contains('a'));
    assertTrue(builder.contains('n'));
}
