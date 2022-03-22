module ru.steamutility.tradehelper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.testng;
    requires org.json;
    requires de.gsi.chartfx.chart;
    requires de.gsi.chartfx.dataset;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;

    opens ru.steamutility.tradehelper to javafx.fxml;
    exports ru.steamutility.tradehelper;
    exports ru.steamutility.tradehelper.controller;
    opens ru.steamutility.tradehelper.controller to javafx.fxml;
}