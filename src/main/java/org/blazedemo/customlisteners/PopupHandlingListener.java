package org.blazedemo.customlisteners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class PopupHandlingListener implements WebDriverListener {

    @Override
    public void afterGet(WebDriver driver, String url) {

        System.out.println("Navigation finished: " + url);

        try {
            closeExtensionTab(driver);
        } catch (Exception e) {
            System.out.println("Failed to close extension tab");
        }
    }

    private void closeExtensionTab(WebDriver driver) {

        String currentWindow = driver.getWindowHandle();

        if (driver.getWindowHandles().size() <= 1)
            return;

        for (String handle : driver.getWindowHandles()) {

            if (!handle.equals(currentWindow)) {

                driver.switchTo().window(handle);
                driver.close();
            }
        }

        driver.switchTo().window(currentWindow);
    }
}