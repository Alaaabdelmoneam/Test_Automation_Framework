package org.blazedemo.utils.actions;

import lombok.extern.log4j.Log4j2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Common actions related to handling alerts in the application can be implemented here.
 * This may include methods for accepting, dismissing, and retrieving text from alerts.
 * */
@Log4j2
public class AlertActions extends BaseAction {

    /**
     * Waits for an alert to be present and accepts it.
     */
    public static void acceptAlert() {
        getDefaultWait().until(d -> {
            try {
                d.switchTo().alert().accept();
                return true;
            } catch (Exception e) {
                log.info("Failed to accept Alert " +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

    public static void dismissAlert(WebDriver driver, By by) {
        // Implementation for dismissing an alert
        getDefaultWait().until(d -> {
            try {
                d.switchTo().alert().dismiss();
                return true;
            } catch (Exception e) {
                log.info("Failed to dismiss Alert " +
                        "due to error {} ,retrying...", e.getMessage());
                return false;
            }
        });
    }

    public static String getAlertText(){
        // Implementation for dismissing an alert
        getDefaultWait().until(d -> {
            try {
                return d.switchTo().alert().getText();
            } catch (Exception e) {
                log.info("Failed to get text from Alert " +
                        "due to error {} ,retrying...", e.getMessage());
                return null;
            }
        });
        return null;
    }
    public static void typeAlertText(String text){
        // Implementation for dismissing an alert
        getDefaultWait().until(d -> {
            try {
                d.switchTo().alert().sendKeys(text);
                return true;
            } catch (Exception e) {
                log.info("Failed to type text into Alert " +
                        "due to error {} ,retrying...", e.getMessage());
                return null;
            }
        });
    }

}
