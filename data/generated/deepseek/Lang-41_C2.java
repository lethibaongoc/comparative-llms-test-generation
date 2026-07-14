@Test
void testGetPackageNameWithNull() {
    assertEquals(StringUtils.EMPTY, getPackageName(null));
}

@Test
void testGetPackageNameWithEmptyString() {
    assertEquals(StringUtils.EMPTY, getPackageName(""));
}

@Test
void testGetPackageNameWithSimpleClassName() {
    assertEquals(StringUtils.EMPTY, getPackageName("MyClass"));
}

@Test
void testGetPackageNameWithFullyQualifiedName() {
    assertEquals("com.example", getPackageName("com.example.MyClass"));
}

@Test
void testGetPackageNameWithNestedPackage() {
    assertEquals("com.example.util", getPackageName("com.example.util.StringHelper"));
}

@Test
void testGetPackageNameWithClassInDefaultPackage() {
    assertEquals(StringUtils.EMPTY, getPackageName("DefaultClass"));
}

@Test
void testGetPackageNameWithArrayType() {
    assertEquals("java.util", getPackageName("[Ljava.util.List;"));
}

@Test
void testGetPackageNameWithMultiDimensionalArray() {
    assertEquals("java.lang", getPackageName("[[Ljava.lang.String;"));
}

@Test
void testGetPackageNameWithPrimitiveArray() {
    assertEquals(StringUtils.EMPTY, getPackageName("[I"));
}

@Test
void testGetPackageNameWithObjectTypeEncoding() {
    assertEquals("com.test", getPackageName("Lcom.test.MyClass;"));
}

@Test
void testGetPackageNameWithArrayAndObjectEncoding() {
    assertEquals("org.example", getPackageName("[Lorg.example.Data;"));
}

@Test
void testGetPackageNameWithClassNameContainingDots() {
    assertEquals("com.foo.bar", getPackageName("com.foo.bar.MyClass"));
}

@Test
void testGetPackageNameWithNoPackageSeparator() {
    assertEquals(StringUtils.EMPTY, getPackageName("SingleClass"));
}

@Test
void testGetPackageNameWithPackageSeparatorAtStart() {
    assertEquals(StringUtils.EMPTY, getPackageName(".HiddenClass"));
}
