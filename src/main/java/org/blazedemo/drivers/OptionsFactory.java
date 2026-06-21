package org.blazedemo.drivers;

import org.blazedemo.config.Configuration;
import org.blazedemo.config.DriverConfiguration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.util.HashMap;
import java.util.Map;

public enum OptionsFactory {

    CHROME{
        public ChromeOptions createOptions(){
            // add your options parsing and validations here
            ChromeOptions options = new ChromeOptions();
            if (isHeadlessEnabled()){
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-gpu-compositing");

            }
            if (isNotificationsBlocked()){
                options.setExperimentalOption(
                        "prefs",
                        Map.of(
                                "profile.default_content_setting_values.notifications",
                                2
                        )
                );
            }

            return options;
        }
    },
    EDGE{
        public EdgeOptions createOptions(){
            // add your options parsing and validations here
            EdgeOptions options = new EdgeOptions();
            if (isHeadlessEnabled()){
                options.addArguments("--headless=new");
            }
            if (isNotificationsBlocked()){
                options.setExperimentalOption(
                        "prefs",
                        Map.of(
                                "profile.default_content_setting_values.notifications",
                                2
                        )
                );
            }

            return options;
        }
    },
    FIREFOX{
        public FirefoxOptions createOptions(){
            // add your options parsing and validations here
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadlessEnabled()){
                options.addArguments("--headless");
            }

            if (isNotificationsBlocked()){
                options.addPreference(
                        "dom.webnotifications.enabled",
                        false
                );
            }
            return options;
        }
    };

    protected static boolean isHeadlessEnabled() {
        return DriverConfiguration.headless();
    }

    protected static boolean isNotificationsBlocked() {
        return DriverConfiguration.blockNotifications();
    }
    abstract public AbstractDriverOptions<?> createOptions();
}
