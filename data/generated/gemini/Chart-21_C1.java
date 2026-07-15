@Test
void testUpdateBoundsWithEmptyDataset() throws Exception {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    // Invoke private updateBounds method
    java.lang.reflect.Method updateBoundsMethod = DefaultBoxAndWhiskerCategoryDataset.class
            .getDeclaredMethod("updateBounds");
    updateBoundsMethod.setAccessible(true);
    updateBoundsMethod.invoke(dataset);

    // Verify fields are reset to default NaN and -1 values using reflection
    java.lang.reflect.Field minValField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValue");
    java.lang.reflect.Field maxValField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValue");
    java.lang.reflect.Field minRowField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValueRow");
    java.lang.reflect.Field minColField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValueColumn");
    java.lang.reflect.Field maxRowField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValueRow");
    java.lang.reflect.Field maxColField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValueColumn");

    minValField.setAccessible(true);
    maxValField.setAccessible(true);
    minRowField.setAccessible(true);
    minColField.setAccessible(true);
    maxRowField.setAccessible(true);
    maxColField.setAccessible(true);

    assertTrue(Double.isNaN((Double) minValField.get(dataset)));
    assertTrue(Double.isNaN((Double) maxValField.get(dataset)));
    assertEquals(-1, minRowField.get(dataset));
    assertEquals(-1, minColField.get(dataset));
    assertEquals(-1, maxRowField.get(dataset));
    assertEquals(-1, maxColField.get(dataset));
}

@Test
void testUpdateBoundsWithSingleValidItem() throws Exception {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    // Create a mock/stub item or concrete BoxAndWhiskerItem depending on classpath availability.
    // Assuming standard BoxAndWhiskerItem constructor:
    // BoxAndWhiskerItem(Number mean, Number median, Number q1, Number q3, Number minRegularValue, Number maxRegularValue, Number minOutlier, Number maxOutlier, List outliers)
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(10.0, 10.0, 8.0, 12.0, 5.0, 15.0, 2.0, 18.0, new java.util.ArrayList<>());
    dataset.add(item, "Row1", "Col1");

    java.lang.reflect.Method updateBoundsMethod = DefaultBoxAndWhiskerCategoryDataset.class
            .getDeclaredMethod("updateBounds");
    updateBoundsMethod.setAccessible(true);
    updateBoundsMethod.invoke(dataset);

    java.lang.reflect.Field minValField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValue");
    java.lang.reflect.Field maxValField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValue");
    java.lang.reflect.Field minRowField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValueRow");
    java.lang.reflect.Field minColField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValueColumn");
    java.lang.reflect.Field maxRowField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValueRow");
    java.lang.reflect.Field maxColField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValueColumn");

    minValField.setAccessible(true);
    maxValField.setAccessible(true);
    minRowField.setAccessible(true);
    minColField.setAccessible(true);
    maxRowField.setAccessible(true);
    maxColField.setAccessible(true);

    assertEquals(2.0, (Double) minValField.get(dataset), 0.00001);
    assertEquals(18.0, (Double) maxValField.get(dataset), 0.00001);
    assertEquals(0, minRowField.get(dataset));
    assertEquals(0, minColField.get(dataset));
    assertEquals(0, maxRowField.get(dataset));
    assertEquals(0, maxColField.get(dataset));
}

@Test
void testUpdateBoundsFiltersNaNAndNullOutliers() throws Exception {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    // Item with NaN for minOutlier and null for maxOutlier
    BoxAndWhiskerItem item = new BoxAndWhiskerItem(10.0, 10.0, 8.0, 12.0, 5.0, 15.0, Double.NaN, null, new java.util.ArrayList<>());
    dataset.add(item, "Row1", "Col1");

    java.lang.reflect.Method updateBoundsMethod = DefaultBoxAndWhiskerCategoryDataset.class
            .getDeclaredMethod("updateBounds");
    updateBoundsMethod.setAccessible(true);
    updateBoundsMethod.invoke(dataset);

    java.lang.reflect.Field minValField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValue");
    java.lang.reflect.Field maxValField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValue");

    minValField.setAccessible(true);
    maxValField.setAccessible(true);

    assertTrue(Double.isNaN((Double) minValField.get(dataset)));
    assertTrue(Double.isNaN((Double) maxValField.get(dataset)));
}

@Test
void testUpdateBoundsFindsAbsoluteMinAndMaxAcrossMultipleItems() throws Exception {
    DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

    BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(10.0, 10.0, 8.0, 12.0, 5.0, 15.0, 5.0, 12.0, new java.util.ArrayList<>());
    BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(10.0, 10.0, 8.0, 12.0, 5.0, 15.0, 1.0, 25.0, new java.util.ArrayList<>());
    BoxAndWhiskerItem item3 = new BoxAndWhiskerItem(10.0, 10.0, 8.0, 12.0, 5.0, 15.0, 3.0, 20.0, new java.util.ArrayList<>());

    dataset.add(item1, "Row1", "Col1"); // Index 0, 0
    dataset.add(item2, "Row1", "Col2"); // Index 0, 1 -> Holds absolute Min (1.0) and absolute Max (25.0)
    dataset.add(item3, "Row2", "Col1"); // Index 1, 0

    java.lang.reflect.Method updateBoundsMethod = DefaultBoxAndWhiskerCategoryDataset.class
            .getDeclaredMethod("updateBounds");
    updateBoundsMethod.setAccessible(true);
    updateBoundsMethod.invoke(dataset);

    java.lang.reflect.Field minValField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValue");
    java.lang.reflect.Field maxValField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValue");
    java.lang.reflect.Field minRowField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValueRow");
    java.lang.reflect.Field minColField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("minimumRangeValueColumn");
    java.lang.reflect.Field maxRowField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValueRow");
    java.lang.reflect.Field maxColField = DefaultBoxAndWhiskerCategoryDataset.class.getDeclaredField("maximumRangeValueColumn");

    minValField.setAccessible(true);
    maxValField.setAccessible(true);
    minRowField.setAccessible(true);
    minColField.setAccessible(true);
    maxRowField.setAccessible(true);
    maxColField.setAccessible(true);

    assertEquals(1.0, (Double) minValField.get(dataset), 0.00001);
    assertEquals(25.0, (Double) maxValField.get(dataset), 0.00001);
    assertEquals(0, minRowField.get(dataset));
    assertEquals(1, minColField.get(dataset));
    assertEquals(0, maxRowField.get(dataset));
    assertEquals(1, maxColField.get(dataset));
}
