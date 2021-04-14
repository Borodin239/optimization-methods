package lab2.graphics;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import lab2.optimization.Iteration;
import lab2.optimization.QuadraticForm;
import org.la4j.Vector;

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
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        setSeries();
    }

    public void setGraphics(List<Iteration> iterations) {
        dotsGraphicSeries.getNode().setStyle("-fx-stroke-width: 2px;");
        this.iterations = iterations;
        buildGraphicData();
        resizeChart();
        drawLevels();
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

    public void clearSeries() {
        series.forEach(a -> a.getData().clear());
    }


    public void update(int iterationNum) {
        dotsGraphicSeries.getData().clear();
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

    public void hideLevels() {
        for (int i = 0; i < iterations.size() && i < 8; i++) {
            series.get(i + 1).getNode().setStyle("-fx-stroke-width: 1px;");
        }
    }

    public void showLevels() {
        for (int i = 0; i < iterations.size() && i < 8; i++) {
            series.get(i + 1).getNode().setStyle("-fx-stroke-width: 0px;");
        }
    }

    private void drawLevels() {
        for (int i = 0; i < iterations.size() && i < 8; i++) {
            List<Vector> temp = form.getLevel(iterations.get(i).getX(), 0.01, 40000);
            for (Vector v : temp) {
                series.get(i + 1).getData().add(new XYChart.Data<>(v.get(0), v.get(1)));
            }
        }
    }

    private void setSeries() {

        series = new ArrayList<>();
        dotsGraphicSeries = new XYChart.Series<>();
        series.add(dotsGraphicSeries);
        for (int i = 0; i < 50; i++) {
            XYChart.Series<Number, Number> temp = new XYChart.Series<>();
            series.add(temp);
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

