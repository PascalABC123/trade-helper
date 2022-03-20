package ru.steamutility.tradehelper;

import java.io.File;
import java.io.IOException;

public class AppPlatform {
    public static final String APP_NAME = "Trade-Helper";

    private static final String applicationDataFolder = System.getenv("APPDATA") + "\\" + APP_NAME;
    private static final String configPath = applicationDataFolder + "\\config";

    public static synchronized String getApplicationDataFolder() {
        new File(applicationDataFolder).mkdirs();
        return applicationDataFolder;
    }

    public static String getConfigPath() {
        return configPath;
    }
}

