package org.blazedemo.customlisteners;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.Configuration;
import org.blazedemo.config.DriverConfiguration;
import org.blazedemo.utils.OSUtils;
import org.blazedemo.utils.reporting.ReportingManager;
import org.blazedemo.utils.reporting.config.AllureConfiguration;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

@Log4j2
public class AllureLifecycleListener implements TestLifecycleListener , ISuiteListener {

    @Override
    public void onStart(ISuite suite){
        suite.getXmlSuite.setName("Test Automation");
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", "Chrome")
                        .put("OS", OSUtils.getCurrentOS().name())
                        .put("Browser.Version", DriverConfiguration.browserType())
                        .put("URL", new Configuration("config/environment.properties").getRequiredProperty("BaseURL"))
                        .build(), AllureConfiguration.RESULTS_DIRECTORY + File.separator);

//            AllureBinaryManager.downloadAndExtract();
    }

    @Override
    public void beforeTestStop(TestResult result) {
        if (result.getStatus() == Status.FAILED || result.getStatus() == Status.BROKEN || result.getStatus() == Status.PASSED) {
            // Insert your screenshot or attachment logic here
            log.error("Attaching Logs for Test {}", result.getName());
            ReportingManager.collectAllureResultsArtifacts(result);
        }
    }
}
