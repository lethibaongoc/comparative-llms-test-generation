@Test
void testClone_ShouldReturnIdenticalCopy() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = Arrays.asList(1, 2, 3);
    TimeSeries cloned = (TimeSeries) original.clone();
    assertNotNull(cloned);
    assertNotSame(original, cloned);
    assertEquals(original.data, cloned.data);
    assertNotSame(original.data, cloned.data);
}

@Test
void testClone_WithEmptyData() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = new ArrayList<>();
    TimeSeries cloned = (TimeSeries) original.clone();
    assertNotNull(cloned);
    assertNotSame(original, cloned);
    assertTrue(cloned.data.isEmpty());
    assertNotSame(original.data, cloned.data);
}

@Test
void testClone_WithNullData() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = null;
    TimeSeries cloned = (TimeSeries) original.clone();
    assertNotNull(cloned);
    assertNotSame(original, cloned);
    assertNull(cloned.data);
}

@Test
void testClone_WithComplexData() throws CloneNotSupportedException {
    List<Map<String, Integer>> complexData = new ArrayList<>();
    Map<String, Integer> map1 = new HashMap<>();
    map1.put("value", 10);
    complexData.add(map1);
    TimeSeries original = new TimeSeries();
    original.data = complexData;
    TimeSeries cloned = (TimeSeries) original.clone();
    assertNotSame(original.data, cloned.data);
    assertNotSame(original.data.get(0), cloned.data.get(0));
    assertEquals(original.data.get(0).get("value"), cloned.data.get(0).get("value"));
}
