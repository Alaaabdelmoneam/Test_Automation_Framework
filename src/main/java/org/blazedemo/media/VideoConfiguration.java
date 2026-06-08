package org.blazedemo.media;

import org.blazedemo.config.Configuration;


public class VideoConfiguration {

    private static final Configuration VIDEO_CONFIGURATION =
            new Configuration("config/video.properties");

    public static boolean isRecordingEnabled() {
        return Boolean.parseBoolean(
                VIDEO_CONFIGURATION.getRequiredProperty("video_recording"));
    }

    public static boolean recordOnlyFailures() {
        return Boolean.parseBoolean(
                VIDEO_CONFIGURATION.getRequiredProperty("record_only_failures"));
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

    public static Boolean  deleteIfTestPass(){
        return Boolean.parseBoolean(
                VIDEO_CONFIGURATION.getRequiredProperty("delete_if_test_passed"));
    }
    public static String getWindowTitle() {
        return VIDEO_CONFIGURATION.getRequiredProperty("window_title");
    }


}