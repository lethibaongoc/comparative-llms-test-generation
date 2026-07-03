package org.apache.commons.lang3;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClassUtilsTest {

    @Test
    void testToClass() {
        Object[] array = new Object[] { "foo", 1, null };
        Class<?>[] expected = new Class<?>[] { String.class, Integer.class, null };
        assertArrayEquals(expected, ClassUtils.toClass(array));
    }

    @Test
    void testToClassEmptyArray() {
        Object[] array = new Object[0];
        Class<?>[] expected = ClassUtils.EMPTY_CLASS_ARRAY;
        assertArrayEquals(expected, ClassUtils.toClass(array));
    }

    @Test
    void testToClassNullArray() {
        Object[] array = null;
        assertNull(ClassUtils.toClass(array));
    }
}
