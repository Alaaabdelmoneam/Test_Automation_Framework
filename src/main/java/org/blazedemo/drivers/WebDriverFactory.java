package org.blazedemo.drivers;

import org.blazedemo.config.Configuration;
import org.blazedemo.config.DriverConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Locale;
import lombok.extern.log4j.Log4j2;

@Log4j2
public enum WebDriverFactory {

    CHROME {
        @Override
        public WebDriver createDriverInstance() {
            return new ChromeDriver((ChromeOptions) OptionsFactory.CHROME.createOptions());
        }
    },

    EDGE {
        @Override
        public WebDriver createDriverInstance() {
            return new EdgeDriver((EdgeOptions) OptionsFactory.EDGE.createOptions());
        }
    },

    FIREFOX {
        @Override
        public WebDriver createDriverInstance() {
            return new FirefoxDriver((FirefoxOptions) OptionsFactory.FIREFOX.createOptions());
        }
    };

    public static WebDriver createDriver(){
        String browserName = DriverConfiguration.browserType();
        try {
            return valueOf(browserName.toUpperCase(Locale.ROOT)).createDriverInstance();
        } catch (IllegalArgumentException e) {
            log.error("Unsupported browser: {}", browserName, e);
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browserName);
        }
    }

    public abstract WebDriver createDriverInstance();

}