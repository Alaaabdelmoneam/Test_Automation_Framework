package org.blazedemo.tests;

import org.blazedemo.drivers.DriverManager;
import org.blazedemo.drivers.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTCs {

    WebDriver driver;
    @BeforeMethod
    public void setup(){
        driver = WebDriverFactory.getDriver();
    }
    @Test
    public void TC(){
        driver.get("https://www.google.com");
        Assert.assertTrue(driver.findElement(By.className("lnXdpd")).isDisplayed());
    }

    @AfterMethod
    public void teardown(){
        DriverManager.quitDriver();
    }
}
