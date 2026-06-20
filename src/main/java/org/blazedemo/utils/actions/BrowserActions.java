package org.blazedemo.utils.actions;

import org.blazedemo.drivers.DriverManager;

public class BrowserActions {
    public static void navigateTo(String URL){
        DriverManager.getDriver().get(URL);
    }
    public static String getCurrentUrl(){
        return DriverManager.getDriver().getCurrentUrl();
    }
}
