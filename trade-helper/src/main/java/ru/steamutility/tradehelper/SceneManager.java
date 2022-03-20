package ru.steamutility.tradehelper;

import javafx.application.Platform;
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
        stage.setMinWidth(width);
        stage.setMinHeight(height);
    }

    static ObservableList<Scene> stack;

    public enum WINDOW {
        HOME_MENU,
        SETUP_MENU
    }

    public void invoke(WINDOW window) {
        Platform.runLater(() -> {
            try {
                var fxmlLoader = new FXMLLoader();
                switch (window) {
                    case HOME_MENU -> fxmlLoader.setLocation(TradeHelperApp.class.getResource("HomeWindow.fxml"));
                    case SETUP_MENU -> fxmlLoader.setLocation(TradeHelperApp.class.getResource("SetupWindow.fxml"));
                }
                Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
                stage.setTitle(AppPlatform.APP_NAME);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void hide() {
        Platform.runLater(() -> stage.hide());
    }
}
