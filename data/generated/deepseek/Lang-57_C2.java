@Test
void testIsAvailableLocale() {
    assertTrue(LocaleUtils.isAvailableLocale(Locale.US));
    assertTrue(LocaleUtils.isAvailableLocale(Locale.UK));
    assertTrue(LocaleUtils.isAvailableLocale(Locale.FRANCE));
    assertTrue(LocaleUtils.isAvailableLocale(Locale.GERMANY));
    assertTrue(LocaleUtils.isAvailableLocale(Locale.ITALY));
    assertTrue(LocaleUtils.isAvailableLocale(Locale.JAPAN));
    assertTrue(LocaleUtils.isAvailableLocale(Locale.CHINA));
    assertFalse(LocaleUtils.isAvailableLocale(new Locale("xx")));
    assertFalse(LocaleUtils.isAvailableLocale(new Locale("zz", "ZZ")));
    assertFalse(LocaleUtils.isAvailableLocale(null));
}
