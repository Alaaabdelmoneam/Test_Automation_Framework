package org.blazedemo.customlisteners;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.media.RecordingManager;
import org.blazedemo.media.VideoConfiguration;
import org.blazedemo.utils.FileUtilities;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

@Log4j2
public class RecordingListener implements ITestListener {
    public void onTestStart(ITestResult result) {
        log.info("record started ");
        RecordingManager.startRecording(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        String path = RecordingManager.stopRecording();
        log.info("record finished after test {} success", result.getMethod().getMethodName());
        if (path != null) {
            File recordedVideo = new File(path);

            if (recordedVideo.exists()) {
                if (VideoConfiguration.deleteIfTestPass()){
                    FileUtilities.deleteFile(path);
                    log.info("should have deleted");
                }
                else {
                    log.info("recorded video saved at: {} for a passed TC", recordedVideo.getAbsolutePath());
                }
            }
        }
    }

    public void onTestFailure(ITestResult result) {
        String path = RecordingManager.stopRecording();
        log.info("record finished after test {} failed", result.getMethod().getMethodName());
        if (path != null) {
            File recordedVideo = new File(path);
            log.info("recorded video saved at: {} for a failed TC", recordedVideo.getAbsolutePath());
        }
    }

    public void onTestSkipped(ITestResult result) {
        String path = RecordingManager.stopRecording();
        log.info("record finished after test {} skipped", result.getMethod().getMethodName());
        if (path != null) {
            File recordedVideo = new File(path);
            log.info("recorded video saved at: {} for a skipped TC", recordedVideo.getAbsolutePath());
        }
    }
}
