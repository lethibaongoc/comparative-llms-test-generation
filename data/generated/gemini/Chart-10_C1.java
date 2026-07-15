@Test
@DisplayName("Should return empty attribute values when input is empty")
void testGenerateToolTipFragmentWithEmptyString() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
    String result = generator.generateToolTipFragment("");
    assertEquals(" title=\"\" alt=\"\"", result);
}

@Test
@DisplayName("Should correctly format tool tip text and escape HTML characters")
void testGenerateToolTipFragmentWithSpecialCharacters() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
    // Assuming ImageMapUtilities.htmlEscape converts '"' to '&quot;', '&' to '&amp;', etc.
    String input = "Hello <World> & \"Friends\"";
    String expected = " title=\"Hello &lt;World&gt; &amp; &quot;Friends&quot;\" alt=\"\"";

    String result = generator.generateToolTipFragment(input);
    assertEquals(expected, result);
}

@Test
@DisplayName("Should generate standard tool tip fragment for regular text")
void testGenerateToolTipFragmentWithNormalText() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
    String result = generator.generateToolTipFragment("Simple Tooltip");
    assertEquals(" title=\"Simple Tooltip\" alt=\"\"", result);
}
