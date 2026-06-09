package org.blazedemo.utils.reporting.attachments;

public final class ApiAttachment {

    public static void attachRequest(
            String requestBody) {

        BasicAttachment.attachJson(
                "Request",
                requestBody
        );
    }

    public static void attachResponse(
            String responseBody) {

        BasicAttachment.attachJson(
                "Response",
                responseBody
        );
    }
}