module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires chartfx.chart;
    requires commons.math3;
    requires org.slf4j;
    requires chartfx.dataset;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}