package lab2.optimization;

import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

public class LinearQuadraticForm extends QuadraticForm {
    private final Vector diag;

    public LinearQuadraticForm(double[] d) {
        super(new double[0][], new double[0], 0);
        diag = new BasicVector(d);
    }

    @Override
    public int size() {
        return diag.length();
    }

    @Override
    public double apply(Vector x) {
        double result = 0;
        for (int i = 0; i < size(); i++) {
            result += x.get(i) * diag.get(i) * x.get(i) * 0.5;
        }
        return result;
    }

    @Override
    public Vector getGradient(Vector x) {
        double[] g = new double[size()];
        for (int i = 0; i < size(); i++) {
            g[i] = diag.get(i) * x.get(i);
        }
        return new BasicVector(g);
    }

    @Override
    public double getBigL() {
        double ans = diag.get(0);
        for (int i = 0; i < size(); i++) {
            ans = Math.max(ans, diag.get(i));
        }
        return ans;
    }

    @Override
    public double getSmallL() {
        double ans = diag.get(0);
        for (int i = 0; i < size(); i++) {
            ans = Math.min(ans, diag.get(i));
        }
        return ans;
    }

    @Override
    protected void check(double[][] a, double[] b, double c) {
    }
}
