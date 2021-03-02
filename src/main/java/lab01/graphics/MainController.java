package lab01.graphics;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import lab01.optimizations.*;
import lab01.optimizations.UnaryOptimization;
import lab01.tools.Iteration;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    @FXML
    ChoiceBox<String> choiceBox;

    @FXML
    Label borderLabel;

    private List<Iteration> iterations;
    private GraphicChart chart;
    private final UnaryOperator<Double> formula = (x) -> 0.2 * x * Math.log10(x) + (x - 2.3) * (x - 2.3);
    private Map<String, UnaryOptimization> optimizationMap;

    public void build() {
        chart = new GraphicChart(lineChart, formula);

        evaluateButton.onActionProperty().setValue((a) -> evaluate());
        iterationSlider.valueProperty().addListener((a, oldV, newV) -> {
            int newIterationIndex = findIteration((double) newV);
            if (iterations != null && newIterationIndex != findIteration((double) oldV)) {
                Iteration iteration = iterations.get(newIterationIndex);
                borderLabel.setText("[ " + iteration.getL() + " : " + iteration.getR() + " ]");
                chart.update(newIterationIndex);
            }
        });
        fillOptimizationMap();
        choiceBox.getItems().addAll(optimizationMap.keySet());
    }

    private void fillOptimizationMap() {
        optimizationMap = new TreeMap<>();
        optimizationMap.put("Фибоначчи", new FibonacciMethod());
        optimizationMap.put("Дихотомия", new DichotomyMethod());
        optimizationMap.put("Золотое сечение", new GoldenSectionSearch());
        optimizationMap.put("Метод парабол", new ParabolicMethod());
        optimizationMap.put("Метод Брандта", new BrandOptimization());
    }

    private int findIteration(double val) {
        return Math.min(iterations.size() - 1, (int) ((val - iterationSlider.getMin()) /
                (iterationSlider.getMax() - iterationSlider.getMin())
                * (iterations.size() - 1)));
    }

    private void evaluate() {
        try {
            double l = Double.parseDouble(lField.getText());
            double r = Double.parseDouble(rField.getText());

            String methodName = choiceBox.getValue();
            if (methodName != null && optimizationMap.containsKey(methodName)) {
                iterations = optimizationMap.get(methodName)
                        .getOptimization(l, r, (r - l) * 0.000001, formula);
                chart.setGraphics(iterations, l, r);
            }
        } catch (NumberFormatException ignored) {

        }
    }
}
