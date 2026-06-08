package org.blazedemo.media;

public class RecordingManager {

    private static final ThreadLocal<VideoRecorder>
            recorder = new ThreadLocal<>();

    public static void startRecording(
            String testName) {


        if(VideoConfiguration.isRecordingEnabled()) {

            VideoRecorder videoRecorder =
                    new FfmpegVideoRecorder();

            videoRecorder.start(testName);

            recorder.set(videoRecorder);
        }
        else {
            recorder.set(
                    new NoOpVideoRecorder());
        }
    }

    public static String stopRecording() {

        VideoRecorder videoRecorder =
                recorder.get();

        if(videoRecorder == null) {
            return null;
        }

        String path =
                String.valueOf(videoRecorder.stop());

        recorder.remove();

        return path;
    }

    public static void main (String[] args) throws InterruptedException {
        RecordingManager.startRecording("main");
        Thread.sleep(5000);
        RecordingManager.stopRecording();
    }
}
