@Test
void shouldReturnTrueWhenLocaleIsAvailable() {
    Locale existingLocale = Locale.US;
    assertTrue(LocaleUtils.isAvailableLocale(existingLocale));
}

@Test
void shouldReturnFalseWhenLocaleIsNotAvailable() {
    Locale fakeLocale = new Locale("xx", "XX");
    assertFalse(LocaleUtils.isAvailableLocale(fakeLocale));
}

@Test
void shouldReturnFalseForNullLocale() {
    assertFalse(LocaleUtils.isAvailableLocale(null));
}

@Test
void shouldReturnTrueForDefaultLocaleIfAvailable() {
    Locale defaultLocale = Locale.getDefault();
    assertTrue(LocaleUtils.isAvailableLocale(defaultLocale));
}

@Test
void shouldReturnTrueForAllInstalledLocales() {
    Locale[] installedLocales = Locale.getAvailableLocales();
    for (Locale locale : installedLocales) {
        assertTrue(LocaleUtils.isAvailableLocale(locale));
    }
}

@Test
void shouldReturnFalseForCustomVariantLocaleNotInstalled() {
    Locale customLocale = new Locale("en", "US", "CUSTOM");
    assertFalse(LocaleUtils.isAvailableLocale(customLocale));
}
