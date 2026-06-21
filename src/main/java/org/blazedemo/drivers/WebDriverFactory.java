package org.blazedemo.drivers;

import org.blazedemo.config.Configuration;
import org.blazedemo.config.DriverConfiguration;
import org.blazedemo.customlisteners.PopupHandlingListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Locale;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.support.events.EventFiringDecorator;

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
            WebDriver webDriver = valueOf(browserName.toUpperCase(Locale.ROOT)).createDriverInstance();
//            return webDriver;
            return new EventFiringDecorator(
                    new PopupHandlingListener())
                    .decorate(webDriver);

        } catch (IllegalArgumentException e) {
            log.error("Unsupported browser: {}", browserName, e);
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browserName);
        }
    }

    public abstract WebDriver createDriverInstance();

}