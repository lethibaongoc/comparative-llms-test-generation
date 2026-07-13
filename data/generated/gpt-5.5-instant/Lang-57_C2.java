@Test
public void testIsAvailableLocale_availableLocale() {
    assertTrue(LocaleUtils.isAvailableLocale(Locale.US));
}

@Test
public void testIsAvailableLocale_anotherAvailableLocale() {
    assertTrue(LocaleUtils.isAvailableLocale(Locale.ENGLISH));
}

@Test
public void testIsAvailableLocale_nullLocale() {
    assertFalse(LocaleUtils.isAvailableLocale(null));
}

@Test
public void testIsAvailableLocale_unavailableLocale() {
    Locale unavailable = new Locale("xx", "YY");
    assertFalse(LocaleUtils.isAvailableLocale(unavailable));
}
