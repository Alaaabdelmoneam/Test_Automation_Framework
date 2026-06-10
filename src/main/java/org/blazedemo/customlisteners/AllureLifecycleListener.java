package org.blazedemo.customlisteners;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.drivers.DriverManager;
import org.blazedemo.media.ScreenshotManager;
import org.blazedemo.media.videorecorder.RecordingManager;
import org.blazedemo.utils.reporting.attachments.ScreenshotAttachment;
import org.blazedemo.utils.reporting.attachments.VideoAttachment;

import java.nio.file.Path;
import java.util.Optional;

@Log4j2
public class AllureLifecycleListener implements TestLifecycleListener {
    @Override
    public void beforeTestStop(TestResult result) {
        log.error("beforeTestStop called for {}", result.getName());
        if (result.getStatus() == Status.FAILED || result.getStatus() == Status.BROKEN || result.getStatus() == Status.PASSED) {
            // Insert your screenshot or attachment logic here
            Optional<Path> video =
                    Optional.ofNullable(RecordingManager.finishRecording(result.getName(), result.getStatus()));
            video.ifPresent(VideoAttachment::attachVideo);

            Optional<Path> screenshot =
                    Optional.of(ScreenshotManager.takePageScreenshot(DriverManager.getDriver(), result.getName()));

            screenshot.ifPresent(ScreenshotAttachment::attachScreenshot);

        }
    }
}