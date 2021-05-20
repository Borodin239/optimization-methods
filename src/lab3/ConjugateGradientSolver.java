package lab3;

import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

public class ConjugateGradientSolver {

    private Vector r;
    private Vector x;
    private Vector z;

    // find solvation x of equation ax = b
    public Vector solve(Matrix a, Vector b) {
        x = new BasicVector(a.size());
        do {
            reinit(a, b);
            for (int i = 0; i < a.size(); i++) {
                doIteration(a, b);
            }
        } while (r.euclideanNorm() / b.euclideanNorm() > 1e-6);
        return x;
    }

    //scalar multiplication a on b
    private static double mulVecVec(Vector a, Vector b) {
        double res = 0;
        for (int i = 0; i < a.length(); i++) {
            res += a.get(i) * b.get(i);
        }
        return res;
    }

    // one iteration of solve's for
    private void doIteration(Matrix a, Vector b) {
        double alpha = mulVecVec(r, r) / mulVecVec(a.mulOnVec(z), z);
        x = x.add(z.multiply(alpha));
        Vector newR = r.subtract(a.mulOnVec(z).multiply(alpha));
        double betta = mulVecVec(newR, newR) / mulVecVec(r, r);
        z = newR.add(z.multiply(betta));
        r = newR;
    }

    // reinit values in sake of accuracy
    private void reinit(Matrix a, Vector b) {
        r = b.subtract(a.mulOnVec(x));
        z = r;
    }
}