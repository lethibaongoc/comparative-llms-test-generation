package org.jfree.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KeyedObjects2DTest {

    @Test
    void testRemoveRow_RecognisedKey() {
        KeyedObjects2D keyedObjects2D = new KeyedObjects2D();
        // Assuming addObject and getRowIndex are implemented correctly
        // For demonstration purposes, we'll assume a simple implementation
        keyedObjects2D.addObject("object", "rowKey", "columnKey");
        assertDoesNotThrow(() -> keyedObjects2D.removeRow("rowKey"));
    }

    @Test
    void testRemoveRow_UnrecognisedKey() {
        KeyedObjects2D keyedObjects2D = new KeyedObjects2D();
        UnknownKeyException exception = assertThrows(UnknownKeyException.class, () -> keyedObjects2D.removeRow("unrecognisedKey"));
        assertEquals("Row key (unrecognisedKey) not recognised.", exception.getMessage());
    }

    @Test
    void testRemoveRow_NullKey() {
        KeyedObjects2D keyedObjects2D = new KeyedObjects2D();
        assertThrows(NullPointerException.class, () -> keyedObjects2D.removeRow(null));
    }
}
