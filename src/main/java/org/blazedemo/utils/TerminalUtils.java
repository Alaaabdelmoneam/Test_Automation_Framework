package org.blazedemo.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class TerminalUtils {
    public static void executeTerminalCommand(String... commandParts) {
        try {
            Process process = Runtime.getRuntime().exec(commandParts); //allure generate -o reports --single-file --clean
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.error("Command failed with exit code: {}", exitCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error("Failed to execute terminal command: {}, error message {}",
                    String.join(" ", commandParts), e.getMessage());
        }
    }
}