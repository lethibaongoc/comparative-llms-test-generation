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
    void testStop_WhenRunning_ChangesStateToStopped() {
        stopWatch.start();
        stopWatch.stop();
        assertEquals(StopWatch.STATE_STOPPED, stopWatch.runningState);
    }

    @Test
    void testStop_WhenSuspended_ChangesStateToStopped() {
        stopWatch.start();
        // Simulate suspension by setting the state directly
        stopWatch.runningState = StopWatch.STATE_SUSPENDED;
        stopWatch.stop();
        assertEquals(StopWatch.STATE_STOPPED, stopWatch.runningState);
    }

    @Test
    void testStop_WhenRunning_SetsStopTime() {
        stopWatch.start();
        long startTime = System.currentTimeMillis();
        stopWatch.stop();
        assertTrue(stopWatch.stopTime >= startTime);
    }
}
