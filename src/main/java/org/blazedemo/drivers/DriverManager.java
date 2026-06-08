package org.blazedemo.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver(){
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            log.error("Driver is not initialized. Call setDriver() first.");
            throw new IllegalStateException(
                    "Driver is not initialized. Call setDriver() first.");
        }
        return driver;
    }

    public static void initDriver(){
        if (driverThreadLocal.get() == null) {
            setDriver(WebDriverFactory.createDriver());
        }
    }

    public static void setDriver(WebDriver driver){
        if (driverThreadLocal.get() != null){
            throw new IllegalStateException(
                    "Driver already initialized for this thread");
        }
        driverThreadLocal.set(ThreadGuard.protect(driver));
    }

    public static void quitDriver() {

        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            // Clear the ThreadLocal map to prevent memory leaks in thread pools
            driverThreadLocal.remove();
        }
        else {
            System.out.println("Can't quit driver as driver=null!");
        }
    }
}
