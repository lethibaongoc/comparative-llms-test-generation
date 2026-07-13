package org.jfree.data.statistics;

import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultBoxAndWhiskerCategoryDatasetTest {

    @Test
    public void testUpdateBounds_EmptyDataset() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        assertEquals(Double.NaN, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(Double.NaN, dataset.getMaximumRangeValue(), 0.0);
    }

    @Test
    public void testUpdateBounds_SingleItem() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        BoxAndWhiskerItem item = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, "row", "column");
        dataset.add(item, "row", "column");
        assertEquals(1.0, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(5.0, dataset.getMaximumRangeValue(), 0.0);
    }

    @Test
    public void testUpdateBounds_MultipleItems() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, "row1", "column1");
        BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(6.0, 7.0, 8.0, 9.0, 10.0, "row2", "column2");
        dataset.add(item1, "row1", "column1");
        dataset.add(item2, "row2", "column2");
        assertEquals(1.0, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(10.0, dataset.getMaximumRangeValue(), 0.0);
    }

    @Test
    public void testUpdateBounds_NaNValues() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        BoxAndWhiskerItem item = new BoxAndWhiskerItem(Double.NaN, 2.0, 3.0, 4.0, 5.0, "row", "column");
        dataset.add(item, "row", "column");
        assertEquals(2.0, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(5.0, dataset.getMaximumRangeValue(), 0.0);
    }

    @Test
    public void testUpdateBounds_NullValues() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        BoxAndWhiskerItem item = new BoxAndWhiskerItem(null, 2.0, 3.0, 4.0, 5.0, "row", "column");
        dataset.add(item, "row", "column");
        assertEquals(2.0, dataset.getMinimumRangeValue(), 0.0);
        assertEquals(5.0, dataset.getMaximumRangeValue(), 0.0);
    }
}
