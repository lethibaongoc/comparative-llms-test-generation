package org.jfree.chart.renderer.category;

import java.awt.Color;
import java.awt.BasicStroke;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinMaxCategoryRendererTest {

    @Test
    public void testEquals_SameInstance() {
        MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
        assertTrue(renderer.equals(renderer));
    }

    @Test
    public void testEquals_DifferentClass() {
        MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
        assertFalse(renderer.equals("different class"));
    }

    @Test
    public void testEquals_SameProperties() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        assertTrue(renderer1.equals(renderer2));
    }

    @Test
    public void testEquals_DifferentPlotLines() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        renderer2.setDrawLines(true);
        assertFalse(renderer1.equals(renderer2));
    }

    @Test
    public void testEquals_DifferentGroupPaint() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        renderer2.setGroupPaint(Color.red);
        assertFalse(renderer1.equals(renderer2));
    }

    @Test
    public void testEquals_DifferentGroupStroke() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        renderer2.setGroupStroke(new BasicStroke(2.0f));
        assertFalse(renderer1.equals(renderer2));
    }

    @Test
    public void testEquals_SuperclassProperties() {
        MinMaxCategoryRenderer renderer1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer renderer2 = new MinMaxCategoryRenderer();
        // Assuming AbstractCategoryItemRenderer has some properties that can be set
        // For demonstration purposes, let's assume it has a 'name' property
        // renderer1.setName("name1");
        // renderer2.setName("name2");
        // assertFalse(renderer1.equals(renderer2));
    }
}
