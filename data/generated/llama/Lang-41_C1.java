package org.apache.commons.lang;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClassUtilsTest {

    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    @Test
    public void testGetPackageNameNull() {
        assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(null));
    }

    @Test
    public void testGetPackageNameEmpty() {
        assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(""));
    }

    @Test
    public void testGetPackageNameNoPackage() {
        assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("ClassName"));
    }

    @Test
    public void testGetPackageNameWithPackage() {
        assertEquals("org.apache.commons.lang", ClassUtils.getPackageName("org.apache.commons.lang.ClassName"));
    }

    @Test
    public void testGetPackageNameWithArrayEncoding() {
        assertEquals("org.apache.commons.lang", ClassUtils.getPackageName("[org.apache.commons.lang.ClassName"));
    }

    @Test
    public void testGetPackageNameWithObjectTypeEncoding() {
        assertEquals("org.apache.commons.lang", ClassUtils.getPackageName("Lorg.apache.commons.lang.ClassName;"));
    }

    @Test
    public void testGetPackageNameWithArrayAndObjectTypeEncoding() {
        assertEquals("org.apache.commons.lang", ClassUtils.getPackageName("[Lorg.apache.commons.lang.ClassName;"));
    }
}
