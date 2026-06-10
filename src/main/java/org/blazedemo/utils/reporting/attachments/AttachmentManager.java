package org.blazedemo.utils.reporting.attachments;

import org.blazedemo.utils.reporting.ArtifactRepository;
import org.blazedemo.utils.reporting.config.AllureConfiguration;


public class AttachmentManager {

    private AttachmentManager(){}

    public static void collectTestArtifacts(String testId){
        if (AllureConfiguration.ATTACH_SCREENSHOTS){
            ArtifactRepository
                    .getScreenshot(testId)
                    .ifPresent(
                            ScreenshotAttachment::attachScreenshot
                    );
        }
        if (AllureConfiguration.ATTACH_LOGS){
            BrowserLevelAttachment.attachBrowserLogs();
        }
        if (AllureConfiguration.ATTACH_VIDEO){
            ArtifactRepository
                    .getVideo(testId)
                    .ifPresent(
                            VideoAttachment::attachVideo
                    );
        }
        if (AllureConfiguration.ATTACH_PAGE_SOURCE){
            BrowserLevelAttachment.attachPageSource();
        }
    }
}
