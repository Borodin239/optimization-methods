package lab2.optimization.gradient;

import lab1.optimizations.unary.GoldenSectionSearch;
import lab1.optimizations.unary.UnaryOptimization;
import lab2.optimization.QuadraticForm;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class FastestGradient extends BasicGradient {

    protected Vector getIteration(QuadraticForm form, Vector x) {
        Vector y = getNextPoint(form, x);
        UnaryOptimization op = new GoldenSectionSearch();

        Function<Double, Vector> slice =
                (val) -> x.add(y.subtract(x).multiply(val));

        UnaryOperator<Double> slicedFunc =
                (val) -> form.apply(slice.apply(val));

        double scale = op.getLastIteration(0, 1, epsilon, slicedFunc).getL();
        return slice.apply(scale);
    }

    @Override
    protected double getAlpha(QuadraticForm form, double epsilon, double... start) {
        return new BasicVector(start).euclideanNorm() * 4;
    }
}
