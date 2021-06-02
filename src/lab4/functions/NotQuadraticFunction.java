package lab4.functions;

public class NotQuadraticFunction implements FunctionInfo {
    // (x_1)^4 + (x_2)^2 + (x_3)^2 - 12(x_2)(x_3) + 17

    @Override
    public double apply(double[] values) {
        return Math.pow(values[0], 4) + Math.pow(values[1], 2) + Math.pow(values[2], 2)
                - 12 * values[1] * values[2] + 17;
    }

    @Override
    public double[][] hessian(double[] values) {
        return new double[][]{
                {12 * values[0] * values[0], 0, 0},
                {0, 2, -12},
                {0, -12, 2}
        };
    }

    @Override
    public double[] gradient(double[] values) {
        return new double[]{4 * Math.pow(values[0], 3), 2 * values[1] - 12 * values[2], 2 * values[2] - 12 * values[1]};
    }
}
