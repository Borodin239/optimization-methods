package lab01.graphics;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;

import java.util.function.UnaryOperator;

public class MainController {

    private LineChart.Series<Number, Number> functionSeries;
    private LineChart.Series<Number, Number> redSeries;
    private LineChart.Series<Number, Number> greenSeries;
    private LineChart.Series<Number, Number> blueSeries;

    @FXML
    LineChart<Number, Number> lineChart;

    @FXML
    Slider iterationSlider;

    public void build() {
        setLineChart();
    }

    private void setLineChart() {
        lineChart.setAnimated(false);
        lineChart.getXAxis().setAutoRanging(true);
        lineChart.getYAxis().setAutoRanging(true);
        new LineChart.Series<>();

        functionSeries = new LineChart.Series<>();
        redSeries = new LineChart.Series<>();
        greenSeries = new LineChart.Series<>();
        blueSeries = new LineChart.Series<>();

        Platform.runLater(() -> {
            lineChart.getData().add(functionSeries);
            lineChart.getData().add(redSeries);
            lineChart.getData().add(greenSeries);
            lineChart.getData().add(blueSeries);
        });

        drawFunction(1, 250, (x) -> 0.2 * x * Math.log10(x) + (x - 2.3) * (x - 2.3));
    }

    private void drawFunction(double l, double r, UnaryOperator<Double> function) {
        for (int i = 0; i < 1000; i++) {
            double x = l + i * (r - l) / 1000;
            functionSeries.getData().add(new LineChart.Data<>(x,
                    function.apply(x)));
        }
    }

}
