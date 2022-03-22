package ru.steamutility.tradehelper.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.steamutility.tradehelper.TradeHelperApp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MessageBox implements Initializable {
    private static String message;
    private static Stage stage;

    private static final ArrayList<String> messages = new ArrayList<>();

    public static void alert(String message) {
        if(messages.isEmpty()) alertNow(message);
        messages.add(message);
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
        stage.setOnCloseRequest(windowEvent -> {
            messages.clear();
        });
    }

    @FXML
    private void close() {
        messages.remove(0);
        stage.hide();
        if(!messages.isEmpty())
            alertNow(messages.get(0));
    }
}
