package org.blazedemo.utils.reporting;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.utils.FileUtilities;
import org.blazedemo.utils.reporting.config.AllureConfiguration;

import java.io.IOException;

@Log4j2
public class AllureReporting {

    public static void generateAllureReport() {
        if (!AllureConfiguration.ACCUMULATE_PAST_RESULTS) {
            FileUtilities.deleteDirectory("allure-results");
        }
        try {
            if (AllureConfiguration.SINGLE_FILE_REPORT) {
                if (AllureConfiguration.ACCUMULATE_PAST_RESULTS){
                    FileUtilities.copyDirectoryFiles(
                            AllureConfiguration.RESULTS_DIRECTORY,
                            AllureConfiguration.HISTORY_DIRECTORY
                    );
                }

                Process process = Runtime.getRuntime().exec(
                        "allure generate " + AllureConfiguration.RESULTS_DIRECTORY +
                                " --clean -o allure-report-single -single-file");
                process.waitFor();
                log.info("Allure report generated successfully in single-file mode.");
            }
            else {
                if (AllureConfiguration.ACCUMULATE_PAST_RESULTS){
                    FileUtilities.copyDirectoryFiles(
                            AllureConfiguration.RESULTS_DIRECTORY,
                            AllureConfiguration.HISTORY_DIRECTORY
                    );
                }
                Process process = Runtime.getRuntime().exec("allure generate" +
                        " " + AllureConfiguration.RESULTS_DIRECTORY +
                        " --clean -o " + AllureConfiguration.REPORT_OUTPUT_DIRECTORY);
                process.waitFor();
                log.info("Allure report generated successfully in multi-file mode.");
            }
            FileUtilities.deleteDirectory(AllureConfiguration.RESULTS_DIRECTORY);
        } catch (IOException | InterruptedException e) {
            log.error("Error occurred while generating Allure report", e);
        }

    }
}
