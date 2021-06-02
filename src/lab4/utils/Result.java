package lab4.utils;

public class Result {
    final double[] x;
    final int iterations;

    public Result(final double[] x, final int iterations) {
        this.x = x;
        this.iterations = iterations;
    }

    public double[] getX() {
        return x;
    }

    public int getIterations() {
        return iterations;
    }
}
