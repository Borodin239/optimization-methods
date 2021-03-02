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

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> optimizationResult = new ArrayList<>();
        optimizationResult.add(new Iteration(l, r));

        Triple lastIterationResult = new Triple(l, (l + r) / 2, r);

        while (true) {
            Triple currentIterationResult = makeOneIteration(lastIterationResult, formula);

            optimizationResult.add(new Iteration(currentIterationResult.getX_1(), currentIterationResult.getX_2()));

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
