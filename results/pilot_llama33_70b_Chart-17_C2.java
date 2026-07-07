package org.jfree.data.time;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimeSeriesTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        TimeSeries original = new TimeSeries("Test Series");
        original.add(new RegularTimePeriod(), 10.0);
        original.add(new RegularTimePeriod(), 20.0);

        TimeSeries clone = (TimeSeries) original.clone();

        // Check that the clone is not the same object as the original
        assertNotSame(original, clone);

        // Check that the clone has the same data as the original
        assertEquals(original.getItemCount(), clone.getItemCount());
        for (int i = 0; i < original.getItemCount(); i++) {
            TimeSeriesDataItem originalItem = original.getRawDataItem(i);
            TimeSeriesDataItem cloneItem = clone.getRawDataItem(i);
            assertEquals(originalItem.getValue(), cloneItem.getValue());
        }

        // Check that modifying the clone does not affect the original
        clone.add(new RegularTimePeriod(), 30.0);
        assertNotEquals(original.getItemCount(), clone.getItemCount());
    }

    @Test
    void testCloneEmptySeries() throws CloneNotSupportedException {
        TimeSeries original = new TimeSeries("Test Series");
        TimeSeries clone = (TimeSeries) original.clone();

        // Check that the clone is not the same object as the original
        assertNotSame(original, clone);

        // Check that the clone has the same data as the original
        assertEquals(original.getItemCount(), clone.getItemCount());
    }
}
