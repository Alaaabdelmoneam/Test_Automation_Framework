package org.blazedemo.utils.datareaders;

import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class PropertiesReader {

    public static void readProperties(Properties properties,
                                      String PropertiesFilePath)
    {
        try (InputStream stream =
                     Thread.currentThread()
                             .getContextClassLoader()
                             .getResourceAsStream(PropertiesFilePath)) {

            if (stream == null) {
                log.error("{} not found", PropertiesFilePath);
                throw new RuntimeException(
                        PropertiesFilePath + " not found");
            }

            properties.load(stream);

        } catch (Exception e) {
            log.error("Failed to load configuration", e);
            throw new RuntimeException(
                    "Failed to load configuration",
                    e
            );
        }
    }

    public static String getRequiredProperty(Properties properties, String key)
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

    public static boolean getBoolean(Properties properties, String key) {
        return Boolean.parseBoolean(
                getRequiredProperty(properties, key));
    }
}
