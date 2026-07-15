@Test
void testIsAvailableLocale_withAvailableLocale() {
    assertTrue(LocaleUtils.isAvailableLocale(Locale.US));
}

@Test
void testIsAvailableLocale_withUnavailableLocale() {
    Locale unavailable = new Locale("xx", "YY");
    assertFalse(LocaleUtils.isAvailableLocale(unavailable));
}

@Test
void testIsAvailableLocale_withNullLocale() {
    assertFalse(LocaleUtils.isAvailableLocale(null));
}
