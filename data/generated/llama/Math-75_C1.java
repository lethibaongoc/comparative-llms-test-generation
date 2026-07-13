package org.apache.commons.math.stat;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FrequencyTest {

    private Frequency frequency;

    @BeforeEach
    public void setUp() {
        frequency = new Frequency();
    }

    @Test
    public void testGetPctDeprecated() {
        // Arrange
        Comparable<?> value = "test";
        frequency.addValue(value);

        // Act
        double result = frequency.getPct(value);

        // Assert
        assertEquals(frequency.getPct((Comparable<?>) value), result);
    }

    @Test
    public void testGetPctDeprecatedNull() {
        // Act and Assert
        assertThrows(ClassCastException.class, () -> frequency.getPct(null));
    }

    @Test
    public void testGetPctDeprecatedNonComparable() {
        // Act and Assert
        assertThrows(ClassCastException.class, () -> frequency.getPct(new Object()));
    }
}
