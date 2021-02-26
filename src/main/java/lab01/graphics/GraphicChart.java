package lab01.graphics;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import lab01.optimizations.Dot;
import lab01.optimizations.Iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class GraphicChart {

    private final LineChart<Number, Number> lineChart;
    private final UnaryOperator<Double> formula;

    private LineChart.Series<Number, Number> outLeftBorderGraphicSeries;
    private LineChart.Series<Number, Number> outRightBorderGraphicSeries;
    private LineChart.Series<Number, Number> inBorderGraphicSeries;
    private LineChart.Series<Number, Number> redSeries;
    private LineChart.Series<Number, Number> greenSeries;
    private LineChart.Series<Number, Number> blueSeries;
    private List<LineChart.Series<Number, Number>> series;

    private List<LineChart.Data<Number, Number>> graphicData;

    private List<Iteration> iterations;


    public GraphicChart(LineChart<Number, Number> lineChart, UnaryOperator<Double> formula) {
        this.lineChart = lineChart;
        this.formula = formula;

        lineChart.setLegendVisible(false);
        lineChart.getXAxis().setAutoRanging(false);
        lineChart.getYAxis().setAutoRanging(false);
        lineChart.setCreateSymbols(false);

        setSeries();
    }

    public void setGraphics(List<Iteration> iterations, double l, double r) {
        this.iterations = iterations;
        buildGraphicData(l, r, formula);
        resizeChart(iterations);
        drawFormula(l, r);
    }

    private void resizeChart(List<Iteration> iterations) {
        double xMax = Double.MIN_VALUE;
        double yMax = Double.MIN_VALUE;
        double xMin = Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;

        for (Iteration iteration : iterations) {
            for (Dot dot : iteration.getDots()) {
                xMax = Math.max(xMax, dot.getX());
                yMax = Math.max(yMax, dot.getY());
                xMin = Math.min(xMin, dot.getX());
                yMin = Math.min(yMin, dot.getY());
            }
        }

        for (LineChart.Data<Number, Number> dot : graphicData) {
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
        fillColorSeries(iteration);
        drawFormula(iteration.getL(), iteration.getR());
    }

    private void fillColorSeries(Iteration iteration) {
        for (Dot dot : iteration.getDots()) {
            switch (dot.getColor()) {
                case RED -> redSeries.getData().add(new LineChart.Data<>(dot.getX(), dot.getY()));
                case BLUE -> blueSeries.getData().add(new LineChart.Data<>(dot.getX(), dot.getY()));
                case GREEN -> greenSeries.getData().add(new LineChart.Data<>(dot.getX(), dot.getY()));
            }
        }
    }

    private void drawFormula(double l, double r) {
        for (LineChart.Data<Number, Number> dot : graphicData) {
            if ((double)dot.getXValue() < l) {
                outLeftBorderGraphicSeries.getData().add(dot);
            } else if (r < (double)dot.getXValue()) {
                outRightBorderGraphicSeries.getData().add(dot);
            } else {
                inBorderGraphicSeries.getData().add(dot);
            }
        }
    }

    private void setSeries() {
        inBorderGraphicSeries = new LineChart.Series<>();
        outLeftBorderGraphicSeries = new LineChart.Series<>();
        outRightBorderGraphicSeries = new LineChart.Series<>();
        redSeries = new LineChart.Series<>();
        greenSeries = new LineChart.Series<>();
        blueSeries = new LineChart.Series<>();

        series = List.of(inBorderGraphicSeries, outLeftBorderGraphicSeries, outRightBorderGraphicSeries,
                redSeries, greenSeries, blueSeries);

        Platform.runLater(() -> series.forEach((s) -> lineChart.getData().add(s)));

    }

    private void buildGraphicData(double l, double r, UnaryOperator<Double> function) {
        graphicData = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            double x = l + i * (r - l) / 1000;
            graphicData.add(new LineChart.Data<>(x, function.apply(x)));
        }
    }
}
