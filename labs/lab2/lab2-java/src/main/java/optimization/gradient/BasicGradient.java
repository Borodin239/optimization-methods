package optimization.gradient;

import optimization.Iteration;
import optimization.QuadraticForm;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;
import java.util.List;

public class BasicGradient implements Gradient {

    private double alpha;

    @Override
    public List<Iteration> getOptimization(QuadraticForm form, double epsilon, double... start) {
        alpha = 2 / (form.getBigL() + form.getSmallL());

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

    private Vector getIteration(QuadraticForm form, Vector x) {
        Vector y = getNextPoint(form, x);
        while (form.apply(x) <= form.apply(y)) {
            alpha /= 2;
        }
        return y;
    }

    private Vector getNextPoint(QuadraticForm form, Vector x) {
        return x.subtract(form.getGradient(x).multiply(alpha));
    }
}
