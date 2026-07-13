package org.apache.commons.lang3.text.translate;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.StringWriter;
import java.io.IOException;

public class NumericEntityUnescaperTest {

    @Test
    void testTranslateDecimalEntity() throws IOException {
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#65;", 0, writer);
        assertEquals(5, consumed);
        assertEquals("A", writer.toString());
    }

    @Test
    void testTranslateHexEntity() throws IOException {
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#x41;", 0, writer);
        assertEquals(6, consumed);
        assertEquals("A", writer.toString());
    }

    @Test
    void testTranslateDecimalEntityWithoutSemicolon() throws IOException {
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#65", 0, writer);
        assertEquals(5, consumed);
        assertEquals("A", writer.toString());
    }

    @Test
    void testTranslateHexEntityWithoutSemicolon() throws IOException {
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#x41", 0, writer);
        assertEquals(6, consumed);
        assertEquals("A", writer.toString());
    }

    @Test
    void testTranslateInvalidEntity() throws IOException {
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&", 0, writer);
        assertEquals(0, consumed);
        assertEquals("", writer.toString());
    }

    @Test
    void testTranslateEntityWithNonNumericCharacters() throws IOException {
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#a1;", 0, writer);
        assertEquals(0, consumed);
        assertEquals("", writer.toString());
    }

    @Test
    void testTranslateEntityWithTooLargeValue() throws IOException {
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#0x110000;", 0, writer);
        assertEquals(9, consumed);
        char[] expected = Character.toChars(0x110000);
        assertEquals(String.valueOf(expected), writer.toString());
    }
}
