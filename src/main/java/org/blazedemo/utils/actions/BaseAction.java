package org.blazedemo.utils.actions;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.WaitConfiguration;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.blazedemo.drivers.DriverManager.getDriver;

@Log4j2
public class BaseAction {
    protected static final WaitConfiguration CONFIG =
            new WaitConfiguration("config/waits.properties");

    protected static FluentWait<WebDriver> getDefaultWait() {
        return new WebDriverWait(
                getDriver(),
                CONFIG.getTimeoutSeconds())
                .pollingEvery(
                        CONFIG.getPollingIntervalMillis())
                .ignoring(NoAlertPresentException.class)
                .ignoring(NoSuchElementException.class)
                .withMessage(
                        CONFIG.getTimeoutMessage());
    }

    protected static FluentWait<WebDriver> getCustomizedWait(WaitConfiguration config) {
        return new WebDriverWait(
                getDriver(),
                config.getTimeoutSeconds())
                .pollingEvery(
                        config.getPollingIntervalMillis())
                .ignoring(NoAlertPresentException.class)
                .withMessage(
                        config.getTimeoutMessage());
    }
}
