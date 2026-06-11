package org.blazedemo.utils.reporting.config;

import org.blazedemo.config.Configuration;

import java.io.File;

public final class AllureConfiguration {
    private static final Configuration CONFIG =
            new Configuration("allure.properties");

    public static final String RESULTS_DIRECTORY =
            CONFIG.getRequiredProperty("allure.results.directory");

    public static final String REPORT_OUTPUT_DIRECTORY =
            CONFIG.getRequiredProperty("report-dir");

    public static final String HISTORY_DIRECTORY =
            REPORT_OUTPUT_DIRECTORY + File.separator + "history";

    public static final String REPORT_MODE =
            CONFIG.getRequiredProperty("reportMode");

    public static final boolean ACCUMULATE_PAST_RESULTS =
            CONFIG.getRequiredProperty("accumulateHistory").equalsIgnoreCase("true");

    public static final boolean OVERWRITE_REPORT =
            CONFIG.getRequiredProperty("overwriteReport").equalsIgnoreCase("true");

    public static final boolean AUTO_OPEN =
            CONFIG.getRequiredProperty("autoOpen").equalsIgnoreCase("true");

    public static final boolean ATTACH_BROWSER_LOGS =
            CONFIG.getRequiredProperty("attachBrowserLogs").equalsIgnoreCase("true");

    public static final boolean ATTACH_URL =
            CONFIG.getRequiredProperty("attachUrl").equalsIgnoreCase("true");

    public static final boolean ATTACH_SCREENSHOTS =
            CONFIG.getRequiredProperty("attachScreenshots").equalsIgnoreCase("true");

    public static final boolean ATTACH_PAGE_SOURCE =
            CONFIG.getRequiredProperty("attachPageSource").equalsIgnoreCase("true");

    public static final boolean ATTACH_VIDEO =
            CONFIG.getRequiredProperty("attachVideo").equalsIgnoreCase("true");

    public static final boolean ATTACH_ON_SUCCESS =
            CONFIG.getRequiredProperty("attachOnSuccess").equalsIgnoreCase("true");

    public static final String SINGLE_FILE_OUTPUT_DIRECTORY =
            CONFIG.getRequiredProperty("singleFileOutputDirectory");

    public static final String MULTI_FILE_OUTPUT_DIRECTORY =
            CONFIG.getRequiredProperty("multiFileOutputDirectory");

}

