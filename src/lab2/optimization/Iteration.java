package lab2.optimization;

import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

public class Iteration {
    private final Vector x;

    public Iteration(double ... x) {
        this.x = new BasicVector(x);
    }

    public Iteration(Vector x) {
        this.x = x;
    }

    public Vector getX() {
        return x;
    }
}
