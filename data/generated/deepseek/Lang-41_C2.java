@Test
void testGetPackageNameWithNull() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(null));
}

@Test
void testGetPackageNameWithEmptyString() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(""));
}

@Test
void testGetPackageNameWithSimpleClassName() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("MyClass"));
}

@Test
void testGetPackageNameWithFullyQualifiedName() {
    assertEquals("com.example", ClassUtils.getPackageName("com.example.MyClass"));
}

@Test
void testGetPackageNameWithNestedPackage() {
    assertEquals("com.example.test", ClassUtils.getPackageName("com.example.test.MyClass"));
}

@Test
void testGetPackageNameWithArrayType() {
    assertEquals("java.lang", ClassUtils.getPackageName("[Ljava.lang.String;"));
}

@Test
void testGetPackageNameWithMultiDimensionalArray() {
    assertEquals("java.util", ClassUtils.getPackageName("[[Ljava.util.ArrayList;"));
}

@Test
void testGetPackageNameWithPrimitiveArray() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("[I"));
}

@Test
void testGetPackageNameWithObjectTypeEncoding() {
    assertEquals("com.example", ClassUtils.getPackageName("Lcom.example.MyClass;"));
}

@Test
void testGetPackageNameWithArrayOfObjectType() {
    assertEquals("com.example", ClassUtils.getPackageName("[Lcom.example.MyClass;"));
}

@Test
void testGetPackageNameWithClassInDefaultPackage() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("DefaultClass"));
}

@Test
void testGetPackageNameWithPackageSeparatorAtEnd() {
    assertEquals("com.example.", ClassUtils.getPackageName("com.example.MyClass"));
}

@Test
void testGetPackageNameWithOnlyPackageSeparator() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("."));
}
