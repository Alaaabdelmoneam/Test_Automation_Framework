package org.blazedemo.customlisteners;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.blazedemo.config.DriverConfiguration;
import org.blazedemo.config.Configuration;
import org.blazedemo.media.videorecorder.RecordingManager;
import org.blazedemo.utils.OSUtils;
import org.blazedemo.utils.reporting.ReportingManager;
import org.blazedemo.utils.reporting.config.AllureConfiguration;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

@Slf4j
public class SuiteLevelListener implements ISuiteListener {

    @Override
    public void onFinish(ISuite suite) {
        RecordingManager.stopAllRecordings();
        log.info("All Recorders Cleaned Up!");
        ReportingManager.generateReport();

    }
}