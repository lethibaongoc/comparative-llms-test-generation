package org.jfree.chart.plot;

import org.jfree.chart.axis.CategoryAxis;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryPlotTest {

    @Test
    void testGetDomainAxisIndex_NullAxis() {
        CategoryPlot plot = new CategoryPlot();
        assertThrows(IllegalArgumentException.class, () -> plot.getDomainAxisIndex(null));
    }

    @Test
    void testGetDomainAxisIndex_AxisNotInList() {
        CategoryPlot plot = new CategoryPlot();
        CategoryAxis axis = new CategoryAxis();
        assertEquals(-1, plot.getDomainAxisIndex(axis));
    }

    @Test
    void testGetDomainAxisIndex_AxisInList() {
        CategoryPlot plot = new CategoryPlot();
        CategoryAxis axis = new CategoryAxis();
        plot.setDomainAxis(0, axis);
        assertEquals(0, plot.getDomainAxisIndex(axis));
    }

    @Test
    void testGetDomainAxisIndex_MultipleAxes() {
        CategoryPlot plot = new CategoryPlot();
        CategoryAxis axis1 = new CategoryAxis();
        CategoryAxis axis2 = new CategoryAxis();
        plot.setDomainAxis(0, axis1);
        plot.setDomainAxis(1, axis2);
        assertEquals(0, plot.getDomainAxisIndex(axis1));
        assertEquals(1, plot.getDomainAxisIndex(axis2));
    }
}
