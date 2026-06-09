package org.blazedemo.media;

import java.nio.file.Path;

public interface VideoRecorder {

    String start(String testName);

    String stop();

    public String getVideoPath();
}