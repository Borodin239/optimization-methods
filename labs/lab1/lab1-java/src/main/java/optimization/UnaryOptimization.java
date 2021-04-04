package optimization;

import tools.Iteration;

import java.util.List;
import java.util.function.UnaryOperator;

public interface UnaryOptimization {
    List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula);
}
