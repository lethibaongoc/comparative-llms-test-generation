@Test
void testGetPackageName() {
    // Null and empty string cases
    assertEquals("", ClassUtils.getPackageName(null));
    assertEquals("", ClassUtils.getPackageName(""));

    // Standard class names
    assertEquals("java.lang", ClassUtils.getPackageName("java.lang.String"));
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName("org.apache.commons.lang3.ClassUtils"));

    // Class without a package
    assertEquals("", ClassUtils.getPackageName("GlobalClass"));

    // Array encoding stripping ([)
    assertEquals("java.lang", ClassUtils.getPackageName("[java.lang.String"));
    assertEquals("java.lang", ClassUtils.getPackageName("[[java.lang.String"));

    // Object type encoding stripping (L...;)
    assertEquals("java.lang", ClassUtils.getPackageName("Ljava.lang.String;"));

    // Combined array and object type encoding stripping
    assertEquals("java.lang", ClassUtils.getPackageName("[Ljava.lang.String;"));
    assertEquals("java.lang", ClassUtils.getPackageName("[[Ljava.lang.String;"));
}
