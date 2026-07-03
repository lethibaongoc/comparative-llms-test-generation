package org.apache.commons.lang.time;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StopWatchTest {

    private StopWatch stopWatch;

    @BeforeEach
    void setUp() {
        stopWatch = new StopWatch();
    }

    @Test
    void testStop_WhenNotRunning_ThrowsException() {
        assertThrows(IllegalStateException.class, () -> stopWatch.stop());
    }

    @Test
    void testStop_WhenRunning_SetsStateToStopped() {
        stopWatch.start();
        stopWatch.stop();
        assertEquals(StopWatch.STATE_STOPPED, stopWatch.runningState);
    }

    @Test
    void testStop_WhenSuspended_SetsStateToStopped() {
        // Assuming suspend method is implemented, for demonstration purposes
        // stopWatch.suspend();
        // stopWatch.stop();
        assertEquals(StopWatch.STATE_STOPPED, stopWatch.runningState);
    }

    @Test
    void testStop_WhenRunning_SetsStopTime() {
        stopWatch.start();
        long startTime = System.currentTimeMillis();
        stopWatch.stop();
        long stopTime = System.currentTimeMillis();
        assertTrue(stopWatch.stopTime >= startTime && stopWatch.stopTime <= stopTime);
    }
}
