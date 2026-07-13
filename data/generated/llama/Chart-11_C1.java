package org.jfree.util;

import java.awt.geom.GeneralPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeUtilitiesTest {

    @Test
    public void testEqual_NullPaths() {
        assertTrue(ShapeUtilities.equal(null, null));
        assertFalse(ShapeUtilities.equal(null, new GeneralPath()));
        assertFalse(ShapeUtilities.equal(new GeneralPath(), null));
    }

    @Test
    public void testEqual_DifferentWindingRules() {
        GeneralPath p1 = new GeneralPath();
        p1.setWindingRule(GeneralPath.WIND_EVEN_ODD);
        GeneralPath p2 = new GeneralPath();
        p2.setWindingRule(GeneralPath.WIND_NON_ZERO);
        assertFalse(ShapeUtilities.equal(p1, p2));
    }

    @Test
    public void testEqual_SamePath() {
        GeneralPath p1 = new GeneralPath();
        p1.moveTo(0, 0);
        p1.lineTo(10, 10);
        GeneralPath p2 = new GeneralPath();
        p2.moveTo(0, 0);
        p2.lineTo(10, 10);
        assertTrue(ShapeUtilities.equal(p1, p2));
    }

    @Test
    public void testEqual_DifferentPaths() {
        GeneralPath p1 = new GeneralPath();
        p1.moveTo(0, 0);
        p1.lineTo(10, 10);
        GeneralPath p2 = new GeneralPath();
        p2.moveTo(0, 0);
        p2.lineTo(20, 20);
        assertFalse(ShapeUtilities.equal(p1, p2));
    }

    @Test
    public void testEqual_EmptyPaths() {
        GeneralPath p1 = new GeneralPath();
        GeneralPath p2 = new GeneralPath();
        assertTrue(ShapeUtilities.equal(p1, p2));
    }

    @Test
    public void testEqual_MultipleSegments() {
        GeneralPath p1 = new GeneralPath();
        p1.moveTo(0, 0);
        p1.lineTo(10, 10);
        p1.lineTo(20, 20);
        GeneralPath p2 = new GeneralPath();
        p2.moveTo(0, 0);
        p2.lineTo(10, 10);
        p2.lineTo(20, 20);
        assertTrue(ShapeUtilities.equal(p1, p2));
    }

    @Test
    public void testEqual_DifferentSegmentOrder() {
        GeneralPath p1 = new GeneralPath();
        p1.moveTo(0, 0);
        p1.lineTo(10, 10);
        p1.lineTo(20, 20);
        GeneralPath p2 = new GeneralPath();
        p2.moveTo(0, 0);
        p2.lineTo(20, 20);
        p2.lineTo(10, 10);
        assertFalse(ShapeUtilities.equal(p1, p2));
    }
}
