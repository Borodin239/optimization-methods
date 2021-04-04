package gradient;

public class Iteration {
    private final double[] x;

    public Iteration(double ... x) {
        this.x = x;
    }

    public double[] getX() {
        return x;
    }
}
