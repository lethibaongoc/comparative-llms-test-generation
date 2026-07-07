package org.jfree.chart.renderer.category;

import java.awt.Color;
import java.awt.BasicStroke;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinMaxCategoryRendererTest {

    @Test
    void testEquals_SameInstance() {
        MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
        assertTrue(renderer.equals(renderer));
    }

    @Test
    void testEquals_DifferentClass() {
        MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
        assertFalse(renderer.equals("different class"));
    }

    @Test
    void testEquals_SameProperties() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        assertTrue(renderer1.equals(renderer2));
    }

    @Test
    void testEquals_DifferentPlotLines() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        renderer2.setDrawLines(true);
        assertFalse(renderer1.equals(renderer2));
    }

    @Test
    void testEquals_DifferentGroupPaint() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        renderer2.setGroupPaint(Color.red);
        assertFalse(renderer1.equals(renderer2));
    }

    @Test
    void testEquals_DifferentGroupStroke() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        renderer2.setGroupStroke(new BasicStroke(2.0f));
        assertFalse(renderer1.equals(renderer2));
    }

    @Test
    void testEquals_Null() {
        MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
        assertFalse(renderer.equals(null));
    }
}
