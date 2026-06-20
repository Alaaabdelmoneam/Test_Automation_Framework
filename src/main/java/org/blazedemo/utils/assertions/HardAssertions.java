package org.blazedemo.utils.assertions;

import org.blazedemo.drivers.DriverManager;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.ElementsActions.ElementDisplayed;

public class HardAssertions {
    public static void assertElementDisplayed(By by){
        Assert.assertTrue(ElementDisplayed(by));
    }

    public static void assertElementVisible(By by){
        DriverManager.getDriver().findElement(by);
    }
}
