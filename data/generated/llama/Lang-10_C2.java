package org.apache.commons.lang3.time;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;

public class FastDateParserTest {

    @Test
    void testEscapeRegex() throws Exception {
        Method method = FastDateParser.class.getDeclaredMethod("escapeRegex", StringBuilder.class, String.class, boolean.class);
        method.setAccessible(true);

        StringBuilder regex = new StringBuilder();
        assertEquals(regex.append("\\'"), method.invoke(null, regex, "'", false));
        assertEquals(regex.append("\\?"), method.invoke(null, regex, "?", false));
        assertEquals(regex.append("\$$"), method.invoke(null, regex, "[", false));
        assertEquals(regex.append("\$$"), method.invoke(null, regex, "]", false));
        assertEquals(regex.append("\$"), method.invoke(null, regex, "(", false));
        assertEquals(regex.append("\$"), method.invoke(null, regex, ")", false));
        assertEquals(regex.append("\\{"), method.invoke(null, regex, "{", false));
        assertEquals(regex.append("\\}"), method.invoke(null, regex, "}", false));
        assertEquals(regex.append("\\\\"), method.invoke(null, regex, "\\", false));
        assertEquals(regex.append("\\|"), method.invoke(null, regex, "|", false));
        assertEquals(regex.append("\\*"), method.invoke(null, regex, "*", false));
        assertEquals(regex.append("\\+"), method.invoke(null, regex, "+", false));
        assertEquals(regex.append("\\^"), method.invoke(null, regex, "^", false));
        assertEquals(regex.append("\\$"), method.invoke(null, regex, "$", false));
        assertEquals(regex.append("\\."), method.invoke(null, regex, ".", false));

        StringBuilder regex2 = new StringBuilder();
        assertEquals(regex2.append("a"), method.invoke(null, regex2, "a", false));

        StringBuilder regex3 = new StringBuilder();
        assertEquals(regex3.append("'a"), method.invoke(null, regex3, "'a", true));
    }

    @Test
    void testEscapeRegexUnquote() throws Exception {
        Method method = FastDateParser.class.getDeclaredMethod("escapeRegex", StringBuilder.class, String.class, boolean.class);
        method.setAccessible(true);

        StringBuilder regex = new StringBuilder();
        assertEquals(regex, method.invoke(null, regex, "'", true));
    }
}
