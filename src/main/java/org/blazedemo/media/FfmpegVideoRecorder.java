package org.blazedemo.media;

import com.automation.remarks.video.recorder.VideoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.blazedemo.config.VideoRecordingConfiguration;
import org.blazedemo.utils.FileUtilities;
import org.blazedemo.utils.LoggerManager;
import org.blazedemo.utils.OSUtils;
import org.blazedemo.utils.TimeStampCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;



@Slf4j
public class FfmpegVideoRecorder implements VideoRecorder {

    private Process process ;
    private String videoPath ;

    @Override
    public synchronized String start(String testName) {

        videoPath = (
                LoggerManager.getLogFolderPath() + File.separator
                        + VideoRecordingConfiguration.getOutputDirectory() + File.separator
                        + "TC_" + testName + "-"
                        + Thread.currentThread().getName() + "_thread"
                        + ".mp4"
        );

        // Create folder if not created yet
        FileUtilities.createDirectory(
                videoPath.substring(0, videoPath.lastIndexOf(File.separator))
        );

        ProcessBuilder builder = new ProcessBuilder(
                getVideoConfigurationCommand()
        );

        builder.redirectErrorStream(true);

        try {

            Process p = builder.start();
            process = p;

            new Thread(() -> {
                try (BufferedReader reader =
                             new BufferedReader(
                                     new InputStreamReader(p.getInputStream()))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        log.info("[FFMPEG] {}", line);
                    }
                } catch (Exception e) {
                    log.error("Error reading FFmpeg output", e);
                }
            }).start();

        } catch (Exception e) {
            log.error("FFmpeg reader error", e);
            throw new RuntimeException("Unable to start video recording", e);
        }
        return null;
    }

    @Override
    public synchronized String stop() {

        Process p = process;
        String path = videoPath;

        if (p == null) return null;

        try {
            // graceful ffmpeg stop
            p.getOutputStream().write("q\n".getBytes());
            p.getOutputStream().flush();

            if (!p.waitFor(5, TimeUnit.SECONDS)) {
                p.destroyForcibly();
            }

        } catch (Exception e) {
            p.destroyForcibly();
        } finally {
            process = null;
            videoPath= null;
        }

        return path;
    }

    @Override
    public String getVideoPath() {
        return "";
    }

    private List<String> getVideoConfigurationCommand(){
        List<String> command = new ArrayList<>();

        // use ffmpeg executable to record video
        command.add("ffmpeg");

        // allow to overwrite? -y --> yes, -n --> no
        command.add("-y");

        command.add("-framerate");
        command.add(String.valueOf(VideoRecordingConfiguration.getFrameRate()));

        addInputSource(command);

        command.add("-c:v");
        command.add("libx264");

        command.add("-preset");
        command.add("ultrafast");

        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add(videoPath);

        log.info("FFmpeg command: {}", String.join(" ", command));
        return command;
    }

    private void addInputSource(List<String> command){

        String mode = VideoRecordingConfiguration.getCaptureMode();

        if (mode.equalsIgnoreCase("desktop")) {

            command.add("-f");
            command.add(OSUtils.getVideoSourceInputFormat());
            command.add("-i");
            command.add("desktop");

        } else if (mode.equalsIgnoreCase("window")) {

            command.add("-f");
            command.add(OSUtils.getVideoSourceInputFormat());
            command.add("-i");
            command.add("title=" + VideoRecordingConfiguration.getWindowTitle());
        } else {
            throw new IllegalArgumentException("Unsupported capture mode: " + mode);
        }

    }

}