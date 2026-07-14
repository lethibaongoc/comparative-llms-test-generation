@Test
void testRemoveRow_shouldRemoveExistingRow() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey = "Row1";
    keyedObjects.addRow(rowKey);
    keyedObjects.removeRow(rowKey);
    assertEquals(-1, keyedObjects.getRowIndex(rowKey));
}

@Test
void testRemoveRow_shouldThrowUnknownKeyExceptionWhenRowKeyNotFound() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey = "NonExistentRow";
    assertThrows(UnknownKeyException.class, () -> keyedObjects.removeRow(rowKey));
}

@Test
void testRemoveRow_shouldThrowUnknownKeyExceptionWithCorrectMessage() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey = "MissingRow";
    UnknownKeyException exception = assertThrows(UnknownKeyException.class,
        () -> keyedObjects.removeRow(rowKey));
    assertTrue(exception.getMessage().contains("Row key (" + rowKey + ") not recognised."));
}

@Test
void testRemoveRow_shouldHandleNullRowKey() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    assertThrows(UnknownKeyException.class, () -> keyedObjects.removeRow(null));
}

@Test
void testRemoveRow_shouldMaintainOtherRowsAfterRemoval() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey1 = "Row1";
    Comparable rowKey2 = "Row2";
    keyedObjects.addRow(rowKey1);
    keyedObjects.addRow(rowKey2);
    keyedObjects.removeRow(rowKey1);
    assertEquals(0, keyedObjects.getRowIndex(rowKey2));
    assertEquals(-1, keyedObjects.getRowIndex(rowKey1));
}

@Test
void testRemoveRow_shouldWorkWithDifferentComparableTypes() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey = Integer.valueOf(123);
    keyedObjects.addRow(rowKey);
    keyedObjects.removeRow(rowKey);
    assertEquals(-1, keyedObjects.getRowIndex(rowKey));
}
