package ru.steamutility.tradehelper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private final Stage stage;
    private Scene scene;
    private FXMLLoader loader;

    private boolean pageOpened = false;
    private Class currentPageClass;

    public boolean isPageOpened() {
        return pageOpened;
    }

    public Class getCurrentPageClass() {
        return currentPageClass;
    }

    public SceneManager(Stage stage, double width, double height) {
        this.stage = stage;
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
    }

    private ObservableList<Window> stack = FXCollections.observableArrayList();

    public enum Window {
        HOME_MENU,
        SETUP_MENU
    }

    public void invoke(Window window) {
        pageOpened = false;
        stack.add(window);
        Platform.runLater(() -> {
            try {
                loader = new FXMLLoader();
                switch (window) {
                    case HOME_MENU -> loader.setLocation(TradeHelperApp.class.getResource("HomeWindow.fxml"));
                    case SETUP_MENU -> loader.setLocation(TradeHelperApp.class.getResource("SetupWindow.fxml"));
                }
                scene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());
                stage.setTitle(AppPlatform.APP_NAME);
                stage.setScene(scene);
                stage.show();
                stage.setWidth(stage.getWidth() - 1); // Application resizing prevents bugs (elements shifting down)
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void openPage(Parent root, Class controller) {
        currentPageClass = controller;
        pageOpened = true;
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setWidth(stage.getWidth() - 1);
        stage.setHeight(stage.getHeight() - 1);
    }

    public void hide() {
        Platform.runLater(stage::hide);
    }

    private static final Button goBack;

    static {
        goBack = new Button();
        Image img = new Image(Objects.requireNonNull(TradeHelperApp.class.getResourceAsStream("go_back.png")));
        goBack.setGraphic(new ImageView(img));
        goBack.setMaxWidth(5);
        goBack.setMaxHeight(5);
        goBack.setOnAction(actionEvent -> {
            var sm = TradeHelperApp.getDefaultSceneManager();
            if (sm.pageOpened && sm.stack.size() > 0) {
                sm.invoke(sm.stack.get(sm.stack.size() - 1));
            } else if (sm.stack.size() > 1) {
                sm.invoke(sm.stack.get(sm.stack.size() - 2));
            } else {
                sm.invoke(Window.HOME_MENU);
            }
        });
    }

    public static Button getGoBackButton() {
        return goBack;
    }

    public Stage getStage() {
        return stage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
