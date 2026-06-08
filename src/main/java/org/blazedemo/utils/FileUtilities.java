package org.blazedemo.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.blazedemo.utils.datareaders.JsonReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class FileUtilities {

    /**     * Delete a directory and all its contents using the provided path     *
     * @param path the directory path to delete     * @throws RuntimeException if deletion fails     */
    public static void deleteDirectory(String path) {
        try {
            File directory = new File(path);
            if (directory.exists()) {
                FileUtils.deleteDirectory(directory);
                log.info("Directory deleted successfully: {}", directory.getAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Failed to delete directory: {}", path, e);
            throw new RuntimeException("Failed to delete directory: " + path, e);
        }
    }

    /**     * Create a directory using the provided path     *
     * @param path the directory path to create     * @throws RuntimeException if creation fails     */
    public static void createDirectory(String path) {
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                FileUtils.forceMkdir(directory);
                log.info("Directory created successfully: {}", directory.getAbsolutePath());
            }
            else {
                log.warn("Directory already exists: {}", directory.getAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Failed to create directory: {}", path, e);
            throw new RuntimeException("Failed to create directory: " + path, e);
        }
    }

    /**     * Rename a file or directory     *
     * @param oldPath the current path of the file/directory     * @param newPath the new path for the file/directory     * @throws RuntimeException if renaming fails     */
    public static void renameFileOrDirectory(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);

        if (!oldFile.exists()) {
            log.error("Source file/directory does not exist: {}", oldFile.getAbsolutePath());
            throw new RuntimeException("Source file/directory does not exist: " + oldPath);
        }

        if (newFile.exists()) {
            log.error("Target file/directory already exists: {}", newFile.getAbsolutePath());
            throw new RuntimeException("Target file/directory already exists: " + newPath);
        }

        if (!oldFile.renameTo(newFile)) {
            log.error("Failed to rename from {} to {}", oldFile.getAbsolutePath(), newFile.getAbsolutePath());
            throw new RuntimeException("Failed to rename from " + oldPath + " to " + newPath);
        }
    }

    /**
     * Checks if file exists in resources and gets it
     * */
    public static InputStream getResource(String filePath) {
        InputStream is = JsonReader.class.getClassLoader().getResourceAsStream(filePath);

        if (is == null) {
            log.error("File not found in classpath: {}", filePath);
            throw new RuntimeException("File not found in classpath: " + filePath);
        }

        return is;
    }

    public static void deleteFile(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            if(FileUtils.deleteQuietly(file)){
                log.info("File deleted successfully: {}", file.getAbsolutePath());
            }
            else {
                log.error("Failed to delete file: {}", file.getAbsolutePath());
                throw new RuntimeException("Failed to delete file: " + file.getAbsolutePath());
            }
        }
        else {
            log.error("File not found: {}", file.getAbsolutePath());
            throw new RuntimeException("File not found: " + file.getAbsolutePath());
        }
    }

    public static void createFile(String fileName, String path){
        File file = new File(path + File.separator + fileName);
        try {
            if (file.createNewFile()) {
                log.info("File created successfully: {}", file.getAbsolutePath());
            }
            else {
                log.warn("File already exists: {}", file.getAbsolutePath());
                throw new RuntimeException("File already exists: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Failed to create file: {}", fileName, e);
            throw new RuntimeException("Failed to create file: " + fileName, e);
        }
    }

}