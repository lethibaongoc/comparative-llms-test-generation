package org.jfree.chart.renderer;

import java.awt.Color;
import java.awt.Paint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GrayPaintScaleTest {

    @Test
    void testGetPaint_LowerBound() {
        GrayPaintScale scale = new GrayPaintScale(0.0, 1.0);
        Paint paint = scale.getPaint(0.0);
        Color expected = new Color(0, 0, 0);
        assertEquals(expected, paint);
    }

    @Test
    void testGetPaint_UpperBound() {
        GrayPaintScale scale = new GrayPaintScale(0.0, 1.0);
        Paint paint = scale.getPaint(1.0);
        Color expected = new Color(255, 255, 255);
        assertEquals(expected, paint);
    }

    @Test
    void testGetPaint_MiddleValue() {
        GrayPaintScale scale = new GrayPaintScale(0.0, 1.0);
        Paint paint = scale.getPaint(0.5);
        Color expected = new Color(128, 128, 128);
        assertEquals(expected, paint);
    }

    @Test
    void testGetPaint_BelowLowerBound() {
        GrayPaintScale scale = new GrayPaintScale(0.0, 1.0);
        Paint paint = scale.getPaint(-1.0);
        Color expected = new Color(0, 0, 0);
        assertEquals(expected, paint);
    }

    @Test
    void testGetPaint_AboveUpperBound() {
        GrayPaintScale scale = new GrayPaintScale(0.0, 1.0);
        Paint paint = scale.getPaint(2.0);
        Color expected = new Color(255, 255, 255);
        assertEquals(expected, paint);
    }

    @Test
    void testGetPaint_CustomBounds() {
        GrayPaintScale scale = new GrayPaintScale(10.0, 20.0);
        Paint paint = scale.getPaint(15.0);
        Color expected = new Color(128, 128, 128);
        assertEquals(expected, paint);
    }
}
