package org.jfree.chart.imagemap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandardToolTipTagFragmentGeneratorTest {

    @Test
    public void testGenerateToolTipFragment_NullInput() {
        StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
        assertThrows(NullPointerException.class, () -> generator.generateToolTipFragment(null));
    }

    @Test
    public void testGenerateToolTipFragment_EmptyInput() {
        StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
        String result = generator.generateToolTipFragment("");
        assertEquals(" title=\"\" alt=\"\"", result);
    }

    @Test
    public void testGenerateToolTipFragment_SimpleInput() {
        StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
        String result = generator.generateToolTipFragment("Hello World");
        assertEquals(" title=\"Hello World\" alt=\"\"", result);
    }

    @Test
    public void testGenerateToolTipFragment_SpecialCharactersInput() {
        StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
        String result = generator.generateToolTipFragment("<Hello> World & Friends");
        assertEquals(" title=\"&lt;Hello&gt; World &amp; Friends\" alt=\"\"", result);
    }
}
