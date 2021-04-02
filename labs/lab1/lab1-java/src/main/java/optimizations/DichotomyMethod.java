package optimizations;

import tools.Iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class DichotomyMethod implements UnaryOptimization {

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> optimizationResult = new ArrayList<>();
        optimizationResult.add(new Iteration(l, r));
        while ((r - l) / 2 > epsilon) {
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

