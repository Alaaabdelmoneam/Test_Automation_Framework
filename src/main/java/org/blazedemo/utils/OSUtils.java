package org.blazedemo.utils;

public enum OSUtils {
    WINDOWS("gdigrab"),
    LINUX("x11grab"),
    MAC("avfoundation");

    private final String videoSourceInputFormat;

    OSUtils(String videoSourceInputFormat) {
        this.videoSourceInputFormat = videoSourceInputFormat;
    }

    public static OSUtils getCurrentOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return WINDOWS;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return LINUX;
        } else if (osName.contains("mac")) {
            return MAC;
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: " + osName);
        }
    }

    public static String getVideoSourceInputFormat() {
        return getCurrentOS().videoSourceInputFormat;
    }

}
