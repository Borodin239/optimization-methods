package lab4.optimizations;

import lab3.ProfileMatrix;
import lab4.functions.FunctionInfo;
import lab4.utils.Result;

public class NewtonOptimization implements Optimization {

    public Result solve(final double[] values, final FunctionInfo function, final double epsilon) {
        double[] x = values;
        int iterations = 0;
        while (true) {
            iterations++;
            double[] gradient = function.gradient(x);
            double[][] hessian = function.hessian(x);
            ProfileMatrix profileMatrix = new ProfileMatrix(hessian);
            double[] s = profileMatrix.solveByLU(baseOperations.negateVector(gradient));
            x = baseOperations.vectorSum(x, s);
            if (baseOperations.euclideanNorm(s) <= epsilon) {
                return new Result(x, iterations);
            }
        }
    }
}
