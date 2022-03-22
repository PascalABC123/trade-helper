package ru.steamutility.tradehelper.controller;

import de.gsi.chart.XYChart;
import de.gsi.chart.axes.AxisLabelOverlapPolicy;
import de.gsi.chart.axes.spi.DefaultNumericAxis;
import de.gsi.chart.plugins.Zoomer;
import de.gsi.dataset.spi.DefaultErrorDataSet;
import de.gsi.dataset.utils.ProcessingProfiler;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import ru.steamutility.tradehelper.TradeHelperApp;
import ru.steamutility.tradehelper.annotations.AutoResizeable;

public class USDRateWindow {
    @AutoResizeable
    public static final AnchorPane root = new AnchorPane();

    @AutoResizeable
    public static XYChart chart;

    private static final int N_SAMPLES = 10_000; // default: 10000

    private static void generateData(final DefaultErrorDataSet dataSet) {
        final long startTime = ProcessingProfiler.getTimeStamp();

        dataSet.autoNotification().set(false);
        dataSet.clearData();
        final double now = System.currentTimeMillis() / 1000.0 + 1; // N.B. '+1'
        // to check
        // for
        // resolution
        for (int n = 0; n < USDRateWindow.N_SAMPLES; n++) {
            double t = now + n * 10;
            t *= +1;
            final double y = 100 * Math.cos(Math.PI * t * 0.0005) + 0 * 0.001 * (t - now) + 0 * 1e4;
            final double ex = 0.1;
            final double ey = 10;
            dataSet.add(t, y, ex, ey);
        }
        dataSet.autoNotification().set(true);

        Platform.runLater(() -> dataSet.fireInvalidated(null));
        ProcessingProfiler.getTimeDiff(startTime, "adding data into DataSet");
    }

    public static void initChart() {
    }

    public static void requestOpen() {
        final DefaultNumericAxis xAxis = new DefaultNumericAxis("time", "date");
        xAxis.setOverlapPolicy(AxisLabelOverlapPolicy.SKIP_ALT);
        final DefaultNumericAxis yAxis = new DefaultNumericAxis("rate", "rub.");

        chart = new XYChart(xAxis, yAxis);
        chart.legendVisibleProperty().set(true);
        chart.getPlugins().add(new Zoomer());
        chart.setAnimated(false);

        xAxis.setAutoRangeRounding(false);
        xAxis.setTimeAxis(true);
        yAxis.setAutoRangeRounding(true);

        final DefaultErrorDataSet dataSet = new DefaultErrorDataSet("USDRate");

        generateData(dataSet);

        long startTime = ProcessingProfiler.getTimeStamp();
        chart.getDatasets().add(dataSet);
        ProcessingProfiler.getTimeDiff(startTime, "adding data to chart");

        startTime = ProcessingProfiler.getTimeStamp();
        root.getChildren().add(chart);
        ProcessingProfiler.getTimeDiff(startTime, "adding chart into StackPane");

        startTime = ProcessingProfiler.getTimeStamp();
        ProcessingProfiler.getTimeDiff(startTime, "for showing");

        TradeHelperApp.getDefaultSceneManager().openPage(root, USDRateWindow.class);
    }
}
