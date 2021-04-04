package lab1.graphics;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import lab1.tools.Iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class GraphicChart {

    private final LineChart<Number, Number> lineChart;
    private final UnaryOperator<Double> formula;

    private XYChart.Series<Number, Number> outLeftBorderGraphicSeries;
    private XYChart.Series<Number, Number> outRightBorderGraphicSeries;
    private XYChart.Series<Number, Number> inBorderGraphicSeries;
    private XYChart.Series<Number, Number> parabolaSeries;
    private List<XYChart.Series<Number, Number>> series;

    private List<XYChart.Data<Number, Number>> graphicData;

    private List<Iteration> iterations;


    public GraphicChart(LineChart<Number, Number> lineChart, UnaryOperator<Double> formula) {
        this.lineChart = lineChart;
        this.formula = formula;

        lineChart.setAnimated(false);
        lineChart.setLegendVisible(false);
        lineChart.getXAxis().setAutoRanging(false);
        lineChart.getYAxis().setAutoRanging(false);
        lineChart.setCreateSymbols(false);

        setSeries();
    }

    public void setGraphics(List<Iteration> iterations, double l, double r) {
        this.iterations = iterations;
        buildGraphicData(l, r, formula);
        resizeChart();
        update(0);
    }

    private void resizeChart() {
        double xMax = Double.MIN_VALUE;
        double yMax = Double.MIN_VALUE;
        double xMin = Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;

        for (XYChart.Data<Number, Number> dot : graphicData) {
            xMax = Math.max(xMax, (double)dot.getXValue());
            yMax = Math.max(yMax, (double)dot.getYValue());
            xMin = Math.min(xMin, (double)dot.getXValue());
            yMin = Math.min(yMin, (double)dot.getYValue());
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
        drawFormula(iteration.getL(), iteration.getR());
        drawParabola(iteration, iterations.get(0).getL(), iterations.get(0).getR());
    }

    private void drawFormula(double l, double r) {
        for (XYChart.Data<Number, Number> dot : graphicData) {
            if ((double)dot.getXValue() < l) {
                outLeftBorderGraphicSeries.getData().add(dot);
            } else if (r < (double)dot.getXValue()) {
                outRightBorderGraphicSeries.getData().add(dot);
            } else {
                inBorderGraphicSeries.getData().add(dot);
            }
        }
    }

    private void drawParabola(Iteration it, double l, double r) {
        if (it.getParabola() == null) {
            return;
        }
        for (double i = l; i < r; i += (r - l) / 1000) {
            parabolaSeries.getData().add(new XYChart.Data<>(i, it.getParabola().apply(i)));
        }
    }

    private void setSeries() {
        inBorderGraphicSeries = new XYChart.Series<>();
        outLeftBorderGraphicSeries = new XYChart.Series<>();
        outRightBorderGraphicSeries = new XYChart.Series<>();
        parabolaSeries = new XYChart.Series<>();

        series = new ArrayList<>();
        series.add(outLeftBorderGraphicSeries);
        series.add(inBorderGraphicSeries);
        series.add(outRightBorderGraphicSeries);
        series.add(parabolaSeries);

        Platform.runLater(() -> lineChart.getData().addAll(series));
    }

    private void buildGraphicData(double l, double r, UnaryOperator<Double> function) {
        graphicData = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            double x = l + i * (r - l) / 1000;
            graphicData.add(new XYChart.Data<>(x, function.apply(x)));
        }
    }
}
