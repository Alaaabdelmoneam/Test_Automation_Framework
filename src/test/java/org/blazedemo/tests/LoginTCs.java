package org.blazedemo.tests;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.drivers.DriverManager;
import org.blazedemo.drivers.WebDriverFactory;
import org.blazedemo.tests.basetest.BaseTest;
import org.blazedemo.utils.actions.AlertActions;
import org.blazedemo.utils.actions.ElementsActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Log4j2
public class LoginTCs extends BaseTest {

    WebDriver driver;

    @Test
    public void TestAlerts(){
        DriverManager.getDriver().get("https://practice.expandtesting.com/scrollbars");
//        ElementsActions.scrollIntoElementSelenium(By.id("open-window-with-onclose-alert"));
        ElementsActions.scrollIntoElementJS(By.id("hidingButton"));

    }

    @Test
    public void TC2(){
        DriverManager.getDriver().get("https://www.google.com");
        Assert.assertTrue(DriverManager.getDriver().findElement(By.className("lnXdpd")).isDisplayed());
        Assert.assertTrue(DriverManager.getDriver().findElement(By.className("lnXdpddasdxx")).isDisplayed());

    }
}
