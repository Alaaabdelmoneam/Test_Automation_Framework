package org.blazedemo.config;

import lombok.Getter;

import java.time.Duration;
import java.util.List;

@Getter
public final class WaitConfiguration {

    private final Duration timeoutSeconds;
    private final Duration pollingIntervalMillis;
    private final String timeoutMessage;

    public WaitConfiguration(String filePath) {
        Configuration config = new Configuration(filePath);

        timeoutSeconds =
                Duration.ofSeconds(
                        Integer.parseInt(
                                config.getRequiredProperty(
                                        "timeout_seconds")));

        pollingIntervalMillis =
                Duration.ofMillis(
                        Long.parseLong(
                                config.getRequiredProperty(
                                        "polling_interval_millis")));

        timeoutMessage =
                config.getRequiredProperty(
                        "custom_timeout_message");
    }
}