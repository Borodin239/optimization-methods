package lab4.optimizations;

import lab3.ProfileMatrix;
import lab4.functions.FunctionInfo;

public class NewtonOptimization implements Optimization {

    public double[] solve(final double[] values, final FunctionInfo function, final double epsilon) {
        double[] x = values;
        while (true) {
            double[] gradient = function.gradient(x);
            double[][] hessian = function.hessian(x);
            ProfileMatrix profileMatrix = new ProfileMatrix(hessian);
            double[] s = profileMatrix.solveByLU(baseOperations.negateVector(gradient));
            x = baseOperations.vectorSum(x, s);
            if (baseOperations.euclideanNorm(s) <= epsilon) {
                return x;
            }
        }
    }
}
