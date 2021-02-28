package lab01.optimizations;

import java.util.List;
import java.util.function.UnaryOperator;

public class DichotomyMethod extends BasicOptimization {

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        while ((r - l) / 2 > epsilon && checkIteration(l, r)) {
            double x_1 = (r + l - epsilon) / 2;
            double x_2 = (r + l + epsilon) / 2;

            if (formula.apply(x_1) <= formula.apply(x_2)) {
                r = x_2;
            } else {
                l = x_1;
            }

            optimizationResult.add(new Iteration(l, r));
        }

        return optimizationResult;
    }
}

