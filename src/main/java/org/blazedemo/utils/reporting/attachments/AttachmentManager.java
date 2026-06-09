//package org.blazedemo.utils.reporting.attachments;
//
//import org.blazedemo.utils.reporting.ArtifactRepository;
//import org.blazedemo.media.RecordingRepository;
//import org.blazedemo.utils.reporting.config.AllureConfiguration;
//
//import java.nio.file.Path;
//
//public class AttachmentManager {
//
//    private AttachmentManager(){}
//
//    public static void collectTestArtifacts(Path videoPath){
//        if (AllureConfiguration.ATTACH_SCREENSHOTS){
//            ScreenshotAttachment.attachScreenshot();
//        }
//        if (AllureConfiguration.ATTACH_LOGS){
//            BrowserLevelAttachment.attachBrowserLogs();
//        }
//        if (AllureConfiguration.ATTACH_VIDEO){
//            ArtifactRepository
//                    .getVideo(testId)
//                    .ifPresent(
//                            VideoAttachment::attach
//                    );
//        }
//        if (AllureConfiguration.ATTACH_PAGE_SOURCE){
//            BrowserLevelAttachment.attachPageSource();
//        }
//
//    }
//}
