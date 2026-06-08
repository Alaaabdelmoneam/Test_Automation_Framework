package org.blazedemo.media;

import java.nio.file.Path;

public interface VideoRecorder {

    void start(String testName);

    String stop();
}