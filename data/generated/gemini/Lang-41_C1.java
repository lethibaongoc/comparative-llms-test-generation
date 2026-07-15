@ParameterizedTest
@NullAndEmptySource
void getPackageName_ShouldReturnEmptyString_WhenClassNameIsNullOrEmpty(String className) {
    assertEquals("", ClassUtils.getPackageName(className));
}

@Test
void getPackageName_ShouldReturnEmptyString_WhenNoPackageSeparatorPresent() {
    assertEquals("", ClassUtils.getPackageName("MyClass"));
}

@Test
void getPackageName_ShouldReturnPackageName_WhenStandardClassNamePassed() {
    assertEquals("org.example", ClassUtils.getPackageName("org.example.MyClass"));
    assertEquals("org.example.sub", ClassUtils.getPackageName("org.example.sub.MyClass"));
}

@Test
void getPackageName_ShouldStripArrayEncoding_BeforeProcessing() {
    // Array of objects represented as [[Lorg.example.MyClass;
    assertEquals("org.example", ClassUtils.getPackageName("[[Lorg.example.MyClass;"));
    // Array of primitives/objects without L/; wrapper represented as [[org.example.MyClass
    assertEquals("org.example", ClassUtils.getPackageName("[[org.example.MyClass"));
}

@Test
void getPackageName_ShouldStripObjectEncoding_BeforeProcessing() {
    // Object type encoding represented as Lorg.example.MyClass;
    assertEquals("org.example", ClassUtils.getPackageName("Lorg.example.MyClass;"));
}

@Test
void getPackageName_ShouldReturnEmptyString_WhenArrayPrimitiveTypePassed() {
    // Array of primitive int represented as [[I (no package)
    assertEquals("", ClassUtils.getPackageName("[[I"));
}
