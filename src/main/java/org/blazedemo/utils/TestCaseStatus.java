package org.blazedemo.utils;

public enum TestCaseStatus {
    PASSED,
    FAILED,
    SKIPPED;

    public static String getStatusText(int status) {
        return switch (status) {
            case 1 -> PASSED.toString();
            case 2 -> FAILED.toString();
            case 3 -> SKIPPED.toString();
            default -> throw new IllegalArgumentException("Unknown test case status: " + status);
        };
    }
}
