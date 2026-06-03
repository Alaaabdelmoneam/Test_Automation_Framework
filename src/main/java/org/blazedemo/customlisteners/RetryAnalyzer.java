package org.blazedemo.customlisteners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    /**
     * Returns true if the test method has to be retried, false otherwise.
     *
     * @param result The result of the test method that just ran.
     * @return true if the test method has to be retried, false otherwise.
     */

    int retries = 0;
    int MAX_RETRIES = 3;
    @Override
    public boolean retry(ITestResult result) {

        if (retries < MAX_RETRIES) {
            retries++;
            return true;
        }

        return false;
    }
}
