package org.blazedemo.tests;

import org.blazedemo.drivers.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTCs {

    @Test
    public void TC(){
        WebDriver driver = WebDriverFactory.getDriver();
        driver.get("https://www.google.com");
        Assert.assertTrue(driver.findElement(By.className("lnXdpd")).isDisplayed());
        driver.quit();
    }
}
