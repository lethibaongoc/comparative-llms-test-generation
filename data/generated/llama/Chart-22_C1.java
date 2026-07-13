package org.jfree.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KeyedObjects2DTest {

    @Test
    public void testRemoveRow_ExistingRowKey() {
        KeyedObjects2D keyedObjects2D = new KeyedObjects2D();
        keyedObjects2D.addObject("object", "rowKey", "columnKey");
        keyedObjects2D.removeRow("rowKey");
        assertEquals(0, keyedObjects2D.getRowCount());
    }

    @Test
    public void testRemoveRow_NonExistingRowKey() {
        KeyedObjects2D keyedObjects2D = new KeyedObjects2D();
        assertThrows(UnknownKeyException.class, () -> keyedObjects2D.removeRow("nonExistingRowKey"));
    }

    @Test
    public void testRemoveRow_NullRowKey() {
        KeyedObjects2D keyedObjects2D = new KeyedObjects2D();
        assertThrows(NullPointerException.class, () -> keyedObjects2D.removeRow(null));
    }

    @Test
    public void testRemoveRow_MultipleRows() {
        KeyedObjects2D keyedObjects2D = new KeyedObjects2D();
        keyedObjects2D.addObject("object1", "rowKey1", "columnKey");
        keyedObjects2D.addObject("object2", "rowKey2", "columnKey");
        keyedObjects2D.removeRow("rowKey1");
        assertEquals(1, keyedObjects2D.getRowCount());
    }
}
