@Test
void isAvailableLocale_withValidLocale_shouldReturnTrue() {
    // Standard system locale should be present in the available list
    assertTrue(LocaleUtils.isAvailableLocale(Locale.US));
}

@Test
void isAvailableLocale_withInvalidLocale_shouldReturnFalse() {
    // A custom, fictional locale that won't be in the system's available list
    Locale dummyLocale = new Locale("xx", "XX");
    assertFalse(LocaleUtils.isAvailableLocale(dummyLocale));
}

@Test
void isAvailableLocale_withNullLocale_shouldReturnFalse() {
    // Verifies the method safely handles null by returning false
    assertFalse(LocaleUtils.isAvailableLocale(null));
}
