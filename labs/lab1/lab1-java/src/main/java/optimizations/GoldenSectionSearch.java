package optimizations;

import tools.Iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class GoldenSectionSearch implements UnaryOptimization {
    private final double GOLDEN_SECTION = (1 + Math.sqrt(5)) / 2.0;

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> optimizationResult = new ArrayList<>();
        optimizationResult.add(new Iteration(l, r));
        double x1 = l + (r - l) / (GOLDEN_SECTION + 1);
        double x2 = r - (r - l) / (GOLDEN_SECTION + 1);
        double f1 = formula.apply(x1);
        double f2 = formula.apply(x2);
        do {
            if (f1 < f2) {
                r = x2;
                x2 = x1;
                f2 = f1;
                x1 = l + (r - l) / (GOLDEN_SECTION + 1);
                f1 = formula.apply(x1);
            } else {
                l = x1;
                x1 = x2;
                f1 = f2;
                x2 = r - (r - l) / (GOLDEN_SECTION + 1);
                f2 = formula.apply(x2);
            }
            optimizationResult.add(new Iteration(l, r));
        } while (Math.abs(r - l) > epsilon);
        return optimizationResult;
    }
}
