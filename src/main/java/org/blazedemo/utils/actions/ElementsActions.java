package org.blazedemo.utils.actions;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

/**
 * common actions of web elements like
 * @ Click
 * @ sending text
 * @ take screenshot
 * */

@Log4j2
public class ElementsActions extends BaseAction {

    // click an element
    public static void click(By by){

        getDefaultWait().until(d -> {
            try {
                d.findElement(by).click();
                return true;
            } catch (Exception e) {
                log.info("Failed to accept Alert " +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

    // send text to an element
    public static void sendText(By by, String text) {
        getDefaultWait().until(d -> {
            try {
                d.findElement(by).clear();
                d.findElement(by).sendKeys(text);
                return true;
            } catch (Exception e) {
                log.info("Failed to send text to element " +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

    // take screenshot
    public static void takeScreenshot() {
        // Implementation for taking screenshot

    }

    // upload file
    public static void uploadFile(By by, String filePath) {
        String AbsolutePath = System.getProperty("user.dir") + File.separator + filePath;
        getDefaultWait().until(d -> {
            try {
                d.findElement(by).sendKeys(AbsolutePath);
                return true;
            } catch (Exception e) {
                log.info("Failed to upload file " +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

    // scroll into element
    public static void scrollIntoElementSelenium(By by) {
        getDefaultWait().until(d -> {
            try {
                new Actions(d).scrollToElement(d.findElement(by)).perform();
                return true;

            } catch (Exception e) {
                log.info("Failed to scroll into element using selenium" +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

    // scroll into element using JS
    public static void scrollIntoElementJS(By by) {
        getDefaultWait().until(d -> {
            try {
                JavascriptExecutor js = (JavascriptExecutor) d;
                // Align the TOP of the element to the top of the viewport
                js.executeScript("arguments[0].scrollIntoView(true);", d.findElement(by));
                // OR: Align the BOTTOM of the element to the bottom of the viewport
//                js.executeScript("arguments[0].scrollIntoView(false);", d.findElement(by));
                return true;

            } catch (Exception e) {
                log.info("Failed to scroll into element " +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

}
