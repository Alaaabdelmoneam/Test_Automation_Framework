package org.blazedemo.tests.basetest;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.annotations.UITest;
import org.blazedemo.media.videorecorder.RecordingManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@Log4j2
@UITest
public class BaseTest {
    // Get Execution ID to be attached to the log
    static {
        String executionId =
                java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        System.setProperty("execution.id", executionId);
    }
    @BeforeMethod
    public void setup(){
    }


    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        RecordingManager.forceStopRecording();
        log.info("Recorder instance cleaned up!");
    }
}
