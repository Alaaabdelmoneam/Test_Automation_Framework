package customlisteners.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;

public class ExecutionLevelListeners implements IExecutionListener {
    private static final Logger log = LoggerFactory.getLogger(ExecutionLevelListeners.class);

    /** Invoked before the TestNG run starts. */
    public void onExecutionStart() {
        // not implemented
    }

    /** Invoked once all the suites have been run. */
    public void onExecutionFinish() {
        // not implemented
    }
}
