package ru.steamutility.tradehelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class TradeHelperApp extends Application {

    private static TradeHelperApp singleton;

    private final ObservableList<Runnable> startupTasks = FXCollections.observableArrayList();
    private final BooleanBinding startupTasksFinished = Bindings.isEmpty(startupTasks);

    private SceneManager defaultSceneManager;

    public static TradeHelperApp getSingleton() {
        return singleton;
    }

    public TradeHelperApp() {
        assert singleton == null;
        singleton = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        defaultSceneManager = new SceneManager(stage);
        handleLaunch();
    }

    public void handleLaunch() throws IOException {
        var sceneManager = new SceneManager(new Stage());
        sceneManager.invokeLaunchWindow();

        var latch = new CountDownLatch(1);
        startupTasksFinished.addListener((observableValue, aBoolean, isFinished) -> {
            if (isFinished) {
                latch.countDown();
            }
        });

        startInBackground("Trade Helper Launch", this::backgroundStart);
        sceneManager.hide();
        defaultSceneManager.invokeHomeWindow();
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
        assert !Platform.isFxApplicationThread(); //Warning
    }
}