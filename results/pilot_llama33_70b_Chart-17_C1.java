package org.jfree.data.time;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Day;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimeSeriesTest {

    @Test
    public void testClone() throws CloneNotSupportedException {
        TimeSeries original = new TimeSeries("Test Series");
        original.add(new Day(1, 1, 2022), 10.0);
        original.add(new Day(2, 1, 2022), 20.0);

        TimeSeries clone = (TimeSeries) original.clone();

        // Check that the clone is not the same object as the original
        assertNotSame(original, clone);

        // Check that the clone has the same data as the original
        assertEquals(original.getItemCount(), clone.getItemCount());
        for (int i = 0; i < original.getItemCount(); i++) {
            TimeSeriesDataItem originalItem = original.getRawDataItem(i);
            TimeSeriesDataItem cloneItem = clone.getRawDataItem(i);
            assertEquals(originalItem.getPeriod(), cloneItem.getPeriod());
            assertEquals(originalItem.getValue(), cloneItem.getValue(), 0.0);
        }

        // Check that modifying the clone does not affect the original
        clone.add(new Day(3, 1, 2022), 30.0);
        assertNotEquals(original.getItemCount(), clone.getItemCount());
    }

    @Test
    public void testCloneEmptySeries() throws CloneNotSupportedException {
        TimeSeries original = new TimeSeries("Test Series");
        TimeSeries clone = (TimeSeries) original.clone();

        // Check that the clone is not the same object as the original
        assertNotSame(original, clone);

        // Check that the clone has the same data as the original
        assertEquals(original.getItemCount(), clone.getItemCount());
    }

    @Test
    public void testCloneSingleItemSeries() throws CloneNotSupportedException {
        TimeSeries original = new TimeSeries("Test Series");
        original.add(new Day(1, 1, 2022), 10.0);
        TimeSeries clone = (TimeSeries) original.clone();

        // Check that the clone is not the same object as the original
        assertNotSame(original, clone);

        // Check that the clone has the same data as the original
        assertEquals(original.getItemCount(), clone.getItemCount());
        TimeSeriesDataItem originalItem = original.getRawDataItem(0);
        TimeSeriesDataItem cloneItem = clone.getRawDataItem(0);
        assertEquals(originalItem.getPeriod(), cloneItem.getPeriod());
        assertEquals(originalItem.getValue(), cloneItem.getValue(), 0.0);
    }
}
