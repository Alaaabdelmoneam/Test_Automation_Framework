package org.blazedemo.customlisteners;

import lombok.extern.slf4j.Slf4j;
import org.blazedemo.annotations.UITest;
import org.blazedemo.drivers.DriverManager;
import org.testng.*;

@Slf4j
public class DriverLifecycleListener implements ITestListener, IExecutionListener {

    public void onExecutionStart(){
        // Don't need to use UI annotations, it just quits drivers if there is pending drivers
        Runtime.getRuntime().addShutdownHook(new Thread(DriverManager::stopAllDrivers));
    }

    public void onTestStart(ITestResult result) {
        if (result.getInstance() instanceof UITest){
            DriverManager.initDriver();
        }
    }

    public void onTestSuccess(ITestResult result) {
        if (result.getInstance() instanceof UITest){
            DriverManager.quitDriver();
        }
    }

    public void onTestFailure(ITestResult result) {
        if (result.getInstance() instanceof UITest){
            DriverManager.quitDriver();
        }
    }

    public void onTestSkipped(ITestResult result) {
        if (result.getInstance() instanceof UITest){
            DriverManager.quitDriver();
        }
    }

}
