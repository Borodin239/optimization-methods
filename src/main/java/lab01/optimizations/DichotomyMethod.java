package lab01.optimizations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class DichotomyMethod implements UnaryOptimization {

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> iterations = new ArrayList<>();

        final double delta = epsilon;

        while ((r - l) / 2 > epsilon) {
            double x_1 = (r + l - delta) / 2;
            double x_2 = (r + l + delta) / 2;

            if (formula.apply(x_1) <= formula.apply(x_2)) {
                r = x_2;
            } else  {
                l = x_1;
            }

            iterations.add(new Iteration(l, r));
        }

        return iterations;
    }
}

