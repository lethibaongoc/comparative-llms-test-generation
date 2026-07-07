package org.jfree.data.statistics;

import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultBoxAndWhiskerCategoryDatasetTest {

    @Test
    void testUpdateBounds_EmptyDataset() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        assertEquals(Double.NaN, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(Double.NaN, dataset.getMaximumRangeValue(), 0.0);
    }

    @Test
    void testUpdateBounds_SingleItem() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        BoxAndWhiskerItem item = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        dataset.add(item, "Row", "Column");
        assertEquals(1.0, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(6.0, dataset.getMaximumRangeValue(), 0.0);
    }

    @Test
    void testUpdateBounds_MultipleItems() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(7.0, 8.0, 9.0, 10.0, 11.0, 12.0);
        dataset.add(item1, "Row1", "Column1");
        dataset.add(item2, "Row2", "Column2");
        assertEquals(1.0, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(12.0, dataset.getMaximumRangeValue(), 0.0);
    }

    @Test
    void testUpdateBounds_NullItem() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        dataset.add(null, "Row", "Column");
        assertEquals(Double.NaN, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(Double.NaN, dataset.getMaximumRangeValue(), 0.0);
    }

    @Test
    void testUpdateBounds_NaNItem() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        BoxAndWhiskerItem item = new BoxAndWhiskerItem(Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
        dataset.add(item, "Row", "Column");
        assertEquals(Double.NaN, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(Double.NaN, dataset.getMaximumRangeValue(), 0.0);
    }
}
