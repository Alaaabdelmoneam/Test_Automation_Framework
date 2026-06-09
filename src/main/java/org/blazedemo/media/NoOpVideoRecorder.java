package org.blazedemo.media;

public class NoOpVideoRecorder
        implements VideoRecorder {

    @Override
    public String start(String testName) {
        return null;
    }

    @Override
    public String stop() {
        return null;
    }
    @Override
    public String getVideoPath(){
        return null;
    }
}