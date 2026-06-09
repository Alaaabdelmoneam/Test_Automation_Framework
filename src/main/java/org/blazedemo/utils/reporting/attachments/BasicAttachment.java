package org.blazedemo.utils.reporting.attachments;

import io.qameta.allure.Allure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class BasicAttachment {

    private BasicAttachment() {}

    public static void attachText(
            String name,
            String content) {

        Allure.addAttachment(name, content);
    }

    public static void attachJson(
            String name,
            String json) {

        Allure.addAttachment(
                name,
                "application/json",
                json,
                ".json"
        );
    }

    public static void attachFile(
            String name,
            Path file) {

        try {

            Allure.addAttachment(
                    name,
                    Files.newInputStream(file)
            );

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}