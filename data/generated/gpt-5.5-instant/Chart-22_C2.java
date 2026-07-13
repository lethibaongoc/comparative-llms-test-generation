@Test
public void testRemoveRow_existingKey() {
    KeyedObjects2D data = new KeyedObjects2D();
    data.addObject("Value1", "Row1", "Column1");
    data.addObject("Value2", "Row2", "Column1");

    data.removeRow("Row1");

    assertEquals(1, data.getRowCount());
    assertEquals("Row2", data.getRowKey(0));
    assertEquals("Value2", data.getObject("Row2", "Column1"));
}

@Test
public void testRemoveRow_removesCorrectRow() {
    KeyedObjects2D data = new KeyedObjects2D();
    data.addObject("Value1", "Row1", "Column1");
    data.addObject("Value2", "Row2", "Column1");
    data.addObject("Value3", "Row3", "Column1");

    data.removeRow("Row2");

    assertEquals(2, data.getRowCount());
    assertEquals("Row1", data.getRowKey(0));
    assertEquals("Row3", data.getRowKey(1));
    assertEquals(-1, data.getRowIndex("Row2"));
}

@Test
public void testRemoveRow_unknownKeyThrowsException() {
    KeyedObjects2D data = new KeyedObjects2D();
    data.addObject("Value1", "Row1", "Column1");

    UnknownKeyException exception = assertThrows(
        UnknownKeyException.class,
        () -> data.removeRow("MissingRow")
    );

    assertEquals("Row key (MissingRow) not recognised.", exception.getMessage());
    assertEquals(1, data.getRowCount());
}
