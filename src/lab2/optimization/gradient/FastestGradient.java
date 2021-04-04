package lab2.optimization.gradient;

import lab1.optimizations.unary.GoldenSectionSearch;
import lab1.optimizations.unary.UnaryOptimization;
import lab2.optimization.QuadraticForm;
import org.la4j.Vector;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class FastestGradient extends BasicGradient {
    protected Vector getIteration(QuadraticForm form, Vector x) {
        Vector y;
        while (form.apply(x) <= form.apply(y = getNextPoint(form, x))) {
            alpha /= 2;
        }
        UnaryOptimization op = new GoldenSectionSearch();

        final Vector z = y;
        Function<Double, Vector> slice =
                (val) -> x.add(z.subtract(x).multiply(val));

        UnaryOperator<Double> slicedFunc =
                (val) -> form.apply(slice.apply(val));

        double scale = op.getLastIteration(0, 1, epsilon, slicedFunc).getL();
        return slice.apply(scale);
    }

}
