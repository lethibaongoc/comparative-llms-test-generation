@Test
public void testRemoveRow_ExistingRowKey_RemovesRow() {
    KeyedObjects2D obj = new KeyedObjects2D();
    obj.addObject("v1", "row1", "col1");
    obj.addObject("v2", "row2", "col1");

    obj.removeRow("row1");

    assertEquals(-1, obj.getRowIndex("row1"));
    assertEquals(1, obj.getRowCount());
    assertEquals("v2", obj.getObject("row2", "col1"));
}

@Test
public void testRemoveRow_LastRemainingRow() {
    KeyedObjects2D obj = new KeyedObjects2D();
    obj.addObject("value", "row1", "col1");

    obj.removeRow("row1");

    assertEquals(0, obj.getRowCount());
    assertEquals(-1, obj.getRowIndex("row1"));
}

@Test
public void testRemoveRow_UnknownRowKey_ThrowsException() {
    KeyedObjects2D obj = new KeyedObjects2D();
    obj.addObject("value", "row1", "col1");

    assertThrows(UnknownKeyException.class, () -> obj.removeRow("missingRow"));
}

@Test
public void testRemoveRow_NullRowKey_ThrowsException() {
    KeyedObjects2D obj = new KeyedObjects2D();
    obj.addObject("value", "row1", "col1");

    assertThrows(UnknownKeyException.class, () -> obj.removeRow(null));
}
