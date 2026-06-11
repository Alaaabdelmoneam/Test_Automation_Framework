package org.blazedemo.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class TerminalUtils {
    public static void executeTerminalCommand(String... commandParts) {
        try {
            ProcessBuilder pb = new ProcessBuilder(commandParts);
            pb.inheritIO();

            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                log.error("Command failed with exit code {}", exitCode);
            }

        } catch (Exception e) {
            log.error("Failed executing command", e);
        }
    }
}