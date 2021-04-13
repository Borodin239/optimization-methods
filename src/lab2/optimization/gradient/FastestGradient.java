package lab2.optimization.gradient;

import lab1.optimizations.unary.GoldenSectionSearch;
import lab1.optimizations.unary.UnaryOptimization;
import lab2.optimization.QuadraticForm;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class FastestGradient extends BasicGradient {

    @Override
    protected Vector getIteration(QuadraticForm form, Vector x) {
        Vector y = getNextPoint(form, x);
        return Gradient.getMinOnSlice(form, x, y, epsilon);
    }

    @Override
    protected double getAlpha(QuadraticForm form, double epsilon, double... start) {
        return new BasicVector(start).euclideanNorm() * 4;
    }
}
