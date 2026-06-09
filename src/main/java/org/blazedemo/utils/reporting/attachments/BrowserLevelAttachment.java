package org.blazedemo.utils.reporting.attachments;

import io.qameta.allure.Allure;
import org.blazedemo.drivers.DriverManager;
import org.openqa.selenium.logging.LogType;

public final class BrowserLevelAttachment {

    private BrowserLevelAttachment(){}

    public static void attachPageSource() {

        Allure.addAttachment(
                "Page Source",
                "text/html",
                DriverManager.getDriver().getPageSource(),
                ".html"
        );
    }

    public static void attachCurrentURL() {
        BasicAttachment.attachText(
                "Current URL",
                DriverManager.getDriver().getCurrentUrl()
        );
    }

    public static void attachBrowserLogs() {

        StringBuilder logs =
                new StringBuilder();

        DriverManager.getDriver().manage()
                .logs()
                .get(LogType.BROWSER)
                .forEach(log ->
                        logs.append(log)
                                .append("\n"));

        BasicAttachment.attachText(
                "Browser Logs",
                logs.toString()
        );
    }



}