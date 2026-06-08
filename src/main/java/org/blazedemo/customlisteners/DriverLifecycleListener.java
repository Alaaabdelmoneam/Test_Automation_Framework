package org.blazedemo.customlisteners;

import org.blazedemo.drivers.DriverManager;
import org.testng.*;

public class DriverLifecycleListener implements ITestListener {

    public void onTestStart(ITestResult result) {
        DriverManager.initDriver();
    }

    public void onTestSuccess(ITestResult result) {
        DriverManager.quitDriver();
    }

    public void onTestFailure(ITestResult result) {
        DriverManager.quitDriver();
    }

    public void onTestSkipped(ITestResult result) {
        DriverManager.quitDriver();
    }
}
