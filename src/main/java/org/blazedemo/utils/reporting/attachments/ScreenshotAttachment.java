package org.blazedemo.utils.reporting.attachments;

import io.qameta.allure.Allure;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.VideoRecordingConfiguration;
import org.blazedemo.drivers.DriverManager;
import org.blazedemo.utils.LoggerManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
public final class ScreenshotAttachment {
    public static void attachScreenshot(Path screenshotPath) {

        String screenshotPathString = screenshotPath.toString();
        String screenshotName = screenshotPathString.substring(screenshotPathString.lastIndexOf(File.separator));
        try {
            byte[] bytes = Files.readAllBytes(screenshotPath);
            Allure.addAttachment(
                    "Screenshot_" + screenshotName,
                    new ByteArrayInputStream(
                            bytes
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}