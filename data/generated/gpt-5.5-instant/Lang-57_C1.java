@Test
public void testIsAvailableLocale_availableLocale() {
    assertTrue(LocaleUtils.isAvailableLocale(Locale.US));
}

@Test
public void testIsAvailableLocale_defaultLocale() {
    assertTrue(LocaleUtils.isAvailableLocale(Locale.getDefault()));
}

@Test
public void testIsAvailableLocale_unavailableLocale() {
    Locale unavailable = new Locale("xx", "YY");
    assertFalse(LocaleUtils.isAvailableLocale(unavailable));
}

@Test
public void testIsAvailableLocale_nullLocale() {
    assertFalse(LocaleUtils.isAvailableLocale(null));
}
