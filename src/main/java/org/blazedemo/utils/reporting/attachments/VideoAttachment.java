package org.blazedemo.utils.reporting.attachments;

import io.qameta.allure.Allure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class VideoAttachment {

    private VideoAttachment() {
    }

    public static void attachVideo(Path videoPath) {

        try {


            Allure.getLifecycle().addAttachment(
                    "Execution Video",
                    "video/mp4",
                    ".mp4",
                    Files.newInputStream(videoPath)
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to attach video",
                    e
            );
        }
        catch (NullPointerException e) {
            throw new RuntimeException(
                    "Video path is null, unable to attach video",
                    e
            );
        }
    }
}