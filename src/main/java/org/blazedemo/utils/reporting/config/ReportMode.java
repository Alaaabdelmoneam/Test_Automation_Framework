package org.blazedemo.utils.reporting.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public enum ReportMode {
    SINGLE_FILE_MODE(),
    MULTI_FILE_MODE();

    public static ReportMode checkReportingMode(String mode){
        try {
            return ReportMode.valueOf(mode.toUpperCase(Locale.ROOT));
        }
        catch (Exception e){
            log.error("Couldn't get the mode {}", e.getMessage());
            throw  e;
        }
    }
}
