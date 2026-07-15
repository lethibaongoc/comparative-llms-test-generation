@Test
void testGenerateToolTipFragmentWithPlainText() {
    StandardToolTipTagFragmentGenerator generator =
            new StandardToolTipTagFragmentGenerator();

    String result = generator.generateToolTipFragment("Simple Tooltip");

    assertEquals(" title=\"Simple Tooltip\" alt=\"\"", result);
}

@Test
void testGenerateToolTipFragmentWithHtmlSpecialCharacters() {
    StandardToolTipTagFragmentGenerator generator =
            new StandardToolTipTagFragmentGenerator();

    String result = generator.generateToolTipFragment("<tag> & \"quote\"");

    assertEquals(" title=\"&lt;tag&gt; &amp; &quot;quote&quot;\" alt=\"\"", result);
}

@Test
void testGenerateToolTipFragmentWithEmptyString() {
    StandardToolTipTagFragmentGenerator generator =
            new StandardToolTipTagFragmentGenerator();

    String result = generator.generateToolTipFragment("");

    assertEquals(" title=\"\" alt=\"\"", result);
}
