package ru.liga.impl.output;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import ru.liga.api.AlgorithmOutput;
import ru.liga.impl.RatesResult;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GraphOutput implements AlgorithmOutput<byte[]> {

    private XYChart chart;

    public GraphOutput() {
        chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Predictions")
                .xAxisTitle("Dates")
                .yAxisTitle("Rates")
                .theme(Styler.ChartTheme.Matlab)
                .build();
        chart.getStyler().setChartBackgroundColor(Color.LIGHT_GRAY);
    }

    public byte[] output(List<RatesResult> results) {

        results.forEach(r -> {
            XYSeries series = chart.addSeries(r.getName(),
                    r.getDates(),
                    r.getRates());
            series.setSmooth(true);
//            series.setMarkerColor(Color.CYAN);
//            series.setLineColor(Color.GREEN);
        });

        try {
            byte[] img = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.PNG);
            return img;
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
