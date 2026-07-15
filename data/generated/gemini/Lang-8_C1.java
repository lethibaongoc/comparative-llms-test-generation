@Test
void appendTo_WhenUseDaylightTimeIsTrueAndDstOffsetIsNotZero_ShouldAppendDaylightTime() {
    // Arrange
    FastDatePrinter printer = Mockito.mock(FastDatePrinter.class, Mockito.CALLS_REAL_METHODS);
    Calendar mockCalendar = Mockito.mock(Calendar.class);
    TimeZone mockZone = Mockito.mock(TimeZone.class);
    StringBuffer buffer = new StringBuffer();

    Mockito.when(mockCalendar.getTimeZone()).thenReturn(mockZone);
    Mockito.when(mockZone.useDaylightTime()).thenReturn(true);
    Mockito.when(mockCalendar.get(Calendar.DST_OFFSET)).thenReturn(3600000); // 1 hour offset (non-zero)

    Mockito.doReturn("Daylight-Timezone-Display")
           .when(printer)
           .getTimeZoneDisplay(Mockito.eq(mockZone), Mockito.eq(true), Mockito.anyInt(), Mockito.any());

    // Act
    printer.appendTo(buffer, mockCalendar);

    // Assert
    assertEquals("Daylight-Timezone-Display", buffer.toString());
    Mockito.verify(printer).getTimeZoneDisplay(Mockito.eq(mockZone), Mockito.eq(true), Mockito.anyInt(), Mockito.any());
}

@Test
void appendTo_WhenUseDaylightTimeIsTrueButDstOffsetIsZero_ShouldAppendStandardTime() {
    // Arrange
    FastDatePrinter printer = Mockito.mock(FastDatePrinter.class, Mockito.CALLS_REAL_METHODS);
    Calendar mockCalendar = Mockito.mock(Calendar.class);
    TimeZone mockZone = Mockito.mock(TimeZone.class);
    StringBuffer buffer = new StringBuffer();

    Mockito.when(mockCalendar.getTimeZone()).thenReturn(mockZone);
    Mockito.when(mockZone.useDaylightTime()).thenReturn(true);
    Mockito.when(mockCalendar.get(Calendar.DST_OFFSET)).thenReturn(0); // zero offset

    Mockito.doReturn("Standard-Timezone-Display")
           .when(printer)
           .getTimeZoneDisplay(Mockito.eq(mockZone), Mockito.eq(false), Mockito.anyInt(), Mockito.any());

    // Act
    printer.appendTo(buffer, mockCalendar);

    // Assert
    assertEquals("Standard-Timezone-Display", buffer.toString());
    Mockito.verify(printer).getTimeZoneDisplay(Mockito.eq(mockZone), Mockito.eq(false), Mockito.anyInt(), Mockito.any());
}

@Test
void appendTo_WhenUseDaylightTimeIsFalse_ShouldAppendStandardTimeRegardlessOfDstOffset() {
    // Arrange
    FastDatePrinter printer = Mockito.mock(FastDatePrinter.class, Mockito.CALLS_REAL_METHODS);
    Calendar mockCalendar = Mockito.mock(Calendar.class);
    TimeZone mockZone = Mockito.mock(TimeZone.class);
    StringBuffer buffer = new StringBuffer();

    Mockito.when(mockCalendar.getTimeZone()).thenReturn(mockZone);
    Mockito.when(mockZone.useDaylightTime()).thenReturn(false);
    Mockito.when(mockCalendar.get(Calendar.DST_OFFSET)).thenReturn(3600000); // non-zero offset but useDaylightTime is false

    Mockito.doReturn("Standard-Timezone-Display")
           .when(printer)
           .getTimeZoneDisplay(Mockito.eq(mockZone), Mockito.eq(false), Mockito.anyInt(), Mockito.any());

    // Act
    printer.appendTo(buffer, mockCalendar);

    // Assert
    assertEquals("Standard-Timezone-Display", buffer.toString());
    Mockito.verify(printer).getTimeZoneDisplay(Mockito.eq(mockZone), Mockito.eq(false), Mockito.anyInt(), Mockito.any());
}