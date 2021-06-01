package lab4.functions;

public class FirstFunction implements FunctionInfo {
    @Override
    public double apply(double[] values) {
        return 100 * (values[1] - values[0] * values[0]) * (values[1] - values[0] * values[0]) +
                (1 - values[1]) * (1 - values[1]);
    }

    @Override
    public double[] gradient(double[] values) {
        return new double[]{400 * values[0] * (values[0] * values[0] - values[1]),
                -200 * values[0] * values[0] + 202 * values[1] - 2};
    }

    @Override
    public double[][] hessian(double[] values) {
        return new double[][]{
                {800 * values[0] * values[0] - 400 * (values[1] - values[0] * values[0]), -400 * values[0]},
                {-400 * values[0], 202}
        };
    }
}
