package org.blazedemo.customlisteners;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.media.videorecorder.RecordingManager;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Log4j2
public class RecordingListener implements ITestListener {
    public void onTestStart(ITestResult result) {
        RecordingManager.startRecording(result.getMethod().getMethodName());
    }


    @Override
    public void onTestFailure(ITestResult result) {
        RecordingManager.stopRecording();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        RecordingManager.stopRecording();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        RecordingManager.stopRecording();
    }
}
