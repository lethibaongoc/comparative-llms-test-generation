@Test
void removeRow_WhenKeyExists_ShouldCallRemoveRowWithIndex() {
    KeyedObjects2D keyedObjects = Mockito.spy(new KeyedObjects2D());
    Comparable rowKey = "existingRow";

    Mockito.doReturn(5).when(keyedObjects).getRowIndex(rowKey);
    Mockito.doNothing().when(keyedObjects).removeRow(5);

    keyedObjects.removeRow(rowKey);

    Mockito.verify(keyedObjects).getRowIndex(rowKey);
    Mockito.verify(keyedObjects).removeRow(5);
}

@Test
void removeRow_WhenKeyDoesNotExist_ShouldThrowUnknownKeyException() {
    KeyedObjects2D keyedObjects = Mockito.spy(new KeyedObjects2D());
    Comparable rowKey = "unknownRow";

    Mockito.doReturn(-1).when(keyedObjects).getRowIndex(rowKey);

    UnknownKeyException exception = assertThrows(UnknownKeyException.class, () -> {
        keyedObjects.removeRow(rowKey);
    });

    assertEquals("Row key (unknownRow) not recognised.", exception.getMessage());
    Mockito.verify(keyedObjects, Mockito.never()).removeRow(Mockito.anyInt());
}
