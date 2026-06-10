package org.blazedemo.media;

import org.blazedemo.utils.FileUtilities;
import org.blazedemo.utils.LoggerManager;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

public abstract class MediaUtilities {

    protected static void createParentMediaDirectory(String path){
        FileUtilities.createDirectory(
                path.substring(0, path.lastIndexOf(File.separator))
        );
    }
    protected static String getUniqueName
            (String testName, String extension,
             String outputDirectory, String... optionalData){

        return  (
                LoggerManager.getLogFolderPath() + File.separator
                        + outputDirectory + File.separator
                        + "TC_" + testName + "-"
                        + Arrays.toString(optionalData).replace(",", "")
                        + Thread.currentThread().getName() + "_thread"
                        + UUID.randomUUID()
                        + extension
        );
    }
}
