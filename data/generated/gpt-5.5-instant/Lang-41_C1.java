@Test
public void testGetPackageName_nullInput() {
    assertEquals("", ClassUtils.getPackageName((String) null));
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
public void testGetPackageName_objectArrayDescriptor() {
    assertEquals("java.lang", ClassUtils.getPackageName("[Ljava.lang.String;"));
}

@Test
public void testGetPackageName_multiDimensionalObjectArrayDescriptor() {
    assertEquals("java.lang", ClassUtils.getPackageName("[[[Ljava.lang.String;"));
}

@Test
public void testGetPackageName_primitiveArrayDescriptor() {
    assertEquals("", ClassUtils.getPackageName("[I"));
}

@Test
public void testGetPackageName_multiDimensionalPrimitiveArrayDescriptor() {
    assertEquals("", ClassUtils.getPackageName("[[[D"));
}

@Test
public void testGetPackageName_objectDescriptorWithoutArray() {
    assertEquals("java.util", ClassUtils.getPackageName("Ljava.util.List;"));
}

@Test
public void testGetPackageName_customPackage() {
    assertEquals("com.example.project", ClassUtils.getPackageName("com.example.project.MyClass"));
}
