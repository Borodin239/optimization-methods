package lab1.optimizations.unary;

import lab1.tools.Iteration;

import java.util.List;
import java.util.function.UnaryOperator;

public interface UnaryOptimization {
    List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula);

    default Iteration getLastIteration(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> res = getOptimization(l, r, epsilon, formula);
        return res.get(res.size() - 1);
    }
}
