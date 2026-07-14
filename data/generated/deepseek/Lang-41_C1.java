@Test
void shouldReturnEmptyWhenClassNameIsNull() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(null));
}

@Test
void shouldReturnEmptyWhenClassNameIsEmpty() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(""));
}

@Test
void shouldReturnEmptyForClassWithNoPackage() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("MyClass"));
}

@Test
void shouldReturnPackageNameForSimpleClass() {
    assertEquals("java.lang", ClassUtils.getPackageName("java.lang.String"));
}

@Test
void shouldReturnPackageNameForNestedClass() {
    assertEquals("java.util", ClassUtils.getPackageName("java.util.Map.Entry"));
}

@Test
void shouldReturnEmptyForArrayOfPrimitive() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("[I"));
}

@Test
void shouldReturnEmptyForMultiDimensionalArrayOfPrimitive() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("[[I"));
}

@Test
void shouldReturnPackageNameForArrayOfObject() {
    assertEquals("java.lang", ClassUtils.getPackageName("[Ljava.lang.String;"));
}

@Test
void shouldReturnPackageNameForMultiDimensionalArrayOfObject() {
    assertEquals("java.util", ClassUtils.getPackageName("[[Ljava.util.ArrayList;"));
}

@Test
void shouldReturnEmptyForObjectTypeEncodingWithoutPackage() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("LMyClass;"));
}

@Test
void shouldReturnPackageNameForObjectTypeEncodingWithPackage() {
    assertEquals("com.example", ClassUtils.getPackageName("Lcom.example.MyClass;"));
}

@Test
void shouldHandleClassWithPackageSeparatorAtBeginning() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName(".MyClass"));
}

@Test
void shouldHandleClassWithPackageSeparatorAtEnd() {
    assertEquals("com.example", ClassUtils.getPackageName("com.example."));
}

@Test
void shouldReturnEmptyForSingleCharacterClassName() {
    assertEquals(StringUtils.EMPTY, ClassUtils.getPackageName("A"));
}

@Test
void shouldReturnPackageNameForDeeplyNestedPackage() {
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName("org.apache.commons.lang3.StringUtils"));
}

@Test
void shouldStripArrayEncodingAndReturnPackageName() {
    assertEquals("java.util", ClassUtils.getPackageName("[Ljava.util.ArrayList;"));
}

@Test
void shouldStripMultipleArrayEncodingsAndReturnPackageName() {
    assertEquals("java.util", ClassUtils.getPackageName("[[[Ljava.util.ArrayList;"));
}
