//package org.blazedemo.customlisteners;
//
//import lombok.extern.log4j.Log4j2;
//import org.blazedemo.media.RecordingManager;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//@Log4j2
//public class RecordingListener implements ITestListener {
//    public void onTestStart(ITestResult result) {
//        RecordingManager.startRecording(result.getMethod().getMethodName());
//    }
//
//    public void onTestSuccess(ITestResult result) {
//        String videoPath = RecordingManager.stopRecording();
//        RecordingManager.applyRecordingPolicy(result, videoPath);
//    }
//
//    public void onTestFailure(ITestResult result) {
//        String videoPath = RecordingManager.stopRecording();
//        RecordingManager.applyRecordingPolicy(result, videoPath);
//    }
//
//    public void onTestSkipped(ITestResult result) {
//        String videoPath = RecordingManager.stopRecording();
//        RecordingManager.applyRecordingPolicy(result, videoPath);
//    }
//}
