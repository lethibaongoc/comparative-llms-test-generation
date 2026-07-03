package org.apache.commons.lang3;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClassUtilsTest {

    @Test
    void testToClass_NullArray() {
        assertNull(ClassUtils.toClass(null));
    }

    @Test
    void testToClass_EmptyArray() {
        assertArrayEquals(new Class<?>[0], ClassUtils.toClass(new Object[0]));
    }

    @Test
    void testToClass_ArrayWithNullElements() {
        Object[] array = new Object[] { null, "hello", null };
        Class<?>[] expected = new Class<?>[] { null, String.class, null };
        assertArrayEquals(expected, ClassUtils.toClass(array));
    }

    @Test
    void testToClass_ArrayWithNonNullElements() {
        Object[] array = new Object[] { "hello", 123, true };
        Class<?>[] expected = new Class<?>[] { String.class, Integer.class, Boolean.class };
        assertArrayEquals(expected, ClassUtils.toClass(array));
    }
}
