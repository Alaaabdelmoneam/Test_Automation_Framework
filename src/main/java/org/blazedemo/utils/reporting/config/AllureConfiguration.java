package org.blazedemo.utils.reporting.config;

import org.blazedemo.config.Configuration;

import java.io.File;

public final class AllureConfiguration {
    private static final Configuration CONFIG =
            new Configuration("allure.properties");

    public static final boolean GENERATE_REPORT =
            CONFIG.getBoolean("generate_report");

    public static final String RESULTS_DIRECTORY =
            CONFIG.getRequiredProperty("allure.results.directory");

    public static final String REPORT_OUTPUT_DIRECTORY =
            CONFIG.getRequiredProperty("report-dir");

    public static final String REPORT_MODE =
            CONFIG.getRequiredProperty("reportMode");

    public static final String SINGLE_FILE_OUTPUT_DIRECTORY =
            AllureConfiguration.REPORT_OUTPUT_DIRECTORY
                    + File.separator
                    + CONFIG.getRequiredProperty("singleFileOutputDirectory");

    public static final String MULTI_FILE_OUTPUT_DIRECTORY =
            AllureConfiguration.REPORT_OUTPUT_DIRECTORY +
                    File.separator +
                    CONFIG.getRequiredProperty("multiFileOutputDirectory");

    public static final String HISTORY_DIRECTORY =
            MULTI_FILE_OUTPUT_DIRECTORY + File.separator + "history";

//    public static final String OUTPUT_DIRECTORY =
//            CONFIG.getRequiredProperty("multiFileOutputDirectory");

    public static final boolean ACCUMULATE_PAST_RESULTS =
            CONFIG.getBoolean("accumulateHistory");

    public static final String RESULTS_HISTORY_DIRECTORY =
            RESULTS_DIRECTORY + File.separator + "history";

    public static final boolean OVERWRITE_REPORT =
            CONFIG.getBoolean("overwriteReport");

    public static final boolean AUTO_OPEN =
            CONFIG.getBoolean("autoOpen");

    public static final boolean ATTACH_BROWSER_LOGS =
            CONFIG.getBoolean("attachBrowserLogs");

    public static final boolean ATTACH_URL =
            CONFIG.getBoolean("attachUrl");

    public static final boolean ATTACH_SCREENSHOTS =
            CONFIG.getBoolean("attachScreenshots");

    public static final boolean ATTACH_PAGE_SOURCE =
            CONFIG.getBoolean("attachPageSource");

    public static final boolean ATTACH_VIDEO =
            CONFIG.getBoolean("attachVideo");

    public static final boolean ATTACH_ON_SUCCESS =
            CONFIG.getBoolean("attachOnSuccess");
}

