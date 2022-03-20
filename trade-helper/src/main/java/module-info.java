module ru.steamutility.tradehelper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens ru.steamutility.tradehelper to javafx.fxml;
    exports ru.steamutility.tradehelper;
    exports ru.steamutility.tradehelper.controller;
    opens ru.steamutility.tradehelper.controller to javafx.fxml;
}