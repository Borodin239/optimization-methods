package lab01.optimizations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ParabolicMethod implements UnaryOptimization {

    public static double makeOneIteration(double x_1, double x_2, double x_3, UnaryOperator<Double> formula) {
        double a_1 = (formula.apply(x_2) - formula.apply(x_1)) / (x_2 - x_1);
        double a_2 =  (1 / (x_3 - x_2)) * ((formula.apply(x_3) - formula.apply(x_1)) / (x_3 - x_1) - a_1);
        return 0.5 * (x_1 + x_2 - a_1 / a_2);
    }

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> optimizationResult = new ArrayList<>();
        optimizationResult.add(new Iteration(l, r));

        double x_1 = l;
        double x_2 = (l + r) / 2;
        double x_3 = r;

        double lastIterationResult = r + 1 + epsilon;

        while (true) {
            double currentIterationResult = makeOneIteration(x_1, x_2, x_3, formula);

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

            optimizationResult.add(new Iteration(x_1, x_3));

            if (Math.abs(currentIterationResult - lastIterationResult) < epsilon) {
                optimizationResult.add(new Iteration(currentIterationResult - epsilon,
                                                        currentIterationResult + epsilon));
                break;
            }
            lastIterationResult = currentIterationResult;
        }

        return optimizationResult;
    }
}
