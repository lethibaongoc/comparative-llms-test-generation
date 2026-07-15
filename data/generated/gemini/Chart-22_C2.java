@Test
void testRemoveRowWithValidKey() {
    KeyedObjects2D data = new KeyedObjects2D();
    data.addObject("Value 1", "Row 1", "Col 1");
    data.addObject("Value 2", "Row 2", "Col 1");

    data.removeRow("Row 1");

    assertEquals(-1, data.getRowIndex("Row 1"));
    assertEquals(0, data.getRowIndex("Row 2"));
}

@Test
void testRemoveRowWithUnknownKeyThrowsException() {
    KeyedObjects2D data = new KeyedObjects2D();
    data.addObject("Value 1", "Row 1", "Col 1");

    assertThrows(UnknownKeyException.class, () -> {
        data.removeRow("NonExistentRow");
    });
}

@Test
void testRemoveRowWithNullKeyThrowsException() {
    KeyedObjects2D data = new KeyedObjects2D();

    assertThrows(UnknownKeyException.class, () -> {
        data.removeRow(null);
    });
}
