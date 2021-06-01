package lab4.functions;

public interface FunctionInfo {
    double apply(double[] values);

    double[][] hessian(double[] values);

    double[] gradient(double[] values);
}
