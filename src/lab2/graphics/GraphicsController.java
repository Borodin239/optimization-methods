package lab2.graphics;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import lab2.optimization.Iteration;
import lab2.optimization.QuadraticForm;
import lab2.optimization.gradient.BasicGradient;
import lab2.optimization.gradient.ConjugateGradient;
import lab2.optimization.gradient.FastestGradient;
import lab2.optimization.gradient.Gradient;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class GraphicsController {

    @FXML
    LineChart<Number, Number> lineChart;

    @FXML
    Slider iterationSlider;

    @FXML
    TextField xField;

    @FXML
    TextField yField;

    @FXML
    Button evaluateButton;

    @FXML
    Button applyButton;

    @FXML
    ChoiceBox<String> choiceBox;

    @FXML
    Label borderLabel;

    @FXML
    CheckBox showLevels;

    private List<Iteration> iterations;
    private GraphicChart chart;
    QuadraticForm form1 = new QuadraticForm(new double[][]{
            {2, 0},
            {0, 2},
    }, new double[] {0, 0});
    QuadraticForm form = new QuadraticForm(new double[][]{
            {2, 0},
            {0, 8},
    }, new double[] {4, 2});
    private Map<String, Gradient> optimizationMap;

    public void build() {
        chart = new GraphicChart(lineChart, form);

        evaluateButton.onActionProperty().setValue((a) -> evaluate());
        applyButton.onActionProperty().setValue((a) -> setShowLevels(showLevels.isSelected()));
        iterationSlider.valueProperty().addListener((a, oldV, newV) -> {
            int newIterationIndex = findIteration((double) newV);
            if (iterations != null && newIterationIndex != findIteration((double) oldV)) {
                Iteration iteration = iterations.get(newIterationIndex);
                updateBorderLabel(iteration.getX().get(0), iteration.getX().get(1));
                chart.update(newIterationIndex);
            }
        });
        ChartPanManager panner = new ChartPanManager(lineChart);
        //while pressing the left mouse button, you can drag to navigate
        panner.setMouseFilter(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {//set your custom combination to trigger navigation
                // let it through
            } else {
                mouseEvent.consume();
            }
        });
        panner.start();

        //holding the right mouse button will draw a rectangle to zoom to desired location
        JFXChartUtil.setupZooming(lineChart, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.SECONDARY)//set your custom combination to trigger rectangle zooming
                mouseEvent.consume();
        });
        fillOptimizationMap();
        choiceBox.getItems().addAll(optimizationMap.keySet());
    }

    private void updateBorderLabel(double x, double y) {
        borderLabel.setText(String.format("[ %.5f : %.5f ]", x, y));
    }

    private void fillOptimizationMap() {
        optimizationMap = new TreeMap<>();
        optimizationMap.put("Метод градиентного спуска", new BasicGradient());
        optimizationMap.put("Метод наискорейшего спуска", new FastestGradient());
        optimizationMap.put("Метод сопряжённых градиентов", new ConjugateGradient());
    }

    private int findIteration(double val) {
        return Math.min(iterations.size() - 1, (int) ((val - iterationSlider.getMin()) /
                (iterationSlider.getMax() - iterationSlider.getMin())
                * (iterations.size() - 1)));
    }

    private void setShowLevels(boolean set) {
        if (set) {
            chart.hideLevels();
        } else {
            chart.showLevels();
        }
    }

    private void evaluate() {
        try {
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());

            String methodName = choiceBox.getValue();
            showLevels.setSelected(true);
            if (methodName != null && optimizationMap.containsKey(methodName)) {
                iterations = optimizationMap.get(methodName)
                        .getOptimization(form, 0.001, x, y);
                System.out.println(iterations.size());
                chart.clearSeries();
                updateBorderLabel(iterations.get(0).getX().get(0), iterations.get(0).getX().get(1));
                chart.setGraphics(iterations);
            }
        } catch (NumberFormatException ignored) {
            // do nothing
        }
    }
}
