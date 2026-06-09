package org.blazedemo.media;

import io.qameta.allure.model.Status;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.VideoRecordingConfiguration;
import org.blazedemo.utils.FileUtilities;
import org.blazedemo.utils.reporting.ArtifactRepository;

import java.io.File;
import java.nio.file.Path;


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

    public static Path finishRecording(String methodName, Status status){
        if (recorder.get() == null){
            log.warn("No video recorder found for test {}, skipping recording finalization",
                    methodName);
            return null;
        }
        String path = stopRecording();
        if (path != null){
            applyRecordingPolicy(methodName, status, path);
            return Path.of(path);
        }
        return null;
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

    public static Boolean applyRecordingPolicy(String methodName, Status status, String path){

        log.info("record finished after test {} with status: {}",
                methodName, status);

        Boolean videoSavingEnabled = checkIfSavingRequired(status);
        if (path != null){
            File recordedVideo = new File(path);
            if (videoSavingEnabled){
                log.info("recorded video saved at: {} for TC {}", recordedVideo.getAbsolutePath(), methodName);
            }
            else {
                if (recordedVideo.exists()) {
                    FileUtilities.deleteFile(path);
                    log.info("recorded video discarded for TC {}", methodName);
                }
            }
        }
        return videoSavingEnabled;
    }

    private static Boolean checkIfSavingRequired(Status status){
        if(status == Status.PASSED && VideoRecordingConfiguration.recordOnSuccess()){
            return true;
        }
        else if(status == Status.PASSED && VideoRecordingConfiguration.recordOnFailures()){
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
