package org.blazedemo.media;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.blazedemo.drivers.DriverManager;

@Log4j2
public class ScreenshotManager {

    public static String takePageScreenshot(String filePath) {

        try {
            WebDriver driver = DriverManager.getDriver();
            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            Files.copy(
                    source.toPath(),
                    Path.of(filePath),
                    StandardCopyOption.REPLACE_EXISTING
            );
            log.info("Page screenshot saved to: {}", filePath);
            return filePath;

        } catch (Exception e) {
            throw new RuntimeException("Failed to capture page screenshot", e);
        }
    }

    public static String takeElementScreenshot(
            By by,
            String filePath) {

        try {
            WebElement element = DriverManager.getDriver().findElement(by);
            File source = element.getScreenshotAs(OutputType.FILE);

            Files.copy(
                    source.toPath(),
                    Path.of(filePath),
                    StandardCopyOption.REPLACE_EXISTING
            );
            log.info("Element screenshot saved to: {}", filePath);
            return filePath;

        } catch (Exception e) {
            throw new RuntimeException("Failed to capture element screenshot", e);
        }
    }
}