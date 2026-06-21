package org.blazedemo.utils.actions;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

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
                scrollIntoElementJS(by);
                d.findElement(by).click();
                return true;
            } catch (Exception e) {
                log.info("Failed to click element " +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

    // send text to an element
    public static void sendText(By by, String text) {
        getDefaultWait().until(d -> {
            try {
                scrollIntoElementJS(by);
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

    // get element text
    public static String getText(By by){
        try {
            return getDefaultWait().until(d -> {
                try {
                    scrollIntoElementJS(by);
                    return d.findElement(by).getText();
                } catch (Exception e) {
                    log.info("Failed to get text of element due to error {} ,retrying...", e.getMessage());
                    return null;
                }
            });
        } catch (Exception e) {
            log.info("couldn't get element text with locator {}: {}", by, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // get element text
    public static void hover(By by){
        getDefaultWait().until(d -> {
            try {
                scrollIntoElementJS(by);
                new Actions(d).moveToElement(d.findElement(by)).perform();
                return true;
            } catch (Exception e) {
                log.info("Failed to hover to element " +
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
                scrollIntoElementJS(by);
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

    /**
     *  scroll into element using JS
     *  Align the element to the center of the viewport
     *  block value can be:
     *  'start' for aligning to start of page
     *  'end': for aligning to end of page
     *  inline:'nearest' prevents un-necessary horizontal scrolling.
    **/
    public static void scrollIntoElementJS(By by) {
        getDefaultWait().until(d -> {
            try {
                JavascriptExecutor js = (JavascriptExecutor) d;

                js.executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
                        d.findElement(by)
                );
                return true;

            } catch (Exception e) {
                log.info("Failed to scroll into element " +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

    public static void submitForm(By by){
        DriverManager.getDriver().findElement(by).submit();
    }

    @Step("check if element {by} found")
    public static boolean ElementDisplayed(By by){
        try {
            return getDefaultWait().until(d -> {
                try {
                    scrollIntoElementJS(by);
                    return d.findElement(by).isDisplayed();
                } catch (Exception e) {
                    log.info("Failed to get display state of element due to error {} ,retrying...", e.getMessage());
                    return false;
                }
            });
        } catch (TimeoutException e) {
            log.info("ElementDisplayed timed out {}: {}... returning false(not found)", by, e.getMessage());
            return false;
        }
        catch (Exception e) {
            log.info("ElementDisplayed failed for {}: {}", by, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void SelectElementFromDropdown(By by, String value){
        getDefaultWait().until(d -> {
            try {
                scrollIntoElementJS(by);
                Select dropdown = new Select(d.findElement(by));
                dropdown.selectByVisibleText(value);
                log.info("Selected element successfully");
                return true;
            } catch (Exception e) {
                log.info("Failed to select element {} from dropdown " +
                        "due to error {} ,retrying...", value, e.getMessage());
                return false;
            }
        });
    }
}
