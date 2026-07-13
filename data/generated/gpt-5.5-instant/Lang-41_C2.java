@Test
public void testGetPackageName_nullInput() {
    assertEquals("", ClassUtils.getPackageName(null));
}

@Test
public void testGetPackageName_emptyInput() {
    assertEquals("", ClassUtils.getPackageName(""));
}

@Test
public void testGetPackageName_regularClassName() {
    assertEquals("java.lang", ClassUtils.getPackageName("java.lang.String"));
}

@Test
public void testGetPackageName_classWithoutPackage() {
    assertEquals("", ClassUtils.getPackageName("String"));
}

@Test
public void testGetPackageName_objectArrayEncoding() {
    assertEquals("java.lang", ClassUtils.getPackageName("[Ljava.lang.String;"));
}

@Test
public void testGetPackageName_multidimensionalObjectArrayEncoding() {
    assertEquals("java.util", ClassUtils.getPackageName("[[Ljava.util.List;"));
}

@Test
public void testGetPackageName_primitiveArrayEncoding() {
    assertEquals("", ClassUtils.getPackageName("[I"));
}

@Test
public void testGetPackageName_nestedClassName() {
    assertEquals("com.example", ClassUtils.getPackageName("com.example.Outer$Inner"));
}

@Test
public void testGetPackageName_objectEncodingWithoutArray() {
    assertEquals("java.lang", ClassUtils.getPackageName("Ljava.lang.String;"));
}

@Test
public void testGetPackageName_defaultPackageObjectArrayEncoding() {
    assertEquals("", ClassUtils.getPackageName("[LMyClass;"));
}
