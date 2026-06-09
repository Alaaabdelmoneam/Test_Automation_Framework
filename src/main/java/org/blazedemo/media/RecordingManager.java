package org.blazedemo.media;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.VideoRecordingConfiguration;
import org.blazedemo.utils.FileUtilities;
import org.blazedemo.utils.reporting.ArtifactRepository;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Path;

import static org.blazedemo.utils.TestCaseStatus.getStatusText;

@Log4j2
public class RecordingManager {

    private static final ThreadLocal<VideoRecorder>
            recorder = new ThreadLocal<>();

    public static void startRecording(
            String testName) {

        if(VideoRecordingConfiguration.isRecordingEnabled()) {

            VideoRecorder videoRecorder =
                    new FfmpegVideoRecorder();

            videoRecorder.start(testName);

            recorder.set(videoRecorder);

            log.info("record started ");
        }
        else {
            recorder.set(
                    new NoOpVideoRecorder());
        }
    }

    public static Boolean finishRecording(ITestResult result){
        if (recorder.get() == null){
            log.warn("No video recorder found for test {}, skipping recording finalization",
                    result.getMethod().getMethodName());
            return false;
        }
        String path = stopRecording();
        return applyRecordingPolicy(result, path);
    }

    public static String stopRecording() {

        VideoRecorder videoRecorder =
                recorder.get();

        if(videoRecorder == null) {
            return null;
        }

        String videoPath = videoRecorder.stop();
        ArtifactRepository.registerVideo(
                videoPath,
                Path.of(videoPath)
        );

        recorder.remove();
        return videoPath;
    }

    public static Boolean applyRecordingPolicy(ITestResult result, String path){

        log.info("record finished after test {} with status: {}",
                result.getMethod().getMethodName(), getStatusText(result.getStatus()));

        Boolean videoSavingEnabled = checkIfSavingRequired(result);
        if (path != null){
            File recordedVideo = new File(path);
            if (videoSavingEnabled){
                log.info("recorded video saved at: {} for TC {}", recordedVideo.getAbsolutePath(), result.getTestName());
            }
            else {
                if (recordedVideo.exists()) {
                    FileUtilities.deleteFile(path);
                    log.info("recorded video discarded for TC {}", result.getTestName());
                }
            }
        }
        return videoSavingEnabled;
    }

    private static Boolean checkIfSavingRequired(ITestResult result){
        if(result.getStatus() == ITestResult.SUCCESS && VideoRecordingConfiguration.recordOnSuccess()){
            return true;
        }
        else if(result.getStatus() == ITestResult.FAILURE && VideoRecordingConfiguration.recordOnFailures()){
            return true;
        }
        else {
            return false;
        }
    }

    public static void main (String[] args) throws InterruptedException {
        RecordingManager.startRecording("main");
        Thread.sleep(5000);
        RecordingManager.stopRecording();
    }
}
