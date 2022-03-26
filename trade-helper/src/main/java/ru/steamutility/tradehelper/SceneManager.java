package ru.steamutility.tradehelper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private final Stage stage;

    private Scene scene;
    private FXMLLoader loader;
    private boolean pageOpened = false;
    private Class<Object> currentPageClass;

    public boolean isPageOpened() {
        return pageOpened;
    }

    public Class<Object> getCurrentPageClass() {
        return currentPageClass;
    }

    private final double defaultWidth, defaultHeight;

    public SceneManager(Stage stage, double width, double height) {
        assert stage != null;

        defaultWidth = width;
        defaultHeight = height;

        this.stage = stage;
        stage.setMinWidth(width);
        stage.setWidth(width);
        stage.setMinHeight(height);
        stage.setHeight(height);

        Annotations.initAll(this);
    }

    private final ObservableList<Window> stack = FXCollections.observableArrayList();

    public enum Window {
        HOME_MENU,
        SETUP_MENU,
        USD_CHART
    }

    public void invoke(Window window) {
        pageOpened = false;
        fixSize();
        stack.add(window);

        Platform.runLater(() -> {
            try {
                loader = new FXMLLoader();

                switch (window) {
                    case HOME_MENU ->
                            loader.setLocation(TradeHelperApp.class.getResource("HomeWindow.fxml"));
                    case SETUP_MENU ->
                            loader.setLocation(TradeHelperApp.class.getResource("SetupWindow.fxml"));
                    case USD_CHART ->
                            loader.setLocation(TradeHelperApp.class.getResource("USDChartWindow.fxml"));
                }

                scene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());
                stage.setTitle(AppPlatform.APP_NAME);
                stage.setScene(scene);
                stage.show();

                //TODO Application resizing prevents bugs (elements shifting down)
                stage.setWidth(stage.getWidth() - 1);
                stage.setHeight(stage.getHeight() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void openPage(Parent root, Class<Object> controller) {
        assert root != null && controller != null;
        currentPageClass = controller;

        pageOpened = true;

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

        stage.setScene(scene);
    }

    private void fixSize() {
        if(stage.getWidth() < defaultWidth - 1)
            stage.setWidth(defaultWidth);
        if(stage.getHeight() < defaultHeight - 1)
            stage.setHeight(defaultHeight);
    }

    public void hide() {
        Platform.runLater(stage::hide);
    }

    private static final Label goBack;

    static {
        goBack = new Label();

        goBack.setText("\uD83E\uDC14");
        goBack.setFont(Font.font(25));
        goBack.setTextFill(Color.rgb(20,20,20));
        goBack.setCursor(Cursor.HAND);

        goBack.onMouseEnteredProperty().set(actionEvent -> {
            goBack.setTextFill(Color.rgb(55, 55, 255));
        });

        goBack.onMouseExitedProperty().set(actionEvent -> {
            goBack.setTextFill(Color.rgb(20,20,20));
        });

        goBack.onMouseClickedProperty().set(actionEvent -> {
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

    public static Label getGoBackButton() {
        return goBack;
    }

    public Stage getStage() {
        return stage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
