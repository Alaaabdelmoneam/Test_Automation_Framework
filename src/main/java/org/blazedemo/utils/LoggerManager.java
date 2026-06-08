package org.blazedemo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class LoggerManager {

    private LoggerManager() {}

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static String getLogFolderPath(){
        LoggerContext context = (LoggerContext) LogManager.getContext(false);

        FileAppender appender =
                (FileAppender) context.getConfiguration()
                        .getAppender("fileAppender");

        String logFilePath = appender.getFileName();
        return Paths.get(logFilePath).getParent().toString();
    }
}