package ru.steamutility.tradehelper.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.steamutility.tradehelper.TradeHelperApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageBox implements Initializable {
    private static String message;
    private static Stage stage;

    private static final ObservableList<String> messagesList = FXCollections.observableArrayList();

    static {
        messagesList.addListener((ListChangeListener<String>) change -> {
            if(change.wasRemoved()) alertNow(messagesList.get(0));
        });
    }

    public static void alert(String message) {
        if(messagesList.isEmpty()) alertNow(message);
        messagesList.add(message);
    }

    private static void alertNow(String message) {
        Platform.runLater(() -> {
            MessageBox.message = message;
            stage = new Stage();
            try {
                var fxmlLoader = new FXMLLoader(TradeHelperApp.class.getResource("MessageBox.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 350, 200);
                stage.setTitle("Warning");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ignored) {
            }
        });
    }

    @FXML
    Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText(message);
    }

    @FXML
    private void close() {
        messagesList.remove(0);
        stage.hide();
    }
}
