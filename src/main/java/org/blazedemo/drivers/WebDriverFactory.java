package org.blazedemo.drivers;

import org.blazedemo.utils.ConfigurationReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Locale;

public enum WebDriverFactory {

    CHROME {
        @Override
        public WebDriver createDriver() {
            return new ChromeDriver((ChromeOptions) OptionsFactory.CHROME.createOptions());
        }
        },

    EDGE {
        @Override
        public WebDriver createDriver() {
            return new EdgeDriver((EdgeOptions) OptionsFactory.EDGE.createOptions());
        }
        },

    FIREFOX {
        @Override
        public WebDriver createDriver() {
            return new FirefoxDriver((FirefoxOptions) OptionsFactory.FIREFOX.createOptions());
        }
    };

    public abstract WebDriver createDriver();

    public static WebDriver getDriver() { // read browser from config
        BrowserType browserType = BrowserType.from(ConfigurationReader.readBrowserType());
        DriverManager.setDriver((valueOf(browserType.name())).createDriver());
        return DriverManager.getDriver();
    }
}