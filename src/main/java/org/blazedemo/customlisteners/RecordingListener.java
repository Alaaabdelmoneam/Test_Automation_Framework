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

//    public void onTestSuccess(ITestResult result) {
//        Optional<Path> video =
//                Optional.ofNullable(RecordingManager.finishRecording(result));
//
//        video.ifPresent(VideoAttachment::attachVideo);
//
//    }
//
//    public void onTestFailure(ITestResult result) {
////        Optional<Path> video =
////                Optional.ofNullable(RecordingManager.finishRecording(result));
////
////        video.ifPresent(VideoAttachment::attachVideo);
//    }
//
//    public void onTestSkipped(ITestResult result) {
//        Optional<Path> video =
//                Optional.ofNullable(RecordingManager.finishRecording(result));
//
//        video.ifPresent(VideoAttachment::attachVideo);
//    }
}
