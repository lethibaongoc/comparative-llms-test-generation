package org.apache.commons.math.stat;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FrequencyTest {

    @Test
    void testGetPctDeprecated() {
        Frequency frequency = new Frequency();
        frequency.addValue("test");
        assertEquals(frequency.getPct("test"), frequency.getPct((Object) "test"));
    }

    @Test
    void testGetPctDeprecatedNull() {
        Frequency frequency = new Frequency();
        assertThrows(ClassCastException.class, () -> frequency.getPct((Object) null));
    }

    @Test
    void testGetPctDeprecatedDifferentType() {
        Frequency frequency = new Frequency();
        frequency.addValue(1);
        assertThrows(ClassCastException.class, () -> frequency.getPct((Object) "test"));
    }
}
