```java
@Test
void testEquals_Reflexive() {
    // Assuming PlotOrientation enum exists: enum PlotOrientation { VERTICAL, HORIZONTAL }
    // Assuming XYPlot class has a constructor: public XYPlot(int weight, PlotOrientation orientation)
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    XYPlot plot = new XYPlot(10, orientation);
    assertTrue(plot.equals(plot), "A plot should be equal to itself (reflexive)");
}

@Test
void testEquals_Null() {
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    XYPlot plot = new XYPlot(10, orientation);
    assertFalse(plot.equals(null), "A plot should not be equal to null");
}

@Test
void testEquals_DifferentClass() {
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    XYPlot plot = new XYPlot(10, orientation);
    Object other = new Object(); // An object of a different class
    assertFalse(plot.equals(other), "A plot should not be equal to an object of a different class");
}

@Test
void testEquals_SameValues() {
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    XYPlot plot1 = new XYPlot(10, orientation);
    XYPlot plot2 = new XYPlot(10, orientation); // Same weight and orientation
    assertTrue(plot1.equals(plot2), "Plots with identical weight and orientation should be equal");
    assertTrue(plot2.equals(plot1), "Equality should be symmetric for identical plots");
}

@Test
void testEquals_DifferentWeight() {
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    XYPlot plot1 = new XYPlot(10, orientation);
    XYPlot plot2 = new XYPlot(20, orientation); // Different weight
    assertFalse(plot1.equals(plot2), "Plots with different weight should not be equal");
    assertFalse(plot2.equals(plot1), "Inequality should be symmetric for plots with different weight");
}

@Test
void testEquals_DifferentOrientation() {
    PlotOrientation orientation1 = PlotOrientation.VERTICAL;
    PlotOrientation orientation2 = PlotOrientation.HORIZONTAL; // Assuming another enum value
    XYPlot plot1 = new XYPlot(10, orientation1);
    XYPlot plot2 = new XYPlot(10, orientation2); // Different orientation
    assertFalse(plot1.equals(plot2), "Plots with different orientation should not be equal");
    assertFalse(plot2.equals(plot1), "Inequality should be symmetric for plots with different orientation");
}

@Test
void testEquals_DifferentWeightAndOrientation() {
    PlotOrientation orientation1 = PlotOrientation.VERTICAL;
    PlotOrientation orientation2 = PlotOrientation.HORIZONTAL;
    XYPlot plot1 = new XYPlot(10, orientation1);
    XYPlot plot2 = new XYPlot(20, orientation2); // Different weight and orientation
    assertFalse(plot1.equals(plot2), "Plots with different weight and orientation should not be equal");
    assertFalse(plot2.equals(plot1), "Inequality should be symmetric for plots with different weight and orientation");
}

@Test
void testEquals_Symmetric() {
    PlotOrientation orientation1 = PlotOrientation.VERTICAL;
    PlotOrientation orientation2 = PlotOrientation.HORIZONTAL;

    // Test symmetric equality
    XYPlot p1 = new XYPlot(10, orientation1);
    XYPlot p2 = new XYPlot(10, orientation1);
    assertTrue(p1.equals(p2), "p1.equals(p2) should be true");
    assertTrue(p2.equals(p1), "p2.equals(p1) should be true (symmetry)");

    // Test symmetric inequality (different weight)
    XYPlot p3 = new XYPlot(20, orientation1);
    assertFalse(p1.equals(p3), "p1.equals(p3) should be false");
    assertFalse(p3.equals(p1), "p3.equals(p1) should be false (symmetry)");

    // Test symmetric inequality (different orientation)
    XYPlot p4 = new XYPlot(10, orientation2);
    assertFalse(p1.equals(p4), "p1.equals(p4) should be false");
    assertFalse(p4.equals(p1), "p4.equals(p1) should be false (symmetry)");
}

@Test
void testEquals_Transitive() {
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    XYPlot p1 = new XYPlot(10, orientation);
    XYPlot p2 = new XYPlot(10, orientation);
    XYPlot p3 = new XYPlot(10, orientation);

    assertTrue(p1.equals(p2), "p1 equals p2");
    assertTrue(p2.equals(p3), "p2 equals p3");
    assertTrue(p1.equals(p3), "p1 equals p3 (transitivity)");

    // Also test with unequal objects
    XYPlot p4 = new XYPlot(20, orientation);
    assertFalse(p1.equals(p4), "p1 does not equal p4");
    assertFalse(p2.equals(p4), "p2 does not equal p4");
}

@Test
void testEquals_Consistency() {
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    XYPlot plot1 = new XYPlot(10, orientation);
    XYPlot plot2 = new XYPlot(10, orientation);
    XYPlot plot3 = new XYPlot(20, orientation);

    // Consistent true
    assertTrue(plot1.equals(plot2), "First call for equal objects should be true");
    assertTrue(plot1.equals(plot2), "Second call for equal objects should be true (consistency)");
    assertTrue(plot1.equals(plot2), "Third call for equal objects should be true (consistency)");

    // Consistent false
    assertFalse(plot1.equals(plot3), "First call for unequal objects should be false");
    assertFalse(plot1.equals(plot3), "Second call for unequal objects should be false (consistency)");
    assertFalse(plot1.equals(plot3), "Third call for unequal objects should be false (consistency)");
}
```