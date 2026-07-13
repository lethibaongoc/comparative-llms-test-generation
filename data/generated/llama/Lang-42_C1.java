package org.apache.commons.lang;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.StringWriter;
import java.io.IOException;

public class EntitiesTest {

    private Entities entities;

    @BeforeEach
    public void setUp() {
        entities = new Entities();
    }

    @Test
    public void testEscape_Ascii() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "Hello World");
        assertEquals("Hello World", writer.toString());
    }

    @Test
    public void testEscape_NonAscii() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "©");
        assertEquals("&#169;", writer.toString());
    }

    @Test
    public void testEscape_Supplementary() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "𐀀");
        assertEquals("&#65536;", writer.toString());
    }

    @Test
    public void testEscape_EntityName() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "&");
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testEscape_EntityName_Named() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "<");
        assertEquals("&lt;", writer.toString());
    }

    @Test
    public void testEscape_EntityName_Unnamed() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, " ");
        assertEquals("&#160;", writer.toString());
    }

    @Test
    public void testEscape_Mixed() throws IOException {
        StringWriter writer = new StringWriter();
        entities.escape(writer, "Hello © World 𐀀");
        assertEquals("Hello &#169; World &#65536;", writer.toString());
    }
}
