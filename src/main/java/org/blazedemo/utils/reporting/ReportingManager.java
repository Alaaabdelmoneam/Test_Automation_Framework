package org.blazedemo.utils.reporting;

import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.VideoRecordingConfiguration;
import org.blazedemo.drivers.DriverManager;
import org.blazedemo.media.ScreenshotManager;
import org.blazedemo.media.videorecorder.RecordingManager;
import org.blazedemo.utils.FileUtilities;
import org.blazedemo.utils.TerminalUtils;
import org.blazedemo.utils.TimeStampCreator;
import org.blazedemo.utils.reporting.attachments.BrowserLevelAttachment;
import org.blazedemo.utils.reporting.attachments.ScreenshotAttachment;
import org.blazedemo.utils.reporting.attachments.VideoAttachment;
import org.blazedemo.utils.reporting.config.AllureConfiguration;
import org.blazedemo.utils.reporting.config.ReportMode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.blazedemo.utils.reporting.config.ReportMode.checkReportingMode;

@Log4j2
public class ReportingManager {

    private ReportingManager(){}

    public static void collectAllureResultsArtifacts(TestResult result){
        if (!AllureConfiguration.ATTACH_ON_SUCCESS && result.getStatus() == Status.PASSED)
            return;

        if (AllureConfiguration.ATTACH_VIDEO && VideoRecordingConfiguration.isRecordingEnabled()){
            Optional<Path> video =
                    Optional.ofNullable(RecordingManager.finishRecording(result.getName(), result.getStatus()));
            video.ifPresent(VideoAttachment::attachVideo);
        }

        if (AllureConfiguration.ATTACH_SCREENSHOTS){
            Optional<Path> screenshot =
                    Optional.of(ScreenshotManager.takePageScreenshot(DriverManager.getDriver(), result.getName()));

            screenshot.ifPresent(ScreenshotAttachment::attachScreenshot);
        }
        if (AllureConfiguration.ATTACH_BROWSER_LOGS){
            BrowserLevelAttachment.attachBrowserLogs();
        }

        if (AllureConfiguration.ATTACH_PAGE_SOURCE){
            BrowserLevelAttachment.attachPageSource();
        }

        if (AllureConfiguration.ATTACH_URL){
            BrowserLevelAttachment.attachCurrentURL();
        }
    }
    public static void generateReport(){
        List<String> command = new ArrayList<>();

        command.add("cmd");
        command.add("/c");
        command.add("allure");
        command.add("generate");

        // allow to overwrite? -y --> yes, -n --> no
        command.add(AllureConfiguration.RESULTS_DIRECTORY);

        String outputDir = configureReportingMode(command);

        if (AllureConfiguration.OVERWRITE_REPORT)
        {
            command.add("--clean");
        }

        // generate with history
        if (AllureConfiguration.ACCUMULATE_PAST_RESULTS
                && FileUtilities.checkIfFileExists(AllureConfiguration.HISTORY_DIRECTORY)
                && FileUtilities.checkIfFileExists(AllureConfiguration.RESULTS_DIRECTORY)){
            try {

                FileUtilities.createDirectory(AllureConfiguration.RESULTS_HISTORY_DIRECTORY);

                FileUtilities.copyDirectoryFiles(
                        AllureConfiguration.HISTORY_DIRECTORY,
                        AllureConfiguration.RESULTS_HISTORY_DIRECTORY

                );
                log.info("Files Copied");
            }
            catch (IOException e){
                log.error("Couldn't Generate Report with history", e);
                throw new RuntimeException("Couldn't Generate Report with history", e);
            }
        }

        // generate report
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));

        // Delete Results Directory
        FileUtilities.deleteDirectory(AllureConfiguration.RESULTS_DIRECTORY);

        log.info("report generated at: {}", outputDir);

        Path indexFile = Paths.get(outputDir, "index.html");

        if (checkReportingMode(AllureConfiguration.REPORT_MODE) == ReportMode.SINGLE_FILE_MODE){
            String reportName = indexFile.toString() + "index_" + TimeStampCreator.getCurrentTime()+".html";
            FileUtilities.renameFileOrDirectory(String.valueOf(indexFile), reportName);
            if (AllureConfiguration.AUTO_OPEN){
                openSingleReport(reportName);
            }
        } else if (checkReportingMode(AllureConfiguration.REPORT_MODE) == ReportMode.MULTI_FILE_MODE) {
            if (AllureConfiguration.AUTO_OPEN) {
                log.warn("""
            Auto-open is disabled for multi-file Allure reports.
            Open the report using:
            allure open {}
            """,
                        getOutputDirectory()
                );
            }
        }
    }
    public static void openSingleReport(String reportPath){
        List<String> cmd = new ArrayList<>();
        cmd.add("cmd");
        cmd.add("/c");
        cmd.add(reportPath);
        TerminalUtils.executeTerminalCommand(cmd.toArray(new String[0]));
    }

    private static String configureReportingMode(List<String> command){
        command.add("-o");
        String outputDirectory = getOutputDirectory();
        command.add(outputDirectory);
        if (checkReportingMode(AllureConfiguration.REPORT_MODE) == ReportMode.SINGLE_FILE_MODE){
            command.add("--single-file");
        }

        // report name in dashboard (not the file name)
        command.add("--report-name");
        command.add("index_" + TimeStampCreator.getCurrentTime()+".html");

        FileUtilities.createDirectory(outputDirectory);
        return outputDirectory;
    }

    public static String getOutputDirectory(){
        if (checkReportingMode(AllureConfiguration.REPORT_MODE) == ReportMode.SINGLE_FILE_MODE) {
            return AllureConfiguration.SINGLE_FILE_OUTPUT_DIRECTORY;
        } else if(checkReportingMode(AllureConfiguration.REPORT_MODE) == ReportMode.MULTI_FILE_MODE){
            return AllureConfiguration.MULTI_FILE_OUTPUT_DIRECTORY;
        }
        else {
            log.error("Invalid Mode!");
            throw new RuntimeException("couldn't resolve output directory, please check the required mode");
        }
    }
    public static void main(String[] args){
//        checkReportingMode(AllureConfiguration.REPORT_MODE);
        List<String> command = List.of(
                "cmd",
                "/c",
                "allure",
                "--version"
        );
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }
}
