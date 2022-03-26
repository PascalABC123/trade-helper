package ru.steamutility.tradehelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.steamutility.tradehelper.common.Config;
import ru.steamutility.tradehelper.common.USDRateHistory;
import ru.steamutility.tradehelper.controller.MessageBox;
import ru.steamutility.tradehelper.economy.Items;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class TradeHelperApp extends Application {

    private static TradeHelperApp singleton;

    private final ObservableList<Runnable> startupTasks = FXCollections.observableArrayList();
    private final BooleanBinding startupTasksFinished = Bindings.isEmpty(startupTasks);

    private static SceneManager defaultSceneManager;

    public static SceneManager getDefaultSceneManager() {
        assert defaultSceneManager != null;
        return defaultSceneManager;
    }

    public static TradeHelperApp getSingleton() {
        return singleton;
    }

    public TradeHelperApp() {
        assert singleton == null;
        singleton = this;

        java.beans.Beans.setDesignTime(true);
    }

    @Override
    public void start(Stage stage) {
        defaultSceneManager = new SceneManager(stage, 800, 400);
        handleLaunch();
    }

    public void handleLaunch() {
        Platform.setImplicitExit(false);

        var latch = new CountDownLatch(1);
        startupTasksFinished.addListener((observableValue, aBoolean, isFinished) -> {
            if (isFinished) {
                latch.countDown();
            }
        });

        startInBackground("Items Init", Items::initMarket);
        startInBackground("Items Init", Items::initSteam);
        startInBackground("Trade Helper Launch", this::backgroundStart);
    }


    private void startInBackground(String taskName, Runnable task) {
        assert task != null;
        var t = new Thread(() -> {
            task.run();

            startupTasks.remove(task);
        }, taskName + " Thread");

        t.setDaemon(true);
        t.start();

        startupTasks.add(task);
    }

    public static void main(String[] args) {
        launch();
    }

    private void backgroundStart() {
        USDRateHistory.requestRecord();

        if(Config.isUnset()) {
            defaultSceneManager.invoke(SceneManager.Window.SETUP_MENU);
        }
        else if(!CSGOMarketApiClient.isConfigKeyValid()) {
            defaultSceneManager.invoke(SceneManager.Window.SETUP_MENU);
            MessageBox.alert("Wrong market api key");
        }
        else {
            defaultSceneManager.invoke(SceneManager.Window.HOME_MENU);
        }

        defaultSceneManager.getStage().setOnCloseRequest(windowEvent -> Platform.runLater(TradeHelperApp::addAppToTray));
    }

    private static void addAppToTray() {
        assert defaultSceneManager.getStage() != null;
        defaultSceneManager.getStage().hide();

        assert java.awt.Toolkit.getDefaultToolkit() != null;

        if (!java.awt.SystemTray.isSupported()) {
            System.out.println("No system tray support, application exiting.");
            Platform.exit();
        }

        final java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();

        try {
            URL url = TradeHelperApp.class.getResource("64x64icon.png");
            java.awt.Image image = ImageIO.read(Objects.requireNonNull(url));
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            tray.add(trayIcon);
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}