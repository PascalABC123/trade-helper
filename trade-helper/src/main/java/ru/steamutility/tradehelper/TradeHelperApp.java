package ru.steamutility.tradehelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class TradeHelperApp extends Application {

    private static TradeHelperApp singleton;

    private final ObservableList<Runnable> startupTasks = FXCollections.observableArrayList();
    private final BooleanBinding startupTasksFinished = Bindings.isEmpty(startupTasks);

    public TradeHelperApp getSingleton() {
        return singleton;
    }

    public TradeHelperApp() {
        assert singleton == null;
        singleton = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        handleLaunch();
        makeWindow(stage);
    }

    public void handleLaunch() {
        var latch = new CountDownLatch(1);

        startupTasksFinished.addListener((observableValue, aBoolean, isFinished) -> {
            if (isFinished) {
                latch.countDown();
            }
        });

        startInBackground("Trade Helper Launch", () -> backgroundStart());
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

    private void makeWindow(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(TradeHelperApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void backgroundStart() {
        assert Platform.isFxApplicationThread() == false; //Warning
    }
}