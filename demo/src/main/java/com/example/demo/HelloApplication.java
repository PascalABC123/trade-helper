package com.example.demo;

import static de.gsi.dataset.spi.AbstractHistogram.HistogramOuterBounds.BINS_ALIGNED_WITH_BOUNDARY;

import de.gsi.chart.XYChart;
import de.gsi.chart.plugins.EditAxis;
import de.gsi.chart.plugins.Zoomer;
import de.gsi.chart.renderer.LineStyle;
import de.gsi.chart.renderer.spi.ErrorDataSetRenderer;
import de.gsi.dataset.spi.Histogram;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private Histogram dataSet1 = new Histogram("myHistogram1", 4, 0.0, 4.0, BINS_ALIGNED_WITH_BOUNDARY);

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root, 600, 600);
        final XYChart chart = new XYChart();
        final ErrorDataSetRenderer renderer1 = new ErrorDataSetRenderer();
        renderer1.setPolyLineStyle(LineStyle.HISTOGRAM_FILLED);
        chart.getRenderers().add(renderer1);
        renderer1.getDatasets().addAll(dataSet1);

        chart.getPlugins().add(new EditAxis());
        final Zoomer zoomer = new Zoomer();
        zoomer.setSliderVisible(false);
        chart.getPlugins().add(zoomer);

        root.getChildren().add(chart);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}