package gradient;

import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class QuadraticForm {

    private final double[][] a;
    private final double[] b;
    private final double c;

    public QuadraticForm(double[][] a, double[] b, double c) {
        check(a, b, c);

        this.a = a;
        this.b = b;
        this.c = c;
    }

    public QuadraticForm(double[][] a, double[] b) {
        this(a, b, 0);
    }

    public QuadraticForm(double[][] a) {
        this(a, new double[a.length]);
    }

    public double[][] getA() {
        return a;
    }

    public double[] getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public int size() {
        return a.length;
    }

    private void checkLength(double[] x) {
        if (x.length != size()) {
            throw new IllegalArgumentException(
                    size() + " arguments must be providied");
        }
    }

    public double apply(double ... x) {
        checkLength(x);
        double result = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                result += x[i] * a[i][j] * x[j];
            }
        }
        for (int i = 0; i < size(); i++) {
            result += x[i] * b[i];
        }
        result += c;
        return result;
    }

    public double[] gradient(double... x) {
        checkLength(x);

        double[] g = new double[size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                g[i] += a[i][j] * x[j];
            }
            g[i] += b[i];
        }
        return g;
    }

    private double[] getEigenValues() {
        Matrix matrix = new Basic2DMatrix(a);
        Matrix decomposed = new EigenDecompositor(matrix).decompose()[1];
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

    private static boolean doubleEqual(double a, double b) {
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
}
