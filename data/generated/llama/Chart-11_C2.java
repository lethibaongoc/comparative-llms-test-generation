package org.jfree.util;

import java.awt.geom.GeneralPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeUtilitiesTest {

    @Test
    void testEqual_NullPaths() {
        assertTrue(ShapeUtilities.equal(null, null));
        assertFalse(ShapeUtilities.equal(new GeneralPath(), null));
        assertFalse(ShapeUtilities.equal(null, new GeneralPath()));
    }

    @Test
    void testEqual_SamePath() {
        GeneralPath path = new GeneralPath();
        assertTrue(ShapeUtilities.equal(path, path));
    }

    @Test
    void testEqual_DifferentWindingRules() {
        GeneralPath path1 = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        GeneralPath path2 = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        assertFalse(ShapeUtilities.equal(path1, path2));
    }

    @Test
    void testEqual_SameSegments() {
        GeneralPath path1 = new GeneralPath();
        path1.moveTo(0, 0);
        path1.lineTo(1, 1);
        GeneralPath path2 = new GeneralPath();
        path2.moveTo(0, 0);
        path2.lineTo(1, 1);
        assertTrue(ShapeUtilities.equal(path1, path2));
    }

    @Test
    void testEqual_DifferentSegments() {
        GeneralPath path1 = new GeneralPath();
        path1.moveTo(0, 0);
        path1.lineTo(1, 1);
        GeneralPath path2 = new GeneralPath();
        path2.moveTo(0, 0);
        path2.lineTo(2, 2);
        assertFalse(ShapeUtilities.equal(path1, path2));
    }

    @Test
    void testEqual_DifferentSegmentTypes() {
        GeneralPath path1 = new GeneralPath();
        path1.moveTo(0, 0);
        path1.lineTo(1, 1);
        GeneralPath path2 = new GeneralPath();
        path2.moveTo(0, 0);
        path2.quadTo(1, 1, 2, 2);
        assertFalse(ShapeUtilities.equal(path1, path2));
    }
}
