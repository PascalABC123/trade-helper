package ru.steamutility.tradehelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import ru.steamutility.tradehelper.common.Config;
import ru.steamutility.tradehelper.controller.MessageBox;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class TradeHelperApp extends Application {

    private static TradeHelperApp singleton;

    private final ObservableList<Runnable> startupTasks = FXCollections.observableArrayList();
    private final BooleanBinding startupTasksFinished = Bindings.isEmpty(startupTasks);

    private SceneManager defaultSceneManager;

    public SceneManager getDefaultSceneManager() {
        return defaultSceneManager;
    }

    public static TradeHelperApp getSingleton() {
        return singleton;
    }

    public TradeHelperApp() {
        assert singleton == null;
        singleton = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        defaultSceneManager = new SceneManager(stage, 800, 400);
        handleLaunch();
    }

    public void handleLaunch() {
        var latch = new CountDownLatch(1);
        startupTasksFinished.addListener((observableValue, aBoolean, isFinished) -> {
            if (isFinished) {
                latch.countDown();
                status = ApplicationStatus.IS_RUNNING;
            }
        });

        startInBackground("Trade Helper Launch", this::backgroundStart);
    }


    private void startInBackground(String taskName, Runnable task) {
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
        if(Config.isUnset()) {
            defaultSceneManager.invoke(SceneManager.WINDOW.SETUP_MENU);
        }
        if(!CSGOMarketApiClient.isKeyValid(Config.getProperty("marketApiKey"))) {
            defaultSceneManager.invoke(SceneManager.WINDOW.SETUP_MENU);
            defaultSceneManager.invoke(SceneManager.WINDOW.SETUP_MENU);
            MessageBox.alert("Wrong market api key");
        }
    }

    private ApplicationStatus status = ApplicationStatus.IS_LAUNCHING;

    public ApplicationStatus getStatus() {
        return status;
    }

    public enum ApplicationStatus {
        IS_LAUNCHING,
        IS_RUNNING,
        IS_INTERRUPTED,
        IS_STOPPED,
        IS_EXITING;
    }
}