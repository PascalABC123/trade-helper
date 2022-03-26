package ru.steamutility.tradehelper;

import ru.steamutility.tradehelper.common.Config;
import ru.steamutility.tradehelper.controller.MessageBox;

import java.io.File;

public class AppPlatform {
    public static final String APP_NAME = "Trade-Helper";

    private static final String userDataFolder = System.getenv("APPDATA") + "\\";
    private static final String applicationDataFolder = userDataFolder + APP_NAME + "\\";
    private static final String configPath = applicationDataFolder + "config";

    public static synchronized String getApplicationDataFolder() {
        assert new File(applicationDataFolder).mkdirs();
        return applicationDataFolder;
    }

    public static synchronized void requestSettingsMenu(String warning) {
        MessageBox.alert(warning);
        requestSettingsMenu();
    }

    public static synchronized void requestSettingsMenu() {
        TradeHelperApp.getDefaultSceneManager().invoke(SceneManager.Window.SETUP_MENU);
    }

    public static String getConfigPath() {
        return configPath;
    }
}

