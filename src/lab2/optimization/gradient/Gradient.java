package lab2.optimization.gradient;

import lab1.optimizations.unary.DichotomyMethod;
import lab1.optimizations.unary.GoldenSectionSearch;
import lab1.optimizations.unary.UnaryOptimization;
import lab2.optimization.Iteration;
import lab2.optimization.QuadraticForm;
import org.la4j.Vector;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface Gradient {

    List<Iteration> getOptimization(QuadraticForm form, double epsilon, double ... start);

    static Vector getMinOnSlice(QuadraticForm form, Vector x, Vector y, double epsilon) {
        UnaryOptimization op = new DichotomyMethod();

        Function<Double, Vector> slice =
                (val) -> x.add(y.subtract(x).multiply(val));

        UnaryOperator<Double> slicedFunc =
                (val) -> form.apply(slice.apply(val));
        double scale = op.getLastIteration(0, 1, 1e-9, slicedFunc).getL();
        return slice.apply(scale);
    }
}
