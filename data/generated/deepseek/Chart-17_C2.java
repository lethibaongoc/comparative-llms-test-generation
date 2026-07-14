@Test
void testClone() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = Arrays.asList("item1", "item2", "item3");
    TimeSeries cloned = (TimeSeries) original.clone();
    assertNotSame(original, cloned);
    assertNotSame(original.data, cloned.data);
    assertEquals(original.data, cloned.data);
}

@Test
void testCloneWithEmptyData() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = new ArrayList<>();
    TimeSeries cloned = (TimeSeries) original.clone();
    assertNotSame(original, cloned);
    assertNotSame(original.data, cloned.data);
    assertTrue(cloned.data.isEmpty());
}

@Test
void testCloneWithNullData() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = null;
    TimeSeries cloned = (TimeSeries) original.clone();
    assertNotSame(original, cloned);
    assertNull(cloned.data);
}

@Test
void testCloneModifyOriginalDoesNotAffectClone() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = new ArrayList<>(Arrays.asList("A", "B"));
    TimeSeries cloned = (TimeSeries) original.clone();
    original.data.add("C");
    assertEquals(2, cloned.data.size());
    assertFalse(cloned.data.contains("C"));
}

@Test
void testCloneModifyCloneDoesNotAffectOriginal() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = new ArrayList<>(Arrays.asList("A", "B"));
    TimeSeries cloned = (TimeSeries) original.clone();
    cloned.data.add("C");
    assertEquals(2, original.data.size());
    assertFalse(original.data.contains("C"));
}

@Test
void testCloneWithComplexObjects() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    List<Map<String, Integer>> complexData = new ArrayList<>();
    Map<String, Integer> map = new HashMap<>();
    map.put("value", 42);
    complexData.add(map);
    original.data = complexData;
    TimeSeries cloned = (TimeSeries) original.clone();
    assertNotSame(original.data.get(0), cloned.data.get(0));
    assertEquals(original.data.get(0), cloned.data.get(0));
}

@Test
void testCloneMaintainsDataOrder() throws CloneNotSupportedException {
    TimeSeries original = new TimeSeries();
    original.data = Arrays.asList("first", "second", "third");
    TimeSeries cloned = (TimeSeries) original.clone();
    assertEquals("first", cloned.data.get(0));
    assertEquals("second", cloned.data.get(1));
    assertEquals("third", cloned.data.get(2));
}
