package ru.steamutility.tradehelper.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.steamutility.tradehelper.Refreshable;
import ru.steamutility.tradehelper.economy.Economy;
import ru.steamutility.tradehelper.SceneManager;
import ru.steamutility.tradehelper.TradeHelperApp;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeWindowController implements Initializable, Refreshable {

    @WidthAutoResizeable
    @FXML
    public AnchorPane anchorPane;

    @WidthAutoResizeable
    @FXML
    public AnchorPane topPane;

    @WidthAutoResizeable
    @FXML
    public VBox vBox;

    @WidthAutoResizeable
    @FXML
    public ScrollPane scrollPane;

    @FXML
    private Label usdRateLabel;

    @FXML
    private Label usdRateText;

    @FXML
    private void setPaneUnfocused() {
        usdRateLabel.requestFocus();
    }

    @FXML
    private void onUsdRatePaneHover() {
        usdRateText.setTextFill(Color.rgb(55, 55, 255));
    }

    @FXML
    private void onUsdRatePaneUnhover() {
        usdRateText.setTextFill(Color.rgb(0, 0, 0));
    }

    @FXML
    private void requestSettings() {
        TradeHelperApp.getDefaultSceneManager().invoke(SceneManager.Window.SETUP_MENU);
    }

    @FXML
    private void requestUSDGraph() {
        SceneManager sm = new SceneManager(new Stage(), 800, 400);
        sm.invoke(SceneManager.Window.USD_CHART);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usdRateLabel.setText(Economy.getUSDRateString());
        if (Economy.getUSDDelta() > 0)
            usdRateLabel.setTextFill(Color.rgb(198, 84, 80));
        else
            usdRateLabel.setTextFill(Color.rgb(73, 155, 84));
    }

    @Override
    public void refresh() {
        initialize(null, null);
    }
}
