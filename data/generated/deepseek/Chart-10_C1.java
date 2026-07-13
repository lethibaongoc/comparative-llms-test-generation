@Test
@DisplayName("Should generate a valid fragment for a standard tooltip text")
void generateToolTipFragment_standardText_returnsCorrectFragment() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
    String toolTipText = "This is a tooltip";
    String expectedFragment = " title=This is a tooltip alt=";
    String actualFragment = generator.generateToolTipFragment(toolTipText);
    assertEquals(expectedFragment, actualFragment);
}

@Test
@DisplayName("Should generate a valid fragment for an empty tooltip text")
void generateToolTipFragment_emptyText_returnsCorrectFragment() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
    String toolTipText = "";
    String expectedFragment = " title= alt=";
    String actualFragment = generator.generateToolTipFragment(toolTipText);
    assertEquals(expectedFragment, actualFragment);
}

@Test
@DisplayName("Should generate a valid fragment for a null tooltip text")
void generateToolTipFragment_nullText_returnsCorrectFragment() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
    String toolTipText = null;
    String expectedFragment = " title=null alt=";
    String actualFragment = generator.generateToolTipFragment(toolTipText);
    assertEquals(expectedFragment, actualFragment);
}

@Test
@DisplayName("Should generate a valid fragment for tooltip text with special characters")
void generateToolTipFragment_specialCharacters_returnsCorrectFragment() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
    String toolTipText = "Tool&tip with <special> \"chars\"!";
    String expectedFragment = " title=Tool&tip with <special> \"chars\"! alt=";
    String actualFragment = generator.generateToolTipFragment(toolTipText);
    assertEquals(expectedFragment, actualFragment);
}
