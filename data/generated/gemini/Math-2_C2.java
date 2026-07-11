```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.util.FastMath;

// NOTE: These tests assume that the HypergeometricDistribution class has a constructor
// that allows injecting a RandomDataGenerator for testability, e.g.:
// public HypergeometricDistribution(int populationSize, int numberOfSuccesses, int sampleSize, RandomDataGenerator randomData) { ... }
// This allows deterministic testing of the `sample()` method's logic.
// If your HypergeometricDistribution class does not have such a constructor,
// you would need to use reflection or modify the class for testability, or test probabilistically
// (which is less precise for unit tests).

// Mocking 'obj' for the purpose of these tests, consistent with the example format
// In a real test class, 'obj' might be a field initialized in a @BeforeEach method.

public class HypergeometricDistributionTest {

    @Test
    public void testSample_basicDeterministicValue() {
        int populationSize = 10;
        int numberOfSuccesses = 5;
        int sampleSize = 3;
        double simulatedNextHypergeometricResult = 1.7; // Value that will be rounded
        int expectedRoundedValue = (int) FastMath.round(simulatedNextHypergeometricResult);

        // Mock the RandomDataGenerator to return a predictable value
        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
        Mockito.when(mockRandomData.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn(simulatedNextHypergeometricResult);

        // Instantiate HypergeometricDistribution with the mock
        HypergeometricDistribution obj = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomData);

        int actualSample = obj.sample();
        assertEquals(expectedRoundedValue, actualSample);
    }

    @Test
    public void testSample_allSuccessesInPopulation() {
        int populationSize = 10;
        int numberOfSuccesses = 10; // All items in population are successes
        int sampleSize = 5;
        int expectedSample = sampleSize; // Must draw all successes from sample

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
        // If all are successes, nextHypergeometric should logically return sampleSize
        Mockito.when(mockRandomData.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn((double) sampleSize);

        HypergeometricDistribution obj = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomData);

        int actualSample = obj.sample();
        assertEquals(expectedSample, actualSample);
    }

    @Test
    public void testSample_noSuccessesInPopulation() {
        int populationSize = 10;
        int numberOfSuccesses = 0; // No successes in population
        int sampleSize = 5;
        int expectedSample = 0; // Must draw zero successes

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
        // If no successes, nextHypergeometric should logically return 0.0
        Mockito.when(mockRandomData.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn(0.0);

        HypergeometricDistribution obj = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomData);

        int actualSample = obj.sample();
        assertEquals(expectedSample, actualSample);
    }

    @Test
    public void testSample_sampleEqualsPopulationSize() {
        int populationSize = 10;
        int numberOfSuccesses = 7;
        int sampleSize = 10; // Sample the entire population
        int expectedSample = numberOfSuccesses; // The sample must contain all successes from the population

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
        // If sampling the entire population, nextHypergeometric should logically return numberOfSuccesses
        Mockito.when(mockRandomData.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn((double) numberOfSuccesses);

        HypergeometricDistribution obj = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomData);

        int actualSample = obj.sample();
        assertEquals(expectedSample, actualSample);
    }

    @Test
    public void testSample_zeroSampleSize() {
        int populationSize = 10;
        int numberOfSuccesses = 5;
        int sampleSize = 0; // Zero sample size
        int expectedSample = 0; // Must draw zero successes

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
        // If sample size is zero, nextHypergeometric should logically return 0.0
        Mockito.when(mockRandomData.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn(0.0);

        HypergeometricDistribution obj = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomData);

        int actualSample = obj.sample();
        assertEquals(expectedSample, actualSample);
    }

    @Test
    public void testSample_withinExpectedRangeAndRoundingBehavior() {
        int populationSize = 20;
        int numberOfSuccesses = 10;
        int sampleSize = 5;

        // The theoretical minimum and maximum number of successes in the sample
        int expectedMinSuccesses = Math.max(0, sampleSize - (populationSize - numberOfSuccesses)); // max(0, 5 - (20-10)) = 0
        int expectedMaxSuccesses = Math.min(sampleSize, numberOfSuccesses); // min(5, 10) = 5

        // --- Test lower bound rounding ---
        RandomDataGenerator mockRandomDataLower = Mockito.mock(RandomDataGenerator.class);
        // A value that rounds down to the minimum expected integer (e.g., 0.4 rounds to 0)
        Mockito.when(mockRandomDataLower.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn(0.4);

        HypergeometricDistribution objLower = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomDataLower);
        int actualLowerSample = objLower.sample();

        assertEquals(0, actualLowerSample, "Sample should round down to the theoretical minimum.");
        assertTrue(actualLowerSample >= expectedMinSuccesses && actualLowerSample <= expectedMaxSuccesses,
                   "Sample " + actualLowerSample + " must be within [" + expectedMinSuccesses + ", " + expectedMaxSuccesses + "]");

        // --- Test upper bound rounding ---
        RandomDataGenerator mockRandomDataUpper = Mockito.mock(RandomDataGenerator.class);
        // A value that rounds up to the maximum expected integer (e.g., 4.5 rounds to 5)
        Mockito.when(mockRandomDataUpper.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn(4.5);

        HypergeometricDistribution objUpper = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomDataUpper);
        int actualUpperSample = objUpper.sample();

        assertEquals(5, actualUpperSample, "Sample should round up to the theoretical maximum.");
        assertTrue(actualUpperSample >= expectedMinSuccesses && actualUpperSample <= expectedMaxSuccesses,
                   "Sample " + actualUpperSample + " must be within [" + expectedMinSuccesses + ", " + expectedMaxSuccesses + "]");
    }
}
```