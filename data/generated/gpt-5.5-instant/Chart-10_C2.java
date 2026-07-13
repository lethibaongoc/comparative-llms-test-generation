@Test
public void testGenerateToolTipFragment_plainText() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();

    String result = generator.generateToolTipFragment("Sample Tooltip");

    assertEquals(" title=\"Sample Tooltip\" alt=\"\"", result);
}

@Test
public void testGenerateToolTipFragment_htmlSpecialCharacters() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();

    String result = generator.generateToolTipFragment("<tag> & \"text\"");

    assertEquals(" title=\"&lt;tag&gt; &amp; &quot;text&quot;\" alt=\"\"", result);
}

@Test
public void testGenerateToolTipFragment_emptyString() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();

    String result = generator.generateToolTipFragment("");

    assertEquals(" title=\"\" alt=\"\"", result);
}

@Test
public void testGenerateToolTipFragment_nullInput() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();

    String result = generator.generateToolTipFragment(null);

    assertEquals(" title=\"\" alt=\"\"", result);
}
