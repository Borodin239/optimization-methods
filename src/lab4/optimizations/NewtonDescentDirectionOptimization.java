package lab4.optimizations;

import lab3.ProfileMatrix;
import lab4.functions.FunctionInfo;
import lab4.utils.GoldenSectionSearch;
import lab4.utils.Result;

public class NewtonDescentDirectionOptimization implements Optimization {
    @Override
    public Result solve(final double[] values, final FunctionInfo function, final double epsilon) {
        int iterations = 0;
        double[] x = values;
        GoldenSectionSearch goldenSectionSearch = new GoldenSectionSearch();
        ProfileMatrix profileMatrix;
        double[] d = baseOperations.negateVector(function.gradient(x));
        double r = goldenSectionSearch.getOptimization(-10, 10, epsilon, function, x, d);
        double[] s = baseOperations.multiplyVectorOnNumber(d, r);
        x = baseOperations.vectorSum(x, s);
        while (true) {
            iterations++;
            double[] gradient = function.gradient(x);
            double[][] hessian = function.hessian(x);
            profileMatrix = new ProfileMatrix(hessian);
            s = profileMatrix.solveByLU(baseOperations.negateVector(gradient));
            if (baseOperations.scalar(s, gradient) < 0) {
                d = s;
            } else {
                d = baseOperations.negateVector(gradient);
            }
            r = goldenSectionSearch.getOptimization(-20, 20, epsilon, function, x, d);
            s = baseOperations.multiplyVectorOnNumber(d, r);
            x = baseOperations.vectorSum(x, s);
            if (baseOperations.euclideanNorm(s) <= epsilon) {
                return new Result(x, iterations);
            }
        }
    }
}
