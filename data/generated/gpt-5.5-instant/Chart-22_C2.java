@Test
void testRemoveRowWithExistingRowKey() {
    KeyedObjects2D data = new KeyedObjects2D();
    data.addObject("value1", "row1", "column1");
    data.addObject("value2", "row2", "column1");

    data.removeRow("row1");

    assertEquals(-1, data.getRowIndex("row1"));
    assertEquals(1, data.getRowCount());
    assertEquals("row2", data.getRowKey(0));
}

@Test
void testRemoveRowWithUnknownRowKeyThrowsException() {
    KeyedObjects2D data = new KeyedObjects2D();
    data.addObject("value1", "row1", "column1");

    assertThrows(UnknownKeyException.class, () -> data.removeRow("unknownRow"));
}
