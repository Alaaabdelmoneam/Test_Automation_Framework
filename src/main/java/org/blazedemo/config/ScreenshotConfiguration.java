package org.blazedemo.config;

public class ScreenshotConfiguration{

    private static final Configuration SCREENSHOT_CONFIGURATION =
            new Configuration("config/screenshot.properties");

    public static String getExtension() {
        return SCREENSHOT_CONFIGURATION.getRequiredProperty("extension");
    }
    public static String getOutputDirectory() {
        return SCREENSHOT_CONFIGURATION.getRequiredProperty(
                "screenshot_output_directory");
    }
}
