package org.apache.commons.lang3.text.translate;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.StringWriter;
import java.io.IOException;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaper;

    @BeforeEach
    public void setUp() {
        unescaper = new NumericEntityUnescaper();
    }

    @Test
    public void testTranslateDecimalEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#65;", 0, writer);
        assertEquals(5, consumed);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#x41;", 0, writer);
        assertEquals(6, consumed);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateDecimalEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#65", 0, writer);
        assertEquals(5, consumed);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateHexEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#x41", 0, writer);
        assertEquals(6, consumed);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#", 0, writer);
        assertEquals(0, consumed);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateEntityWithNonNumericCharacters() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#a;", 0, writer);
        assertEquals(0, consumed);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateEntityWithTooLargeValue() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#0x110000;", 0, writer);
        assertEquals(0, consumed);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateEntityWithSurrogatePair() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#0x1D400;", 0, writer);
        assertEquals(7, consumed);
        assertEquals("𐀀", writer.toString());
    }
}
