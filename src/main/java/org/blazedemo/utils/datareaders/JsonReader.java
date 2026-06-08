package org.blazedemo.utils.datareaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import static org.blazedemo.utils.FileUtilities.getResource;

public class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T getTestDataFromClasspath(String filePath, Class<T> clazz) {
        try (InputStream is = getResource(filePath)) {
            return mapper.readValue(is, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON: " + filePath, e);
        }
    }

    public static <T> T getTestDataFromClasspath(String filePath, TypeReference<T> typeRef) {
        try (InputStream is = getResource(filePath)) {
            return mapper.readValue(is, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON: " + filePath, e);
        }
    }
}
