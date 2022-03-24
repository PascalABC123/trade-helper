package ru.steamutility.tradehelper.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import ru.steamutility.tradehelper.CSGOMarketApiClient;
import ru.steamutility.tradehelper.Economy;
import ru.steamutility.tradehelper.SceneManager;
import ru.steamutility.tradehelper.TradeHelperApp;
import ru.steamutility.tradehelper.common.Config;

import java.net.URL;
import java.util.ResourceBundle;

public class SetupWindowController implements Initializable {

    @FXML
    private PasswordField marketApiKeyField;

    @FXML
    private ChoiceBox<Economy.Currency> currencyChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String marketApiKey = Config.getProperty("marketApiKey");
        int currency = Config.getPropertyInt("currency");
        if(currency == -1) currency = 0;

        marketApiKeyField.setText(marketApiKey);
        currencyChoice.setValue(Economy.Currency.values()[currency]);
        for(var c : Economy.Currency.values()) {
            currencyChoice.getItems().add(c);
        }
    }

    @FXML
    private void parseData() {
        final String key = marketApiKeyField.getText();
        if(CSGOMarketApiClient.isKeyValid(key)) {
            TradeHelperApp.getDefaultSceneManager().invoke(SceneManager.Window.HOME_MENU);
            Config.setProperty("marketApiKey", key);
            Config.setProperty("currency", String.valueOf(currencyChoice.getValue().ordinal()));
        }
        else {
            MessageBox.alert("Wrong market api key!");
        }
    }
}
