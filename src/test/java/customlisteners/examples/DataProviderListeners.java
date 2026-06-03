package customlisteners.examples;

import org.testng.IDataProviderListener;
import org.testng.IDataProviderMethod;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

public class DataProviderListeners implements IDataProviderListener {

    /**
     * This method gets invoked just before a data provider is invoked.
     *
     * @param dataProviderMethod - A {@link IDataProviderMethod} object that contains details about
     *     the data provider that is about to be executed.
     * @param method - The {@link ITestNGMethod} method that is going to consume the data
     * @param iTestContext - The current test context
     */
    public void beforeDataProviderExecution(
            IDataProviderMethod dataProviderMethod, ITestNGMethod method, ITestContext iTestContext) {
        // not implemented
    }

    /**
     * This method gets invoked just after a data provider is invoked.
     *
     * @param dataProviderMethod - A {@link IDataProviderMethod} object that contains details about
     *     the data provider that got executed.
     * @param method - The {@link ITestNGMethod} method that received the data
     * @param iTestContext - The current test context
     */
    public void afterDataProviderExecution(
            IDataProviderMethod dataProviderMethod, ITestNGMethod method, ITestContext iTestContext) {
        // not implemented
    }

    /**
     * This method gets invoked when the data provider encounters an exception
     *
     * @param method - The {@link ITestNGMethod} method that received the data. A reference to the
     *     corresponding data provider can be obtained via {@link
     *     ITestNGMethod#getDataProviderMethod()}
     * @param ctx - The current test context
     * @param t - The {@link RuntimeException} that embeds the actual exception. Use {@link
     *     RuntimeException#getCause()} to get to the actual exception.
     */
    public void onDataProviderFailure(ITestNGMethod method, ITestContext ctx, RuntimeException t) {
        // not implemented
    }
}
