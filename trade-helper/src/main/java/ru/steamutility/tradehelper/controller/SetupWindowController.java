package ru.steamutility.tradehelper.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import ru.steamutility.tradehelper.AppPlatform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupWindowController implements Initializable {
    @FXML
    private HBox marketApiBox;

    @FXML
    private Label marketApiLabel;

    @FXML
    private PasswordField marketApiKey;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String marketApiKey;
        int currency;

    }
}
