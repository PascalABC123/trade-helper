package ru.steamutility.tradehelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    Stage stage;

    public SceneManager(Stage stage, double width, double height) {
        this.stage = stage;
        stage.setWidth(width);
        stage.setHeight(height);
    }

    static ObservableList<Scene> stack;

    public void invokeLaunchWindow() throws IOException {
        var fxmlLoader = new FXMLLoader(TradeHelperApp.class.getResource("LoadingWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        stage.setTitle("Loading...");
        stage.setScene(scene);
        stage.show();
    }

    public void invokeHomeWindow() throws IOException {
        var fxmlLoader = new FXMLLoader(TradeHelperApp.class.getResource("HomeWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        stage.setTitle(AppPlatform.APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public void invokeSetupWindow() throws IOException {
        var fxmlLoader = new FXMLLoader(TradeHelperApp.class.getResource("SetupWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        stage.setTitle(AppPlatform.APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public void hide() {
        stage.hide();
    }
}
