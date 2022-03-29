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
import ru.steamutility.tradehelper.economy.*;
import ru.steamutility.tradehelper.SceneManager;
import ru.steamutility.tradehelper.TradeHelperApp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class HomeWindowController implements Initializable, Refreshable {

    private static volatile HomeWindowController singleton;

    public HomeWindowController() {
        singleton = this;
    }

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

    @WidthAutoResizeable
    @FXML
    public AnchorPane itemsPane;

    @FXML
    private Label usdRateLabel;

    @FXML
    private Label usdRateText;

    @FXML
    private Label bestToDeposit;

    @FXML
    private Label depositMore;

    @FXML
    private Label bestToDepositProfit;

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
        ArrayList<Predicate<Item>> filters = new ArrayList<>() {{
            add(Filters.getFilter(FilterType.STEAM_MIN_PRICE, 0.3));
            add(Filters.getFilter(FilterType.STEAM_MIN_VOLUME, 100.00));
        }};
        var set = Items.getFilteredItems(filters);
        Iterator<Item> i = set.iterator();
        File file = new File("C:\\Users\\Ульяна\\AppData\\Roaming\\Trade-Helper\\item_names");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            while(i.hasNext()) {
                bw.write(i.next().getHashName() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        bestToDeposit.setText("...");
        bestToDepositProfit.setText("...");
    }

    public static void initializeItems(Item deposit, Item withdraw) {
        singleton.bestToDeposit.setText(deposit.getHashName());
        singleton.bestToDepositProfit.setText(String.valueOf(deposit.getDepositProfitPercent()));
    }

    @Override
    public void refresh() {
        initialize(null, null);
    }
}
