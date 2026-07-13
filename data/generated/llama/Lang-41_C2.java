package org.apache.commons.lang;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
public class ClassUtilsTest {

    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    @Test
    void testGetPackageName() {
        assertEquals("java.lang", ClassUtils.getPackageName("java.lang.String"));
        assertEquals("java.lang", ClassUtils.getPackageName("[Ljava.lang.String;"));
        assertEquals("java.lang", ClassUtils.getPackageName("Ljava.lang.String;"));
        assertEquals("java.lang", ClassUtils.getPackageName("[[Ljava.lang.String;"));
        assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(null));
        assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(""));
        assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("String"));
    }
}
