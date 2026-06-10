package org.blazedemo.media.videorecorder;

import java.util.Locale;

public enum RecorderType {
    ffmpeg;

    public static VideoRecorder createRecorder(String recorderType){
        switch (RecorderType.valueOf(recorderType.toLowerCase(Locale.ROOT))){
            // add other recorders here if required
            case ffmpeg ->
            {
                return new FfmpegVideoRecorder();
            }
        }
        return null;
    }
}
