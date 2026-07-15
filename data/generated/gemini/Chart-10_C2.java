@Test
void testGenerateToolTipFragment() {
    StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();

    // Test standard tooltip text
    assertEquals(" title=\"Hello World\" alt=\"\"", generator.generateToolTipFragment("Hello World"));

    // Test tooltip text that contains characters requiring HTML escaping
    assertEquals(" title=\"&lt;script&gt;alert(&quot;test&quot;)&lt;/script&gt;\" alt=\"\"",
        generator.generateToolTipFragment("<script>alert(\"test\")</script>"));

    // Test empty tooltip text
    assertEquals(" title=\"\" alt=\"\"", generator.generateToolTipFragment(""));
}
