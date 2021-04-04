package lab2.optimization.gradient;

import lab2.optimization.*;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;
import java.util.List;

public class BasicGradient implements Gradient {

    protected double alpha;
    protected double epsilon;

    @Override
    public List<Iteration> getOptimization(QuadraticForm form, double epsilon, double... start) {
        this.alpha = getAlpha(form, epsilon, start);
        this.epsilon = epsilon;

        List<Iteration> result = new ArrayList<>();

        Vector prev = getIteration(form, new BasicVector(start));
        result.add(new Iteration(prev));

        Vector cur = getIteration(form, prev);
        result.add(new Iteration(cur));

        while (distance(cur, prev) > epsilon) {
            prev = cur;
            cur = getIteration(form, cur);
            result.add(new Iteration(cur));
        }
        return result;
    }

    private double distance(Vector a, Vector b) {
        return a.subtract(b).euclideanNorm();
    }

    protected Vector getIteration(QuadraticForm form, Vector x) {
        Vector y;
        while (form.apply(x) <= form.apply(y = getNextPoint(form, x))) {
            alpha /= 2;
        }
        return y;
    }

    protected Vector getNextPoint(QuadraticForm form, Vector x) {
        return x.subtract(form.getGradient(x).multiply(alpha));
    }

    protected double getAlpha(QuadraticForm form, double epsilon, double... start) {
        return 2 / (form.getBigL() + form.getSmallL());
    }
}
