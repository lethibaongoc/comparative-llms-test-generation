package org.jfree.chart.renderer.category;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
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
    public void testGetLegendItems_NullDataset() {
        CategoryPlot plot = new CategoryPlot();
        renderer.setPlot(plot);
        assertEquals(0, renderer.getLegendItems().getItemCount());
    }

    @Test
    public void testGetLegendItems_AscendingOrder() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Series 1", "Category 1");
        dataset.addValue(20, "Series 1", "Category 2");
        dataset.addValue(30, "Series 2", "Category 1");
        dataset.addValue(40, "Series 2", "Category 2");

        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(0, dataset);
        plot.setRenderer(0, renderer);
        renderer.setPlot(plot);

        LegendItemCollection legendItems = renderer.getLegendItems();
        assertEquals(2, legendItems.getItemCount());
        assertEquals("Series 1", legendItems.getItem(0).getLabel());
        assertEquals("Series 2", legendItems.getItem(1).getLabel());
    }

    @Test
    public void testGetLegendItems_DescendingOrder() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Series 1", "Category 1");
        dataset.addValue(20, "Series 1", "Category 2");
        dataset.addValue(30, "Series 2", "Category 1");
        dataset.addValue(40, "Series 2", "Category 2");

        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(0, dataset);
        plot.setRenderer(0, renderer);
        plot.setRowRenderingOrder(SortOrder.DESCENDING);
        renderer.setPlot(plot);

        LegendItemCollection legendItems = renderer.getLegendItems();
        assertEquals(2, legendItems.getItemCount());
        assertEquals("Series 2", legendItems.getItem(0).getLabel());
        assertEquals("Series 1", legendItems.getItem(1).getLabel());
    }
}
