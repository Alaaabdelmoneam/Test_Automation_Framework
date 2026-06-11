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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        String outputDir = getOutputDirectory(command);

        if (AllureConfiguration.OVERWRITE_REPORT)
        {
            command.add("--clean");
        }

        log.info("Allure Generation command: {}", String.join(" ", command));
        log.info("report generated at: {}", outputDir);

        // generate report
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));

        Path indexFile = Paths.get(outputDir, "index.html");

        String reportName = indexFile.toString() + "index_" + TimeStampCreator.getCurrentTime()+".html";

        FileUtilities.renameFileOrDirectory(String.valueOf(indexFile), reportName);
        if (AllureConfiguration.AUTO_OPEN){
            openReport(reportName);
        }
    }
    public static void openReport(String reportPath){
        List<String> cmd = new ArrayList<>();
        cmd.add("cmd");
        cmd.add("/c");
        cmd.add(reportPath);
        TerminalUtils.executeTerminalCommand(cmd.toArray(new String[0]));
    }

    private static String getOutputDirectory(List<String> command){
        command.add("-o");
        String outputDirectory = null;
        if (checkReportingMode(AllureConfiguration.REPORT_MODE) == ReportMode.SINGLE_FILE_MODE){
            outputDirectory = AllureConfiguration.REPORT_OUTPUT_DIRECTORY
                    + File.separator
                    + AllureConfiguration.SINGLE_FILE_OUTPUT_DIRECTORY;

            command.add(outputDirectory);
            command.add("--single-file");
        }
        else if(checkReportingMode(AllureConfiguration.REPORT_MODE) == ReportMode.MULTI_FILE_MODE){
            outputDirectory = AllureConfiguration.REPORT_OUTPUT_DIRECTORY
                    + File.separator
                    + AllureConfiguration.MULTI_FILE_OUTPUT_DIRECTORY;

            command.add(outputDirectory);
        }
        else {
            log.error("Invalid Mode!");
            throw new RuntimeException("couldn't resolve output directory, please check the required mode");
        }

        // report name in dashboard (not the file name)
        command.add("--report-name");
        command.add("index_" + TimeStampCreator.getCurrentTime()+".html");

        FileUtilities.createDirectory(outputDirectory);
        return outputDirectory;
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
