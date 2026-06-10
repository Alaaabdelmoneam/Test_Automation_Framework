package org.blazedemo.utils.reporting;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class ArtifactRepository {

    private ArtifactRepository() {}

    private static final Map<String, Path> saved_videos =
            new ConcurrentHashMap<>();

    private static final Map<String, Path> saved_screenshots =
            new ConcurrentHashMap<>();

    public static void registerVideo(
            String testId,
            Path video) {

        saved_videos.put(testId, video);
    }

    public static Optional<Path> getVideo(
            String testId) {

        return Optional.ofNullable(
                saved_videos.get(testId)
        );
    }

    public static void registerScreenshot(
            String testId,
            Path video) {

        saved_screenshots.put(testId, video);
    }

    public static Optional<Path> getScreenshot(
            String testId) {

        return Optional.ofNullable(
                saved_screenshots.get(testId)
        );
    }
}