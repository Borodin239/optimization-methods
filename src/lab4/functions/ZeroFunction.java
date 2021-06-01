package lab4.functions;

public class ZeroFunction implements FunctionInfo {
    // (x_1)^2 + (x_2)^2 - 1.2 * x_1 * x_2

    @Override
    public double apply(final double[] v) {
        return v[0] * v[0] + v[1] * v[1] - 1.2 * v[0] * v[1];
    }

    @Override
    public double[] gradient(final double[] v) {
        return new double[] {2 * v[0] - 1.2 * v[1], 2 * v[1] - 1.2 * v[0]};
    }

    @Override
    public double[][] hessian(final double[] v) {
        return new double[][] {
                {2, -1.2},
                {-1.2, 2}
        };
    }
}
