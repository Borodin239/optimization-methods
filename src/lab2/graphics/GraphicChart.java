package lab2.graphics;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import lab2.optimization.Iteration;
import lab2.optimization.QuadraticForm;

import java.util.ArrayList;
import java.util.List;

public class GraphicChart {

    private final LineChart<Number, Number> lineChart;
    private final QuadraticForm form;

    private XYChart.Series<Number, Number> dotsGraphicSeries;
    private List<XYChart.Series<Number, Number>> series;

    private List<XYChart.Data<Number, Number>> graphicData;

    private List<Iteration> iterations;


    public GraphicChart(LineChart<Number, Number> lineChart, QuadraticForm form) {
        this.lineChart = lineChart;
        this.form = form;

        lineChart.setAnimated(false);
        lineChart.setLegendVisible(false);
        lineChart.getXAxis().setAutoRanging(false);
        lineChart.getYAxis().setAutoRanging(false);
        lineChart.setCreateSymbols(false);

        setSeries();
    }

    public void setGraphics(List<Iteration> iterations, double x, double y) {
        this.iterations = iterations;
        buildGraphicData();
        resizeChart();
        update(0);
    }

    private void resizeChart() {
        double xMax = Double.MIN_VALUE;
        double yMax = Double.MIN_VALUE;
        double xMin = Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;

        for (XYChart.Data<Number, Number> dot : graphicData) {
            xMax = Math.max(xMax, (double) dot.getXValue());
            yMax = Math.max(yMax, (double) dot.getYValue());
            xMin = Math.min(xMin, (double) dot.getXValue());
            yMin = Math.min(yMin, (double) dot.getYValue());
        }

        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        double borderX = (xMax - xMin) / 15;
        double borderY = (yMax - yMin) / 10;

        xAxis.setTickUnit((xMax - xMin) / 8);
        yAxis.setTickUnit((yMax - yMin) / 6);
        xAxis.setLowerBound(xMin - borderX);
        xAxis.setUpperBound(xMax + borderX);

        yAxis.setLowerBound(yMin - borderY);
        yAxis.setUpperBound(yMax + borderY);
    }


    public void update(int iterationNum) {
        series.forEach((a) -> a.getData().clear());
        Iteration iteration = iterations.get(iterationNum);
        drawFormula(iteration.getX().get(0), iteration.getX().get(1));
    }


    private void drawFormula(Number x, Number y) {
        for (XYChart.Data<Number, Number> dot : graphicData) {
            dotsGraphicSeries.getData().add(dot);
            if (dot.getXValue().equals(x) && dot.getYValue().equals(y)) {
                break;
            }
        }
    }
    /*

    private void drawParabola(Iteration it, double l, double r) {
        if (it.getParabola() == null) {
            return;
        }
        for (double i = l; i < r; i += (r - l) / 1000) {
            parabolaSeries.getData().add(new XYChart.Data<>(i, it.getParabola().apply(i)));
        }
    }

     */

    private void setSeries() {

        series = new ArrayList<>();
        dotsGraphicSeries = new XYChart.Series<>();
        series.add(dotsGraphicSeries);
        for (int i = 0; i < 50; i++) {
            series.add(new XYChart.Series<>());
        }

        Platform.runLater(() -> lineChart.getData().addAll(series));
    }


    private void buildGraphicData() {
        graphicData = new ArrayList<>();
        for (Iteration i : iterations) {
            graphicData.add(new XYChart.Data<>(i.getX().get(0), i.getX().get(1)));
        }
    }
}

