package org.blazedemo.config;

import java.util.Properties;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.utils.datareaders.PropertiesReader;

@Log4j2
public class Configuration {

    private final Properties properties = new Properties();

    public Configuration(String PropertiesFilePath){
        PropertiesReader.readProperties(properties, PropertiesFilePath);
    }

    public String getRequiredProperty(
            String key)
    {
        return PropertiesReader.getRequiredProperty(properties, key);
    }

    public boolean getBoolean(String key) {
        return PropertiesReader.getBoolean(properties, key);
    }
}