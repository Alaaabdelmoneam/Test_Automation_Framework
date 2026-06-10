package org.blazedemo.media.videorecorder;

public interface VideoRecorder {

    String start(String testName);

    String stop();

    public String getVideoPath();
}