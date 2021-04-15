package lab2.optimization;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class QuadraticForm {

    private final Matrix a;
    private final Vector b;
    private final double c;

    public QuadraticForm(double[][] a, double[] b, double c) {
        check(a, b, c);

        this.a = new Basic2DMatrix(a);
        this.b = new BasicVector(b);
        this.c = c;
    }

    public QuadraticForm(double[][] a, double[] b) {
        this(a, b, 0);
    }

    public QuadraticForm(double[][] a) {
        this(a, new double[a.length]);
    }

    public Matrix getA() {
        return a;
    }

    public Vector getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public int size() {
        return a.columns();
    }

    private void checkLength(Vector x) {
        if (x.length() != size()) {
            throw new IllegalArgumentException(
                    size() + " arguments must be providied");
        }
    }

    public double apply(Vector x) {
        checkLength(x);
        double result = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                result += x.get(i) * a.get(i, j) * x.get(j) * 0.5;
            }
        }
        for (int i = 0; i < size(); i++) {
            result += x.get(i) * b.get(i);
        }
        result += c;
        return result;
    }

    public Vector getGradient(Vector x) {
        checkLength(x);

        double[] g = new double[size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                g[i] += a.get(i, j) * x.get(j);
            }
            g[i] += b.get(i);
        }
        return new BasicVector(g);
    }

    private double[] getEigenValues() {
        Matrix decomposed = new EigenDecompositor(a).decompose()[1];
        double[] res = new double[size()];
        for (int i = 0; i < size(); i++) {
            res[i] = decomposed.get(i, i);
        }
        return res;
    }

    private double getEigen(BinaryOperator<Double> op) {
        double[] e = getEigenValues();
        double res = e[0];
        for (int i = 0; i < size(); i++) {
            res = op.apply(res, e[i]);
        }
        return res;
    }

    public double getBigL() {
        return getEigen(Math::max);
    }

    public double getSmallL() {
        return getEigen(Math::min);
    }

    public static boolean doubleEqual(double a, double b) {
        return Math.abs(a - b) < 1e-10;
    }

    private static void check(double[][] a, double[] b, double c) {
        if (a == null || b == null) {
            throw new IllegalArgumentException();
        }
        if (a.length != b.length) {
            throw new IllegalArgumentException();
        }
        for (double[] doubles : a) {
            if (doubles.length != a.length) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (!doubleEqual(a[i][j], a[j][i])) {
                    throw new IllegalArgumentException("Matrix is not symmetric");
                }
            }
        }
    }

    // for 2d only!
    public List<Vector> getLevel(Vector start, double deltaLength, double angleParts) {
        if (size() != 2) {
            throw new IllegalArgumentException("Size must be 2");
        }
        if (start.length() != 2) {
            throw new IllegalArgumentException("Start length must be 2");
        }
        Vector delta = turnOnAngle(getGradient(start), Math.PI / 2);
        delta = delta.divide(delta.euclideanNorm()).multiply(deltaLength);

        Vector cur = start;
        Vector cmp = delta;
        double angle = Math.PI * 2 / angleParts;

        List<Vector> level = new ArrayList<>();
        level.add(cur);

        delta = turnOnAngle(delta, angle);
        boolean negativeAngReached = false;

        while (getAngle(delta, cmp) <= 0 || !negativeAngReached) {
            negativeAngReached = negativeAngReached | getAngle(delta, cmp) <= 0;

            while (apply(cur) < apply(cur.add(delta))) {
                delta = turnOnAngle(delta, angle);
            }
            cur = cur.add(delta);
            level.add(cur);
        }
        return level;
    }

    private double getAngle(Vector a, Vector b) {
        double x1 = a.get(0);
        double y1 = a.get(1);
        double x2 = b.get(0);
        double y2 = b.get(1);
        return Math.atan2(y2 * x1 - x2 * y1, x1 * x2 + y1 * y2);
    }

    private Vector turnOnAngle(Vector vec, double angle) {
        double x = vec.get(0);
        double y = vec.get(1);

        double x1 = x * cos(angle) + y * sin(angle);
        double y1 = y * cos(angle) - x * sin(angle);

        return new BasicVector(new double[] {x1, y1});
    }
}
