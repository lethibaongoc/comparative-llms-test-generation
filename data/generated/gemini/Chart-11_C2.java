```java
// Assumes XYPlot class has a constructor like:
// public XYPlot(int weight, int orientation) {
//     this.weight = weight;
//     this.orientation = orientation;
// }
// where 'weight' and 'orientation' are fields of type 'int'.

@Test
void testEquals_sameObjectReference() {
    XYPlot plot = new XYPlot(10, 1);
    assertTrue(plot.equals(plot));
}

@Test
void testEquals_nullObject() {
    XYPlot plot = new XYPlot(10, 1);
    assertFalse(plot.equals(null));
}

@Test
void testEquals_differentClass() {
    XYPlot plot = new XYPlot(10, 1);
    Object differentClassObj = new Object();
    assertFalse(plot.equals(differentClassObj));
}

@Test
void testEquals_equalValues() {
    XYPlot plot1 = new XYPlot(10, 1);
    XYPlot plot2 = new XYPlot(10, 1);
    assertTrue(plot1.equals(plot2));
}

@Test
void testEquals_differentWeight() {
    XYPlot plot1 = new XYPlot(10, 1);
    XYPlot plot2 = new XYPlot(20, 1); // Weight differs
    assertFalse(plot1.equals(plot2));
}

@Test
void testEquals_differentOrientation() {
    XYPlot plot1 = new XYPlot(10, 1);
    XYPlot plot2 = new XYPlot(10, 2); // Orientation differs
    assertFalse(plot1.equals(plot2));
}

@Test
void testEquals_differentWeightAndOrientation() {
    XYPlot plot1 = new XYPlot(10, 1);
    XYPlot plot2 = new XYPlot(20, 2); // Both differ
    assertFalse(plot1.equals(plot2));
}

@Test
void testEquals_symmetryProperty() {
    XYPlot plot1 = new XYPlot(10, 1);
    XYPlot plot2 = new XYPlot(10, 1);
    assertTrue(plot1.equals(plot2));
    assertTrue(plot2.equals(plot1));
}

@Test
void testEquals_transitivityProperty() {
    XYPlot plot1 = new XYPlot(10, 1);
    XYPlot plot2 = new XYPlot(10, 1);
    XYPlot plot3 = new XYPlot(10, 1);
    assertTrue(plot1.equals(plot2));
    assertTrue(plot2.equals(plot3));
    assertTrue(plot1.equals(plot3));
}

@Test
void testEquals_consistencyProperty() {
    XYPlot plot1 = new XYPlot(10, 1);
    XYPlot plot2 = new XYPlot(10, 1);
    XYPlot plot3 = new XYPlot(20, 1);

    assertTrue(plot1.equals(plot2));
    assertTrue(plot1.equals(plot2)); // Repeated call
    assertFalse(plot1.equals(plot3));
    assertFalse(plot1.equals(plot3)); // Repeated call
}
```