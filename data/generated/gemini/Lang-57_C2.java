@Test
void testIsAvailableLocale() {
    Locale availableLocale = Locale.US;
    Locale unavailableLocale = new Locale("xx", "XX");

    assertTrue(LocaleUtils.isAvailableLocale(availableLocale));
    assertFalse(LocaleUtils.isAvailableLocale(unavailableLocale));
}

@Test
void testIsAvailableLocale_Null() {
    assertFalse(LocaleUtils.isAvailableLocale(null));
}
