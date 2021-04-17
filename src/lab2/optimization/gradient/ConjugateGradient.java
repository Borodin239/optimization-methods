package lab2.optimization.gradient;

import lab2.optimization.QuadraticForm;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;

public class ConjugateGradient extends BasicGradient {

    ArrayList<Double> euclideanNormsSquare = new ArrayList<>();
    ArrayList<Vector> ps = new ArrayList<>();

    private double mul(Matrix a, Vector p) {
        Vector ap = a.multiply(p);
        double res = 0;
        for (int i = 0; i < p.length(); i++) {
            res += ap.get(i) * p.get(i);
        }
        return res;
    }

    protected Vector computeNext(QuadraticForm form, Vector x) {
        double gradientNormK = euclideanNormsSquare.get(euclideanNormsSquare.size() - 1);

//        Vector to = getNextPoint(form, x, ps.get(ps.size() - 1));
//        Vector x1 = Gradient.getMinOnSlice(form, x, to, epsilon);
        Vector pk = ps.get(ps.size() - 1);
        double ak = gradientNormK / mul(form.getA(), pk);
        Vector x1 = x.add(pk.multiply(ak));

        double gradientNormK1 = form.getGradient(x1).euclideanNorm();
        euclideanNormsSquare.add(gradientNormK1 * gradientNormK1);
        double b = gradientNormK1 / gradientNormK;
        ps.add(ps.get(ps.size() - 1).multiply(b).subtract(form.getGradient(x1)));
        return x1;
    }

    protected Vector getIteration(QuadraticForm form, Vector x) {
        if (ps.size() == form.size() || ps.isEmpty()) {
            reinit(form, x);
        }
        return computeNext(form, x);
    }

    private void reinit(QuadraticForm form, Vector x) {
        ps.clear();
        euclideanNormsSquare.clear();

        ps.add(form.getGradient(x).multiply(-1));
        euclideanNormsSquare.add(Math.pow(form.getGradient(x).euclideanNorm(), 2));
    }

    protected Vector getNextPoint(QuadraticForm form, Vector x, Vector p) {
        return x.add(p.multiply(alpha));
    }

    @Override
    protected double getAlpha(QuadraticForm form, double epsilon, double... start) {
        return new BasicVector(start).euclideanNorm() * 4;
    }
}