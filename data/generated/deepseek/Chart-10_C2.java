@Test
public void testGenerateToolTipFragment_normalText() {
    StandardToolTipTagFragmentGenerator obj = new StandardToolTipTagFragmentGenerator();
    String toolTipText = "This is a tooltip";
    String expected = " title=This is a tooltip alt=";
    assertEquals(expected, obj.generateToolTipFragment(toolTipText));
}

@Test
public void testGenerateToolTipFragment_emptyText() {
    StandardToolTipTagFragmentGenerator obj = new StandardToolTipTagFragmentGenerator();
    String toolTipText = "";
    String expected = " title= alt=";
    assertEquals(expected, obj.generateToolTipFragment(toolTipText));
}

@Test
public void testGenerateToolTipFragment_nullText() {
    StandardToolTipTagFragmentGenerator obj = new StandardToolTipTagFragmentGenerator();
    String toolTipText = null;
    String expected = " title=null alt=";
    assertEquals(expected, obj.generateToolTipFragment(toolTipText));
}

@Test
public void testGenerateToolTipFragment_textWithSpecialCharacters() {
    StandardToolTipTagFragmentGenerator obj = new StandardToolTipTagFragmentGenerator();
    String toolTipText = "Text with <tags> & \"quotes\"";
    String expected = " title=Text with <tags> & \"quotes\" alt=";
    assertEquals(expected, obj.generateToolTipFragment(toolTipText));
}

@Test
public void testGenerateToolTipFragment_textWithLeadingTrailingSpaces() {
    StandardToolTipTagFragmentGenerator obj = new StandardToolTipTagFragmentGenerator();
    String toolTipText = "  Spaced text  ";
    String expected = " title=  Spaced text  alt=";
    assertEquals(expected, obj.generateToolTipFragment(toolTipText));
}

@Test
public void testGenerateToolTipFragment_longText() {
    StandardToolTipTagFragmentGenerator obj = new StandardToolTipTagFragmentGenerator();
    StringBuilder longTextBuilder = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
        longTextBuilder.append("a");
    }
    String longToolTipText = longTextBuilder.toString();
    String expected = " title=" + longToolTipText + " alt=";
    assertEquals(expected, obj.generateToolTipFragment(longToolTipText));
}
