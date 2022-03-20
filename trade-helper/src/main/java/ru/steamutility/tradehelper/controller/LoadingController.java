package ru.steamutility.tradehelper.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.steamutility.tradehelper.TradeHelperApp;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {
    @FXML
    private ImageView loadingWheel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var i = new Image(TradeHelperApp.class.getResource("Loading.gif").toString());
        loadingWheel.setImage(i);
    }
}
