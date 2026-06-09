package org.blazedemo.utils.reporting.attachments;

import io.qameta.allure.Allure;
import org.blazedemo.drivers.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public final class ScreenshotAttachment {

    public static void attachScreenshot() {

        byte[] screenshot =
                ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(
                                OutputType.BYTES
                        );

        Allure.addAttachment(
                "Screenshot",
                new ByteArrayInputStream(
                        screenshot
                )
        );
    }
}