package lab4.functions;

public class SecondFunction implements FunctionInfo {
    @Override
    public double apply(double[] values) {
        return Math.pow(values[0] * values[0] + values[1] - 11, 2) + Math.pow(values[0] + values[1] * values[1] - 7, 2);
    }

    @Override
    public double[] gradient(double[] values) {
        return new double[] {
                2 * (2 * values[0] * (values[0] * values[0] + values[1] - 11) + values[0] + values[1] * values[1] - 7),
                2 * (2 * values[1] * (values[0] + values[1] * values[1] - 7) + values[0] * values[0] + values[1] - 11),
        };
    }

    @Override
    public double[][] hessian(double[] v) {
        return new double[][] {
                {4 * (v[0] * v[0] + v[1] - 11) + 8 * v[0] * v[0] + 2, 4 * v[0] + 4 * v[1]},
                {4 * v[0] + 4 * v[1], 4 * (v[0] + v[1] * v[1] - 7) + 8 * v[1] * v[1] + 2},
        };
    }
}
