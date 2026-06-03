package customlisteners;

import org.blazedemo.drivers.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

public class DriverLifecycleListener implements IInvokedMethodListener{
    private static final Logger logger = LoggerFactory.getLogger(DriverLifecycleListener.class);

    // Method Level Listeners
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()){
            logger.info("Configuration method START -> {}", method.getTestMethod().getMethodName());
        }
        else if (method.isTestMethod()){
            logger.info("Test method START -> {}", method.getTestMethod().getMethodName());
            DriverManager.initDriver();
        }

    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()){
            logger.info("Configuration method END -> {}", method.getTestMethod().getMethodName());
        }
        else if (method.isTestMethod()){
            logger.info("Test method END -> {}", method.getTestMethod().getMethodName());
            logger.info("Test Result     -> {}", testResult.isSuccess());
            DriverManager.quitDriver();
        }
    }
}
