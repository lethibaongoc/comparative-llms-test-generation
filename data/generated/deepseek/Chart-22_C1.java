@Test
void shouldRemoveRowWhenRowKeyExists() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey = "Row1";
    keyedObjects.addRow(rowKey);
    keyedObjects.removeRow(rowKey);
    assertEquals(-1, keyedObjects.getRowIndex(rowKey));
}

@Test
void shouldThrowUnknownKeyExceptionWhenRowKeyDoesNotExist() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey = "NonExistentRow";
    UnknownKeyException exception = assertThrows(UnknownKeyException.class, () -> {
        keyedObjects.removeRow(rowKey);
    });
    assertTrue(exception.getMessage().contains("Row key (" + rowKey + ") not recognised."));
}

@Test
void shouldThrowUnknownKeyExceptionWhenRowKeyIsNull() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    assertThrows(UnknownKeyException.class, () -> {
        keyedObjects.removeRow(null);
    });
}

@Test
void shouldSuccessfullyRemoveFirstRowWhenMultipleRowsExist() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey1 = "Row1";
    Comparable rowKey2 = "Row2";
    keyedObjects.addRow(rowKey1);
    keyedObjects.addRow(rowKey2);
    keyedObjects.removeRow(rowKey1);
    assertEquals(-1, keyedObjects.getRowIndex(rowKey1));
    assertEquals(0, keyedObjects.getRowIndex(rowKey2));
}

@Test
void shouldSuccessfullyRemoveLastRowWhenMultipleRowsExist() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey1 = "Row1";
    Comparable rowKey2 = "Row2";
    keyedObjects.addRow(rowKey1);
    keyedObjects.addRow(rowKey2);
    keyedObjects.removeRow(rowKey2);
    assertEquals(0, keyedObjects.getRowIndex(rowKey1));
    assertEquals(-1, keyedObjects.getRowIndex(rowKey2));
}

@Test
void shouldMaintainRowCountAfterRemoval() {
    KeyedObjects2D keyedObjects = new KeyedObjects2D();
    Comparable rowKey1 = "Row1";
    Comparable rowKey2 = "Row2";
    Comparable rowKey3 = "Row3";
    keyedObjects.addRow(rowKey1);
    keyedObjects.addRow(rowKey2);
    keyedObjects.addRow(rowKey3);
    keyedObjects.removeRow(rowKey2);
    assertEquals(2, keyedObjects.getRowCount());
    assertEquals(0, keyedObjects.getRowIndex(rowKey1));
    assertEquals(-1, keyedObjects.getRowIndex(rowKey2));
    assertEquals(1, keyedObjects.getRowIndex(rowKey3));
}
