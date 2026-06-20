package org.blazedemo.media.videorecorder;

import io.qameta.allure.model.Status;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.VideoRecordingConfiguration;
import org.blazedemo.media.MediaUtilities;
import org.blazedemo.utils.FileUtilities;
import org.blazedemo.utils.reporting.ArtifactRepository;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.blazedemo.utils.FileUtilities.checkIfFileExists;


@Log4j2
public class RecordingManager extends MediaUtilities {

    private static final ThreadLocal<VideoRecorder>
            recorder = new ThreadLocal<>();

    private static final Set<VideoRecorder> ACTIVE_RECORDERS =
            ConcurrentHashMap.newKeySet();

    private static final String extension =
            VideoRecordingConfiguration.getExtension();

    private static final String outputDirectory =
            VideoRecordingConfiguration.getOutputDirectory();

    public static void startRecording(
            String testName) {

        String videoPath = getUniqueName(testName, extension, outputDirectory);

        // Create folder if not created yet
        createParentMediaDirectory(videoPath);

        if(VideoRecordingConfiguration.isRecordingEnabled()) {

            VideoRecorder videoRecorder =
                    new FfmpegVideoRecorder();

            log.debug(videoPath);
            videoRecorder.start(videoPath);

            recorder.set(videoRecorder);
            ACTIVE_RECORDERS.add(videoRecorder);

            log.info("record started ");
        }
        else {
            recorder.set(
                    new NoOpVideoRecorder());
        }
    }

    public static Path finishRecording(String methodName, Status status){
        VideoRecorder videoRecorder = recorder.get();
        if (videoRecorder == null){
            log.warn("No video recorder found for test {}, skipping recording finalization",
                    methodName);
            return null;
        }
        String path = stopRecording();
        ACTIVE_RECORDERS.remove(videoRecorder);

        if (path != null){
            applyRecordingPolicy(methodName, status, path);
            return Path.of(path);
        }
        return null;
    }

    public static String stopRecording() {

        if (!VideoRecordingConfiguration.isRecordingEnabled()){
            log.error("Can't Stop recording if recording isn't enabled at the first place!");
            log.error("Please Enable Recording First");
            return null;
        }
        VideoRecorder videoRecorder =
                recorder.get();

        if(videoRecorder == null) {
            return null;
        }

        String videoPath = videoRecorder.stop();
        if (!checkIfFileExists(videoPath)){
            log.error("no video file exist, can't stop recording");
            throw new RuntimeException("File Not Found!");
        }
        ArtifactRepository.registerVideo(
                videoPath,
                Path.of(videoPath)
        );

        recorder.remove();
        return videoPath;
    }

    // thread-level cleanup on exception
    public static void forceStopRecording(){
        VideoRecorder videoRecorder =
                recorder.get();


        if(videoRecorder == null) {
            return;
        }

        videoRecorder.stop();
        ACTIVE_RECORDERS.remove(videoRecorder);
    }

    // suite level cleanup
    public static void stopAllRecordings() {

        ACTIVE_RECORDERS.forEach(recorder -> {
            if (recorder != null) {
                try {
                    recorder.stop();
                } catch (Exception e) {
                    log.error("Unable to stop recorder", e);
                }
            }
        });

        ACTIVE_RECORDERS.clear();
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



    // refactor for media... this function can be central for videos and screenshots
    private static Boolean checkIfSavingRequired(Status status){
        if(status == Status.PASSED && VideoRecordingConfiguration.recordOnSuccess()){
            return true;
        }
        else if(status == Status.FAILED && VideoRecordingConfiguration.recordOnFailures()){
            return true;
        }
        else if(status == Status.BROKEN && VideoRecordingConfiguration.recordOnFailures()){
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
