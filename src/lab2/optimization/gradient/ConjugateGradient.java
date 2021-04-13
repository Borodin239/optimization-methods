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
        return x.add(ys.get(ys.size() - 1). multiply(alpha));
    }

    protected Vector getIteration(QuadraticForm form, Vector x) {
        Vector y;
        Vector xNew = ys.isEmpty() ? x : getNextPoint(form, x);
        if (ys.size() == 0) {
            y = form.getGradient(xNew).multiply(-1);
            double currGradientNorm = form.getGradient(xNew).euclideanNorm();
            euclideanNormsSquare.add(currGradientNorm * currGradientNorm);
        } else {
            double currGradientNorm = form.getGradient(xNew).euclideanNorm();
            euclideanNormsSquare.add(currGradientNorm * currGradientNorm);
            double b = euclideanNormsSquare.get(euclideanNormsSquare.size() - 1) /
                    euclideanNormsSquare.get(euclideanNormsSquare.size() - 2);
            y = ys.get(ys.size() - 1).multiply(b).subtract(form.getGradient(xNew));

        }
        ys.add(y);
        return y;
    }

}