@Test
void testGetPackageName() {
    assertEquals("", ClassUtils.getPackageName(null));
    assertEquals("", ClassUtils.getPackageName(""));
    assertEquals("", ClassUtils.getPackageName("String"));
    assertEquals("java.lang", ClassUtils.getPackageName("java.lang.String"));
}

@Test
void testGetPackageNameWithArrayEncoding() {
    assertEquals("java.lang", ClassUtils.getPackageName("[Ljava.lang.String;"));
    assertEquals("java.util", ClassUtils.getPackageName("[[Ljava.util.List;"));
    assertEquals("", ClassUtils.getPackageName("[I"));
}

@Test
void testGetPackageNameWithObjectEncoding() {
    assertEquals("java.lang", ClassUtils.getPackageName("Ljava.lang.String;"));
    assertEquals("com.example", ClassUtils.getPackageName("Lcom.example.Outer$Inner;"));
}
