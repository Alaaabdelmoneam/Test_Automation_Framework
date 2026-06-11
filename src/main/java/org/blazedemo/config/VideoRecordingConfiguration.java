package org.blazedemo.config;


public class VideoRecordingConfiguration{

    private static final Configuration VIDEO_CONFIGURATION =
            new Configuration("config/video.properties");

    public static boolean isRecordingEnabled() {
        return Boolean.parseBoolean(
                VIDEO_CONFIGURATION.getRequiredProperty("video_recording"));
    }

    public static boolean recordOnFailures() {
        return Boolean.parseBoolean(
                VIDEO_CONFIGURATION.getRequiredProperty("save_record_on_failures"));
    }

    public static boolean recordOnSuccess() {
        return Boolean.parseBoolean(
                VIDEO_CONFIGURATION.getRequiredProperty("save_record_on_success"));
    }



    public static String getOutputDirectory() {
        return VIDEO_CONFIGURATION.getRequiredProperty(
                "video_output_directory");
    }

    public static int getFrameRate(){
        return Integer.parseInt(
                VIDEO_CONFIGURATION.getRequiredProperty("frame_rate"));
    }

    public static String  getCaptureMode(){
        return VIDEO_CONFIGURATION.getRequiredProperty("capture_mode");
    }

    public static String getWindowTitle() {
        return VIDEO_CONFIGURATION.getRequiredProperty("window_title");
    }

    public static String getExtension() {
        return VIDEO_CONFIGURATION.getRequiredProperty("extension");
    }


}