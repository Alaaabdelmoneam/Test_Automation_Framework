package org.blazedemo.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final List<WebDriver> activeDrivers = Collections.synchronizedList(new ArrayList<>());

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
        activeDrivers.add(driver);
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            // Clear the ThreadLocal map to prevent memory leaks in thread pools
            activeDrivers.remove(driver);
            driverThreadLocal.remove();
        }
        else {
            System.out.println("Can't quit driver as driver=null!");
        }
    }

    public static void stopAllDrivers() {
        log.info("Shutdown hook triggered. Cleaning up all active drivers...");
        synchronized (activeDrivers) {
            for (WebDriver driver : activeDrivers) {
                if (driver != null) {
                    try {
                        // Extract the raw driver if ThreadGuard wrapped it
                        WebDriver actualDriver = driver;
                        if (driver.getClass().getName().contains("ThreadGuard")) {
                            // ThreadGuard uses a WrappedDriver interface or proxy
                            if (driver instanceof org.openqa.selenium.WrapsDriver) {
                                actualDriver = ((org.openqa.selenium.WrapsDriver) driver).getWrappedDriver();
                            }
                        }
                        actualDriver.quit();
                    } catch (Exception e) {
                        log.error("Failed to force-quit driver: " + e.getMessage());
                    }
                }
            }
            activeDrivers.clear();
        }
    }
}
