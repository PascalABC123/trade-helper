package ru.steamutility.tradehelper;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import ru.steamutility.tradehelper.common.Config;
import ru.steamutility.tradehelper.common.USDRateHistory;
import ru.steamutility.tradehelper.controller.MessageBox;

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
        var latch = new CountDownLatch(1);
        startupTasksFinished.addListener((observableValue, aBoolean, isFinished) -> {
            if (isFinished) {
                latch.countDown();
            }
        });

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
    }
}