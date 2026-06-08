package org.blazedemo.media;

import java.nio.file.Path;

public class NoOpVideoRecorder
        implements VideoRecorder {

    @Override
    public void start(String testName) {}

    @Override
    public String stop() {
        return null;
    }
}