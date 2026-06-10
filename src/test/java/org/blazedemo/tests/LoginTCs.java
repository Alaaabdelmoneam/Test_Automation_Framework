package org.blazedemo.tests;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.drivers.DriverManager;
import org.blazedemo.media.ScreenshotManager;
import org.blazedemo.tests.basetest.BaseTest;
import org.blazedemo.utils.actions.ElementsActions;
import org.blazedemo.utils.reporting.attachments.ScreenshotAttachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v143.page.model.Screenshot;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.nio.file.Path;

@Log4j2
public class LoginTCs extends BaseTest {

    WebDriver driver;

    @Test
    public void TestAlerts(Method method){
        String testMethodName = method.getName();
        WebDriver webDriver = DriverManager.getDriver();
        webDriver.get("https://practice.expandtesting.com/scrollbars");
        Path screenshotPath = ScreenshotManager.takePageScreenshot(webDriver, testMethodName);
        By hidingButton = By.id("hidingButton");
        ElementsActions.scrollIntoElementJS(hidingButton);
        Path elementScreenshotPath = ScreenshotManager.takeElementScreenshot(hidingButton, testMethodName);
        ScreenshotAttachment.attachScreenshot(screenshotPath);
        ScreenshotAttachment.attachScreenshot(elementScreenshotPath);
    }

    @Test
    public void TC2(){
        WebDriver webDriver = DriverManager.getDriver();
        webDriver.get("https://www.google.com");
        Assert.assertTrue(webDriver.findElement(By.className("lnXdpd")).isDisplayed());
        Assert.assertTrue(webDriver.findElement(By.className("lnXdpd")).isDisplayed());
    }
}
