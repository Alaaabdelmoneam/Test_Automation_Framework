package org.blazedemo.config;

public class EnvironmentConfiguration {
    private static final Configuration ENVIRONMENT_CONFIGURATION =
            new Configuration("config/environment.properties");

    public static String getBaseURL() {
        return ENVIRONMENT_CONFIGURATION.getRequiredProperty("BaseURL");
    }

}
