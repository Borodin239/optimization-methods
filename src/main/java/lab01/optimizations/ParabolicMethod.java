package lab01.optimizations;

import lab01.tools.Iteration;
import lab01.tools.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ParabolicMethod implements UnaryOptimization {

    public static Triple makeOneIteration(Triple res, UnaryOperator<Double> formula) {
        double x_1 = res.getX_1();
        double x_2 = res.getX_2();
        double x_3 = res.getX_3();

        double a_1 = (formula.apply(x_2) - formula.apply(x_1)) / (x_2 - x_1);
        double a_2 = (1 / (x_3 - x_2)) * ((formula.apply(x_3) - formula.apply(x_1)) / (x_3 - x_1) - a_1);
        double currentIterationResult = 0.5 * (x_1 + x_2 - a_1 / a_2);

        if (x_1 <= currentIterationResult && currentIterationResult <= x_2 && x_2 <= x_3) {
            if (formula.apply(currentIterationResult) >= formula.apply(x_2)) {
                x_1 = currentIterationResult;
            } else {
                x_3 = x_2;
                x_2 = currentIterationResult;
            }
        } else if (x_1 <= x_2 && x_2 <= currentIterationResult && currentIterationResult <= x_3) {
            if (formula.apply(currentIterationResult) <= formula.apply(x_2)) {
                x_1 = x_2;
                x_2 = currentIterationResult;
            } else {
                x_3 = currentIterationResult;
            }
        }

        return new Triple(x_1, x_2, x_3);
    }

    private UnaryOperator<Double> parabolaFunction(Triple points, UnaryOperator<Double> formula) {
        double x_1 = points.getX_1();
        double x_2 = points.getX_2();
        double x_3 = points.getX_3();
        double y_1 = formula.apply(x_1);
        double y_2 = formula.apply(x_2);
        double y_3 = formula.apply(x_3);

        double a = (x_3 * (y_2 - y_1) + x_2 * (y_1 - y_3) + x_1 * (y_3 - y_2)) / ((x_1 - x_2) * (x_1 - x_3) * (x_2 - x_3));
        double b = (y_2 - y_1 - a * (x_2 * x_2 - x_1 * x_1)) / (x_2 - x_1);
        double c = y_1 - a * x_1 * x_1 - b * x_1;

        return (x) -> a * x * x + b * x + c;
    }

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> optimizationResult = new ArrayList<>();
        optimizationResult.add(new Iteration(l, r));

        Triple lastIterationResult = new Triple(l, 2.1, r);

        while (true) {
            Triple currentIterationResult = makeOneIteration(lastIterationResult, formula);

            optimizationResult.add(new Iteration(currentIterationResult.getX_1(), currentIterationResult.getX_3()));
            optimizationResult.get(optimizationResult.size() - 1).setParabola(parabolaFunction(currentIterationResult, formula));

            if (Math.abs(currentIterationResult.getX_2() - lastIterationResult.getX_2()) < epsilon) {
                optimizationResult.add(new Iteration(currentIterationResult.getX_2() - epsilon,
                        currentIterationResult.getX_2() + epsilon));
                break;
            }
            lastIterationResult = currentIterationResult;
        }

        return optimizationResult;
    }
}
