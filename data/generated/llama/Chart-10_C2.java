package org.jfree.chart.imagemap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandardToolTipTagFragmentGeneratorTest {

    @Test
    void testGenerateToolTipFragment() {
        StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
        assertEquals(" title=\"\" alt=\"\"", generator.generateToolTipFragment(""));
        assertEquals(" title=\"Hello World\" alt=\"\"", generator.generateToolTipFragment("Hello World"));
        assertEquals(" title=\"&lt;html&gt;\" alt=\"\"", generator.generateToolTipFragment("<html>"));
    }

    @Test
    void testGenerateToolTipFragmentNull() {
        StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
        assertThrows(NullPointerException.class, () -> generator.generateToolTipFragment(null));
    }
}
