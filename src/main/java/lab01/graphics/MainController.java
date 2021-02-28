package lab01.graphics;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import lab01.optimizations.*;

import java.util.List;
import java.util.function.UnaryOperator;


public class MainController {

    @FXML
    LineChart<Number, Number> lineChart;

    @FXML
    Slider iterationSlider;

    @FXML
    TextField lField;

    @FXML
    TextField rField;

    @FXML
    Button evaluateButton;

    private List<Iteration> iterations;
    private GraphicChart chart;
    private final UnaryOperator<Double> formula = (x) -> 0.2 * x * Math.log10(x) + (x - 2.3) * (x - 2.3);

    public void build() {
        chart = new GraphicChart(lineChart, formula);

        evaluateButton.onActionProperty().setValue((a) -> evaluate());
        iterationSlider.valueProperty().addListener((a, oldV, newV) -> {
            if (iterations != null && findIteration((double) oldV) != findIteration((double) newV)) {
                System.out.println(findIteration((double) newV) + " " + newV);
                chart.update(findIteration((double) newV));
            }
        });
    }

    private int findIteration(double val) {
        return Math.min(iterations.size() - 1, (int) ((val - iterationSlider.getMin()) /
                (iterationSlider.getMax() - iterationSlider.getMin())
                * (iterations.size() - 1)));
    }

    private void evaluate() {
        double l = Double.parseDouble(lField.getText());
        double r = Double.parseDouble(rField.getText());


        UnaryOptimization opt = new FibonacciMethod();
        // ^^^^^^ PASTE OPTIMIZATION HERE ^^^^^^

        iterations = opt.getOptimization(l, r, (r - l) * 0.000001, formula);
        chart.setGraphics(iterations, l, r);
    }


}
