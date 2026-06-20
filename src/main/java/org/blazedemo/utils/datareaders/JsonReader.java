package org.blazedemo.utils.datareaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
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

    public static String getPropertyValue(String resourcePath, String propertyName) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream is = JsonReader.class
                    .getClassLoader()
                    .getResourceAsStream(resourcePath);

            if (is == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }

            JsonNode root = mapper.readTree(is);
            JsonNode node = root.get(propertyName);

            return node != null ? node.asText() : null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
}
