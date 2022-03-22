package ru.steamutility.tradehelper.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ru.steamutility.tradehelper.Economy;
import ru.steamutility.tradehelper.SceneManager;
import ru.steamutility.tradehelper.TradeHelperApp;
import ru.steamutility.tradehelper.annotations.AutoResizeable;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeWindowController implements Initializable {

    @AutoResizeable
    @FXML
    public AnchorPane anchorPane;

    @AutoResizeable
    @FXML
    public AnchorPane topPane;

    @AutoResizeable
    @FXML
    public VBox vBox;

    @AutoResizeable
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
        USDRateWindow.requestOpen();
    }

    private void setElementsWidth(double width) {
        anchorPane.setPrefWidth(width);
        vBox.setPrefWidth(width);
        topPane.setPrefWidth(width);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usdRateLabel.setText(Economy.getUSDRateString());
        if(Economy.getUSDDelta() > 0)
            usdRateLabel.setTextFill(Color.rgb(198, 84, 80));
        else
            usdRateLabel.setTextFill(Color.rgb(73, 155, 84));
        //TradeHelperApp.getDefaultSceneManager().getStage().widthProperty().addListener(
                //(observableValue, number, newSceneWidth) -> setElementsWidth((Double) newSceneWidth));
    }
}
