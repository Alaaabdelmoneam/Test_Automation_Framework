package org.blazedemo.drivers;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.config.Configuration;
import org.blazedemo.config.DriverConfiguration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public enum OptionsFactory {

    CHROME{
        public ChromeOptions createOptions(){
            // add your options parsing and validations here
            ChromeOptions options = new ChromeOptions();
            if (isHeadlessEnabled()){
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            loadExtensions(options, ".crx");
            if (isNotificationsBlocked()){
                Map<String, Object> prefs = new HashMap<>();

                // 1. Core Fix: Disable the "Save Address" icon and popups completely
                prefs.put("autofill.profile_enabled", false);

            //    // 2. Additional Fixes: Disable related autofill and card prompts
            //    prefs.put("autofill.credit_card_enabled", false);
            //    prefs.put("credentials_enable_service", false);
            //    prefs.put("profile.password_manager_enabled", false);

            //    // 3. Keep your standard push notification blocking active
            //    prefs.put("profile.default_content_setting_values.notifications", 2);

                // Apply preferences via the mutable HashMap
                options.setExperimentalOption("prefs", prefs);
                options.addArguments("--disable-notifications");
            }

            return options;
        }
    },
    EDGE{
        public EdgeOptions createOptions(){
            // add your options parsing and validations here
            EdgeOptions options = new EdgeOptions();
            if (isHeadlessEnabled()){
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            loadExtensions(options, ".crx");

            if (isNotificationsBlocked()){
                Map<String, Object> prefs = new HashMap<>();
                // 1. Core Fix: Disable the "Save Address" icon and popups completely
                prefs.put("autofill.profile_enabled", false);

                // 2. Additional Fixes: Disable related autofill and card prompts
//                prefs.put("autofill.credit_card_enabled", false);
//                prefs.put("credentials_enable_service", false);
//                prefs.put("profile.password_manager_enabled", false);

                // 3. Keep your standard push notification blocking active
//                prefs.put("profile.default_content_setting_values.notifications", 2);

                // Apply preferences via the mutable HashMap
//                options.setExperimentalOption("prefs", prefs);
//                options.addArguments("--disable-notifications");
                options.setExperimentalOption("prefs", prefs);
            }

            return options;
        }
    },
    FIREFOX{
        public FirefoxOptions createOptions(){
            // add your options parsing and validations here
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadlessEnabled()){
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            loadExtensions(options, ".xpi");

            if (isNotificationsBlocked()){
                // 1. Block Notifications
                options.addPreference("signon.rememberSignons", false);
                options.addPreference("browser.formfill.enable", false);
            }
            return options;
        }
    };

    private static void loadExtensions(org.openqa.selenium.remote.AbstractDriverOptions<?> options, String extensionSuffix) {
        java.util.List<java.io.File> toLoad = new java.util.ArrayList<>();

        // Scan the extensions/ directory
        java.io.File extensionsDir = new java.io.File("extensions");
        if (extensionsDir.exists() && extensionsDir.isDirectory()) {
            java.io.File[] files = extensionsDir.listFiles((dir, name) -> name.endsWith(extensionSuffix));
            if (files != null) {
                toLoad.addAll(java.util.Arrays.asList(files));
            }
        }

        // Fallback: root-level ublock.crx / ublock.xpi
        java.io.File fallback = new java.io.File("ublock" + extensionSuffix);
        if (fallback.exists()) {
            toLoad.add(fallback);
        }

        if (toLoad.isEmpty()) return;

        if (options instanceof ChromeOptions) {
            ((ChromeOptions) options).addExtensions(toLoad.toArray(new java.io.File[0]));
        } else if (options instanceof EdgeOptions) {
            ((EdgeOptions) options).addExtensions(toLoad.toArray(new java.io.File[0]));
        } else if (options instanceof FirefoxOptions) {
            // FirefoxOptions does not have addExtensions(File) — use FirefoxProfile instead
            org.openqa.selenium.firefox.FirefoxProfile profile = new org.openqa.selenium.firefox.FirefoxProfile();
            for (java.io.File xpi : toLoad) {
                profile.addExtension(xpi);
            }
            ((FirefoxOptions) options).setProfile(profile);
        }
    }

    protected static boolean isHeadlessEnabled() {
        return DriverConfiguration.headless();
    }

    protected static boolean isNotificationsBlocked() {
        return DriverConfiguration.blockNotifications();
    }
    abstract public AbstractDriverOptions<?> createOptions();
}
