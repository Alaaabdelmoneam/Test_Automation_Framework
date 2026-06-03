package customlisteners.examples;

import org.blazedemo.drivers.DriverManager;
import org.testng.*;

public class SuiteLevelListeners implements ISuiteListener {

    /**
     * This method is invoked before the SuiteRunner starts.
     *
     * @param suite The suite
     */
    public void onStart(ISuite suite) {
        System.out.println("START -> " + suite.getName());
    }

    /**
     * This method is invoked after the SuiteRunner has run all the tests in the suite.
     *
     * @param suite The suite
     */
    public void onFinish(ISuite suite) {
        System.out.println("FINISH -> " + suite.getName());
        DriverManager.quitDriver();
    }
}
