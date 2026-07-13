package org.jfree.chart.plot;

import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PiePlotTest {

    @Test
    public void testGetMaximumExplodePercent_NullDataset() {
        PiePlot plot = new PiePlot();
        assertEquals(0.0, plot.getMaximumExplodePercent(), 0.001);
    }

    @Test
    public void testGetMaximumExplodePercent_EmptyDataset() {
        PiePlot plot = new PiePlot();
        DefaultPieDataset dataset = new DefaultPieDataset();
        plot.setDataset(dataset);
        assertEquals(0.0, plot.getMaximumExplodePercent(), 0.001);
    }

    @Test
    public void testGetMaximumExplodePercent_NoExplodePercentages() {
        PiePlot plot = new PiePlot();
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 10);
        dataset.setValue("B", 20);
        plot.setDataset(dataset);
        assertEquals(0.0, plot.getMaximumExplodePercent(), 0.001);
    }

    @Test
    public void testGetMaximumExplodePercent_SingleExplodePercentage() {
        PiePlot plot = new PiePlot();
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 10);
        plot.setDataset(dataset);
        plot.setExplodePercent("A", 0.5);
        assertEquals(0.5, plot.getMaximumExplodePercent(), 0.001);
    }

    @Test
    public void testGetMaximumExplodePercent_MultipleExplodePercentages() {
        PiePlot plot = new PiePlot();
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 10);
        dataset.setValue("B", 20);
        plot.setDataset(dataset);
        plot.setExplodePercent("A", 0.3);
        plot.setExplodePercent("B", 0.5);
        assertEquals(0.5, plot.getMaximumExplodePercent(), 0.001);
    }

    @Test
    public void testGetMaximumExplodePercent_MultipleExplodePercentages_SameValue() {
        PiePlot plot = new PiePlot();
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 10);
        dataset.setValue("B", 20);
        plot.setDataset(dataset);
        plot.setExplodePercent("A", 0.5);
        plot.setExplodePercent("B", 0.5);
        assertEquals(0.5, plot.getMaximumExplodePercent(), 0.001);
    }
}
