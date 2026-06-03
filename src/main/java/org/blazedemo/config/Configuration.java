package org.blazedemo.config;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private final Properties properties = new Properties();

    public Configuration(String PropertiesFilePath){
        try (InputStream stream =
                     Thread.currentThread()
                             .getContextClassLoader()
                             .getResourceAsStream(PropertiesFilePath)) {

            if (stream == null) {
                throw new RuntimeException(
                        PropertiesFilePath + " not found");
            }

            properties.load(stream);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load configuration",
                    e
            );
        }
    }


    public String getRequiredProperty(
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

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(
                getRequiredProperty(key));
    }
}