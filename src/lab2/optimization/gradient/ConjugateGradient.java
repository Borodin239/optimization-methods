package lab2.optimization.gradient;

import lab1.optimizations.unary.GoldenSectionSearch;
import lab1.optimizations.unary.UnaryOptimization;
import lab2.optimization.Iteration;
import lab2.optimization.QuadraticForm;
import org.la4j.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ConjugateGradient extends BasicGradient {

    ArrayList<Double> euclideanNormsSquare = new ArrayList<>();
    ArrayList<Vector> ys = new ArrayList<>();

    @Override
    protected Vector getNextPoint(QuadraticForm form, Vector x) {
        double currGradientNorm = form.getGradient(x).euclideanNorm();
        euclideanNormsSquare.add(currGradientNorm * currGradientNorm);
        double b = euclideanNormsSquare.get(euclideanNormsSquare.size() - 1) /
                   euclideanNormsSquare.get(euclideanNormsSquare.size() - 2);
        return ys.get(ys.size() - 1).multiply(b).subtract(form.getGradient(x));
    }

    protected Vector getIteration(QuadraticForm form, Vector x) {
        Vector y;
        if (ys.size() == 0) {
            y = form.getGradient(x).multiply(-1);
            double currGradientNorm = form.getGradient(x).euclideanNorm();
            euclideanNormsSquare.add(currGradientNorm * currGradientNorm);
        } else {
            y = getNextPoint(form, x);
        }
        ys.add(y);
        return y;
    }

}
