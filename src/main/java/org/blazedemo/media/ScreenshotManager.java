package org.blazedemo.media;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.ScreenshotConfiguration;
import org.blazedemo.utils.reporting.ArtifactRepository;
import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.blazedemo.drivers.DriverManager;

import static org.blazedemo.media.MediaUtilities.createParentMediaDirectory;
import static org.blazedemo.media.MediaUtilities.getUniqueName;

@Log4j2
public class ScreenshotManager{

    private static final String extension = ScreenshotConfiguration.getExtension();
    private static final String outputDirectory = ScreenshotConfiguration.getOutputDirectory();

    public static Path takePageScreenshot(WebDriver driver, String testName) {


        String screenshotPathString =
                getUniqueName(testName, extension,outputDirectory,
                        "pageTitle{", driver.getTitle(), "}");
        createParentMediaDirectory(screenshotPathString);
        try {
            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            Path screenshotPath = Path.of(screenshotPathString);
            Files.copy(
                    source.toPath(),
                    screenshotPath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            log.info("Page screenshot saved to {}", screenshotPath);
            ArtifactRepository.registerScreenshot(screenshotPathString, screenshotPath);
            return screenshotPath;

        } catch (Exception e) {
            throw new RuntimeException("Failed to capture page screenshot", e);
        }
    }

    public static Path takeElementScreenshot(By by, String testName) {

        // replace the : as it can't be used in file saving
        String byString = by.toString().replaceFirst(":", "");
        String screenshotPathString = getUniqueName(testName, extension,outputDirectory,
                "locator","'", byString, "'");
        Path screenshotPath = Path.of(screenshotPathString);
        try {
            WebElement element = DriverManager.getDriver().findElement(by);
            File source = element.getScreenshotAs(OutputType.FILE);

            Files.copy(
                    source.toPath(),
                    screenshotPath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            log.info("Element screenshot saved to: {}", screenshotPathString);
            ArtifactRepository.registerScreenshot(screenshotPathString, screenshotPath);
            return screenshotPath;

        } catch (Exception e) {
            throw new RuntimeException("Failed to capture element screenshot", e);
        }
    }

}