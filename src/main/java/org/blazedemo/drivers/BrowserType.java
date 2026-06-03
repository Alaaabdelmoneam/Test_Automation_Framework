package org.blazedemo.drivers;

import java.util.Locale;

public enum BrowserType {
    CHROME,
    EDGE,
    FIREFOX;

    public static BrowserType from(String browserName){
        if (browserName == null){
            throw new RuntimeException(
                    "browser_type property is missing / properties file not found");
        }
        else{
            try {
                return valueOf(browserName.toUpperCase(Locale.ROOT));
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browserName);
            }
        }
    }
}

