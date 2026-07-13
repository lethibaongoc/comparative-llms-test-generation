package org.jfree.chart.renderer.category;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AbstractCategoryItemRendererTest {

    private AbstractCategoryItemRenderer renderer;

    @BeforeEach
    public void setUp() {
        renderer = new AbstractCategoryItemRenderer() {
            @Override
            public LegendItem getLegendItem(int datasetIndex, int series) {
                return new LegendItem("Series " + series);
            }

            @Override
            public boolean isSeriesVisibleInLegend(int series) {
                return true;
            }
        };
    }

    @Test
    public void testGetLegendItems_NullPlot() {
        assertEquals(0, renderer.getLegendItems().getItemCount());
    }

    @Test
    public void testGetLegendItems_NoDataset() {
        CategoryPlot plot = new CategoryPlot();
        renderer.setPlot(plot);
        assertEquals(0, renderer.getLegendItems().getItemCount());
    }

    @Test
    public void testGetLegendItems_AscendingOrder() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "Series 1", "Category 1");
        dataset.addValue(2, "Series 2", "Category 1");
        CategoryPlot plot = new CategoryPlot(dataset, null, null, null);
        plot.setRowRenderingOrder(SortOrder.ASCENDING);
        renderer.setPlot(plot);
        assertEquals(2, renderer.getLegendItems().getItemCount());
    }

    @Test
    public void testGetLegendItems_DescendingOrder() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "Series 1", "Category 1");
        dataset.addValue(2, "Series 2", "Category 1");
        CategoryPlot plot = new CategoryPlot(dataset, null, null, null);
        plot.setRowRenderingOrder(SortOrder.DESCENDING);
        renderer.setPlot(plot);
        assertEquals(2, renderer.getLegendItems().getItemCount());
    }
}
