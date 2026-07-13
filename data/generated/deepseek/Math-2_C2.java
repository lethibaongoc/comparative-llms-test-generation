import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.util.FastMath;

public class HypergeometricDistributionTest {

    @Test
    public void testSample_basicDeterministicValue() {
        int populationSize = 10;
        int numberOfSuccesses = 5;
        int sampleSize = 3;
        double simulatedNextHypergeometricResult = 1.7;
        int expectedRoundedValue = (int) FastMath.round(simulatedNextHypergeometricResult);

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
        Mockito.when(mockRandomData.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn(simulatedNextHypergeometricResult);

        HypergeometricDistribution obj = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomData);

        int actualSample = obj.sample();
        assertEquals(expectedRoundedValue, actualSample);
    }

    @Test
    public void testSample_allSuccessesInPopulation() {
        int populationSize = 10;
        int numberOfSuccesses = 10;
        int sampleSize = 5;
        int expectedSample = sampleSize;

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
        Mockito.when(mockRandomData.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn((double) sampleSize);

        HypergeometricDistribution obj = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomData);

        int actualSample = obj.sample();
        assertEquals(expectedSample, actualSample);
    }

    @Test
    public void testSample_noSuccessesInPopulation() {
        int populationSize = 10;
        int numberOfSuccesses = 0;
        int sampleSize = 5;
        int expectedSample = 0;

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
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
        int sampleSize = 10;
        int expectedSample = numberOfSuccesses;

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
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
        int sampleSize = 0;
        int expectedSample = 0;

        RandomDataGenerator mockRandomData = Mockito.mock(RandomDataGenerator.class);
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

        int expectedMinSuccesses = Math.max(0, sampleSize - (populationSize - numberOfSuccesses));
        int expectedMaxSuccesses = Math.min(sampleSize, numberOfSuccesses);

        RandomDataGenerator mockRandomDataLower = Mockito.mock(RandomDataGenerator.class);
        Mockito.when(mockRandomDataLower.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn(0.4);

        HypergeometricDistribution objLower = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomDataLower);
        int actualLowerSample = objLower.sample();

        assertEquals(0, actualLowerSample, "Sample should round down to the theoretical minimum.");
        assertTrue(actualLowerSample >= expectedMinSuccesses && actualLowerSample <= expectedMaxSuccesses);

        RandomDataGenerator mockRandomDataUpper = Mockito.mock(RandomDataGenerator.class);
        Mockito.when(mockRandomDataUpper.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize))
               .thenReturn(4.5);

        HypergeometricDistribution objUpper = new HypergeometricDistribution(populationSize, numberOfSuccesses, sampleSize, mockRandomDataUpper);
        int actualUpperSample = objUpper.sample();

        assertEquals(5, actualUpperSample, "Sample should round up to the theoretical maximum.");
        assertTrue(actualUpperSample >= expectedMinSuccesses && actualUpperSample <= expectedMaxSuccesses);
    }
}
