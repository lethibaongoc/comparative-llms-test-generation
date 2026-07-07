package org.apache.commons.lang3.time;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;

public class FastDateParserTest {

    private Method escapeRegexMethod;

    @BeforeEach
    public void setup() throws NoSuchMethodException {
        escapeRegexMethod = FastDateParser.class.getDeclaredMethod("escapeRegex", StringBuilder.class, String.class, boolean.class);
        escapeRegexMethod.setAccessible(true);
    }

    @Test
    public void testEscapeRegex_NoSpecialChars() throws Exception {
        StringBuilder regex = new StringBuilder();
        String value = "HelloWorld";
        boolean unquote = false;
        StringBuilder result = (StringBuilder) escapeRegexMethod.invoke(null, regex, value, unquote);
        assertEquals("HelloWorld", result.toString());
    }

    @Test
    public void testEscapeRegex_SpecialChars() throws Exception {
        StringBuilder regex = new StringBuilder();
        String value = "Hello?World[";
        boolean unquote = false;
        StringBuilder result = (StringBuilder) escapeRegexMethod.invoke(null, regex, value, unquote);
        assertEquals("Hello\?World\$$", result.toString());
    }

    @Test
    public void testEscapeRegex_Unquote() throws Exception {
        StringBuilder regex = new StringBuilder();
        String value = "Hello'World";
        boolean unquote = true;
        StringBuilder result = (StringBuilder) escapeRegexMethod.invoke(null, regex, value, unquote);
        assertEquals("HelloWorld", result.toString());
    }

    @Test
    public void testEscapeRegex_Unquote_AtEnd() throws Exception {
        StringBuilder regex = new StringBuilder();
        String value = "Hello'";
        boolean unquote = true;
        StringBuilder result = (StringBuilder) escapeRegexMethod.invoke(null, regex, value, unquote);
        assertEquals("Hello", result.toString());
    }

    @Test
    public void testEscapeRegex_Unquote_AtEnd_NoNextChar() throws Exception {
        StringBuilder regex = new StringBuilder();
        String value = "Hello'";
        boolean unquote = true;
        StringBuilder result = (StringBuilder) escapeRegexMethod.invoke(null, regex, value, unquote);
        assertEquals("Hello", result.toString());
    }
}
