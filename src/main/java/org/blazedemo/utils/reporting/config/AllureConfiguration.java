package org.blazedemo.utils.reporting.config;

import org.blazedemo.config.Configuration;

import java.io.File;

public final class AllureConfiguration {
    private static final Configuration CONFIG =
            new Configuration(System.getProperty("user.dir"));

    public static final String RESULTS_DIRECTORY =
            CONFIG.getRequiredProperty("allure.results.directory");

    public static final String REPORT_OUTPUT_DIRECTORY =
            CONFIG.getRequiredProperty("report-dir");

    public static final String HISTORY_DIRECTORY =
            REPORT_OUTPUT_DIRECTORY + File.separator + "history";

    public static final boolean SINGLE_FILE_REPORT =
            CONFIG.getRequiredProperty("reportMode").equalsIgnoreCase("single-file");

    public static final boolean ACCUMULATE_PAST_RESULTS =
            CONFIG.getRequiredProperty("accumulateHistory").equalsIgnoreCase("true");

    public static final boolean OVERWRITE_REPORT =
            CONFIG.getRequiredProperty("overwriteReport").equalsIgnoreCase("true");

    public static final boolean AUTO_OPEN =
            CONFIG.getRequiredProperty("autoOpen").equalsIgnoreCase("true");

    public static final boolean ATTACH_LOGS =
            CONFIG.getRequiredProperty("attachBrowserLogs").equalsIgnoreCase("true");

    public static final boolean ATTACH_SCREENSHOTS =
            CONFIG.getRequiredProperty("attachScreenshots").equalsIgnoreCase("true");

    public static final boolean ATTACH_PAGE_SOURCE =
            CONFIG.getRequiredProperty("attachPageSource").equalsIgnoreCase("true");

    public static final boolean ATTACH_VIDEO =
            CONFIG.getRequiredProperty("attachVideo").equalsIgnoreCase("true");


}

