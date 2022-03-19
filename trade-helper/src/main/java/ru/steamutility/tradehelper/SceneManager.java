package ru.steamutility.tradehelper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public record SceneManager(Stage stage) {

    public void invokeLaunchWindow() throws IOException {
        var fxmlLoader = new FXMLLoader(TradeHelperApp.class.getResource("LoadingWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Loading...");
        stage.setScene(scene);
        stage.show();
    }

    public void invokeHomeWindow() throws IOException {
        var fxmlLoader = new FXMLLoader(TradeHelperApp.class.getResource("HomeWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle(AppPlatform.APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public void hide() {
        stage.hide();
    }
}
