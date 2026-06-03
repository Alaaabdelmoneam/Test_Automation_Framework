package customlisteners.examples;

import org.testng.*;

/**
 * A listener that gets invoked before and after a method is invoked by TestNG. This listener will
 * be invoked for configuration and test methods irrespective of whether they passe/fail or get
 * skipped. This listener invocation can be disabled for SKIPPED tests through one of the below
 * mechanisms:
 *
 * <ul>
 *   <li>Command line parameter <code>alwaysRunListeners</code>
 *   <li>Build tool
 *   <li>Via {@code TestNG.alwaysRunListeners(false)}
 * </ul>
 */

public class MethodLevelListeners implements IInvokedMethodListener {


    // Method Level Listeners
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()){
            System.out.println("Configuration method START -> " + method.getTestMethod().getMethodName());
        }
        else if (method.isTestMethod()){
            System.out.println("Test method START -> " + method.getTestMethod().getMethodName());
        }

    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()){
            System.out.println("Configuration method END -> " + method.getTestMethod().getMethodName());
        }
        else if (method.isTestMethod()){
            System.out.println("Test method END -> " + method.getTestMethod().getMethodName());
            System.out.println("Test Result     -> " + testResult.isSuccess());
        }
    }
}
