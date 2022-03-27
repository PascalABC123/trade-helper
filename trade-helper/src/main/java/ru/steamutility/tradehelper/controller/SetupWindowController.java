package ru.steamutility.tradehelper.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.economy.Economy;
import ru.steamutility.tradehelper.SceneManager;
import ru.steamutility.tradehelper.TradeHelperApp;
import ru.steamutility.tradehelper.common.Config;

import java.net.URL;
import java.util.ResourceBundle;

public class SetupWindowController implements Initializable {

    @FXML
    private PasswordField marketApiKeyField;

    @FXML
    private PasswordField apisApiKeyField;

    @FXML
    private ChoiceBox<Economy.Currency> currencyChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String marketApiKey = Config.getProperty("marketApiKey");
        final String apisApiKey = Config.getProperty("apisApiKey");
        int currency = Config.getPropertyInt("currency");
        if (currency == -1) currency = 0;

        marketApiKeyField.setText(marketApiKey);
        apisApiKeyField.setText(apisApiKey);
        currencyChoice.setValue(Economy.Currency.values()[currency]);

        for (Economy.Currency c : Economy.Currency.values()) {
            currencyChoice.getItems().add(c);
        }
    }

    @FXML
    private void parseData() {
        boolean ok = true;
        final String marketKey = marketApiKeyField.getText();
        final String apisKey = apisApiKeyField.getText();

        if (AppPlatform.isMarketKeyValid(marketKey)) {
            Config.setProperty("marketApiKey", marketKey);
        } else {
            MessageBox.alert("Wrong market api key!");
            ok = false;
        }

        if (AppPlatform.isApisKeyValid(apisKey)) {
            Config.setProperty("apisApiKey", apisKey);
        } else {
            MessageBox.alert("Wrong apis api key!");
            ok = false;
        }

        Config.setProperty("currency", String.valueOf(currencyChoice.getValue().ordinal()));

        if (ok) {
            TradeHelperApp.getDefaultSceneManager().invoke(SceneManager.Window.HOME_MENU);
        }
    }
}
