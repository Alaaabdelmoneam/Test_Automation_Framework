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

    @Test
    public void TC1(){
        DriverManager.getDriver().get("https://www.google.com");
        Assert.assertTrue(DriverManager.getDriver().findElement(By.className("lnXdpd")).isDisplayed());

    }

    @Test
    public void TC2(){
        DriverManager.getDriver().get("https://www.google.com");
        Assert.assertTrue(DriverManager.getDriver().findElement(By.className("lnXdpd")).isDisplayed());

    }
}
