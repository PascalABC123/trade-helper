package ru.steamutility.tradehelper.controller;

import de.gsi.chart.plugins.*;
import de.gsi.chart.renderer.LineStyle;
import de.gsi.chart.renderer.spi.ErrorDataSetRenderer;
import de.gsi.chart.renderer.spi.ReducingLineRenderer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import de.gsi.chart.XYChart;
import de.gsi.chart.axes.AxisLabelOverlapPolicy;
import de.gsi.chart.axes.spi.DefaultNumericAxis;
import de.gsi.dataset.spi.DefaultErrorDataSet;
import de.gsi.dataset.utils.ProcessingProfiler;
import javafx.util.StringConverter;
import ru.steamutility.tradehelper.common.USDRateHistory;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class USDChartController implements Initializable {
    private static final StringConverter<Number> dateFormatter;

    static {
        dateFormatter = new StringConverter<>() {
            @Override
            public String toString(Number number) {
                var sdf = new SimpleDateFormat("dd.MM.yy");
                long time = number.longValue() * 1000;
                return sdf.format(new Date(time));
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        };
    }

    private static final StringConverter<Number> doubleFormatter;

    static {
        doubleFormatter = new StringConverter<>() {
            @Override
            public String toString(Number number) {
                var df = new DecimalFormat("#0.00");
                return df.format((Double) number);
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        };
    }

    @HeightAutoResizeable(height = 0.9)
    @WidthAutoResizeable(width = 0.97)
    public XYChart chart;

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProcessingProfiler.setVerboseOutputState(true);
        ProcessingProfiler.setLoggerOutputState(true);
        ProcessingProfiler.setDebugState(false);

        final var xAxis1 = new DefaultNumericAxis("time", "date");
        xAxis1.setOverlapPolicy(AxisLabelOverlapPolicy.SKIP_ALT);
        final var yAxis1 = new DefaultNumericAxis("rate", "rub.");

        final var dpt = new DataPointTooltip();
        dpt.setXValueFormatter(dateFormatter);
        dpt.setYValueFormatter(doubleFormatter);
        dpt.setPickingDistance(5);

        final var ci = new CrosshairIndicator();
        ci.setXValueFormatter(dateFormatter);
        ci.setYValueFormatter(doubleFormatter);

        // TODO: small zoom out to see all the chart
        chart = new XYChart(xAxis1, yAxis1);
        chart.legendVisibleProperty().set(true);
        chart.getPlugins().add(new YRangeIndicator(yAxis1, -100000, 0));
        chart.getPlugins().add(new Zoomer());
        chart.getPlugins().add(ci);
        chart.getPlugins().add(dpt);
        chart.setAnimated(false);

        xAxis1.setAutoRangeRounding(false);
        xAxis1.setTimeAxis(true);
        yAxis1.setAutoRangeRounding(true);

        final var dataSet = new DefaultErrorDataSet("Rate");

        final TreeMap<Date, Double> data = USDRateHistory.getSortedRecords();
        for (Date d : data.keySet()) {
            double t = d.getTime() / 1000.0;
            dataSet.add(t, data.get(d), 0.1, 0.0);
        }

        final var renderer1 = new ErrorDataSetRenderer();
        renderer1.setPolyLineStyle(LineStyle.NORMAL);
        renderer1.setShowInLegend(false);
        chart.getRenderers().add(renderer1);
        renderer1.getDatasets().addAll(dataSet);

        long startTime = ProcessingProfiler.getTimeStamp();
        ProcessingProfiler.getTimeDiff(startTime, "adding data to chart");

        startTime = ProcessingProfiler.getTimeStamp();
        root.getChildren().add(chart);
        ProcessingProfiler.getTimeDiff(startTime, "adding chart into AnchorPane");

        ProcessingProfiler.getTimeDiff(startTime, "for showing");
    }
}
