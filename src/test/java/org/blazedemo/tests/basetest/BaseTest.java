package org.blazedemo.tests.basetest;

public class BaseTest {
    // Get Execution ID to be attached to the log
    static {
        String executionId =
                java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        System.setProperty("execution.id", executionId);
    }
}
