package org.blazedemo.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {

    private static final Properties properties = new Properties();
    static {
        try (InputStream stream =
                     Thread.currentThread()
                             .getContextClassLoader()
                             .getResourceAsStream("DriverOptions.properties")) {

            if (stream == null) {
                throw new RuntimeException(
                        "DriverOptions.properties not found");
            }

            properties.load(stream);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load configuration",
                    e
            );
        }
    }

    public static String getRequiredProperty(
            String key)
    {
        String value =
                properties.getProperty(key);

        if(value == null)
        {
            throw new RuntimeException(
                    "Missing property: " + key);
        }

        return value;
    }

    public static String readBrowserType() {
        return properties.getProperty("browser_type");
    }
}