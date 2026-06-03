package org.blazedemo.config;

public final class DriverConfiguration {

    private static final Configuration CONFIG =
            new Configuration("config/DriverOptions.properties");

    public static String browserType() {
        return CONFIG.getRequiredProperty("browser_type");
    }

    public static boolean headless() {
        return (CONFIG.getBoolean("headless_mode"));
    }
}