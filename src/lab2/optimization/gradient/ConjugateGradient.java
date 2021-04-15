package lab2.optimization.gradient;

import lab2.optimization.QuadraticForm;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;

public class ConjugateGradient extends BasicGradient {

    ArrayList<Double> euclideanNormsSquare = new ArrayList<>();
    ArrayList<Vector> ps = new ArrayList<>();

    protected Vector computeNext(QuadraticForm form, Vector x) {
        double currGradientNorm = form.getGradient(x).euclideanNorm();
        euclideanNormsSquare.add(currGradientNorm * currGradientNorm);
        double b = euclideanNormsSquare.get(euclideanNormsSquare.size() - 1) /
                euclideanNormsSquare.get(euclideanNormsSquare.size() - 2);
        ps.add(ps.get(ps.size() - 1).multiply(b).subtract(form.getGradient(x)));
        Vector to = getNextPoint(form, x, ps.get(ps.size() - 1));
        return Gradient.getMinOnSlice(form, x, to, epsilon);
    }

    protected Vector getIteration(QuadraticForm form, Vector x) {
        if (ps.size() == form.size()) {
            reinit(form, x);
        }
        if (ps.size() == 0) {
            reinit(form, x);
            return x;
        } else {
            return computeNext(form, x);
        }
    }

    private void reinit(QuadraticForm form, Vector x) {
        ps.clear();
        euclideanNormsSquare.clear();
        euclideanNormsSquare.add(Math.pow(form.getGradient(x).euclideanNorm(), 2));
        ps.add(form.getGradient(x).multiply(-1));
    }

    protected Vector getNextPoint(QuadraticForm form, Vector x, Vector p) {
        return x.add(p.multiply(alpha));
    }

    @Override
    protected double getAlpha(QuadraticForm form, double epsilon, double... start) {
        return new BasicVector(start).euclideanNorm() * 4;
    }
}