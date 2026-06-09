package org.blazedemo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStampCreator {
    private TimeStampCreator(){}

    public static String  getCurrentTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        return myDateObj.format(myFormatObj);
    }
}

