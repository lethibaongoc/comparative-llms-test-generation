package org.apache.commons.lang;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.StringWriter;
import java.io.IOException;

public class EntitiesTest {

    private Entities entities;

    @BeforeEach
    void setUp() {
        entities = new Entities();
    }

    @Test
    void testEscape_Ascii() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "Hello World");
        assertEquals("Hello World", writer.toString());
    }

    @Test
    void testEscape_NonAscii() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "©");
        assertEquals("&#169;", writer.toString());
    }

    @Test
    void testEscape_Supplementary() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "𐀀");
        assertEquals("&#65536;", writer.toString());
    }

    @Test
    void testEscape_EntityName() throws IOException {
        // Assuming entityName returns "copy" for code point 169
        StringWriter writer = new StringWriter();
        entities.escape(writer, "©");
        assertEquals("&copy;", writer.toString());
    }

    @Test
    void testEscape_MultipleCharacters() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "Hello © World");
        assertEquals("Hello &copy; World", writer.toString());
    }
}
