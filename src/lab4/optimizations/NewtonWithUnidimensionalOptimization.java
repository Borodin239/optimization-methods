package lab4.optimizations;

import lab3.ProfileMatrix;
import lab4.functions.FunctionInfo;
import lab4.utils.GoldenSectionSearch;
import lab4.utils.Result;

public class NewtonWithUnidimensionalOptimization implements Optimization{

    @Override
    public Result solve(final double[] values, final FunctionInfo function, final double epsilon) {
        double[] x = values;
        ProfileMatrix profileMatrix;
        GoldenSectionSearch goldenSectionSearch = new GoldenSectionSearch();
        int iterations = 0;
        while (true) {
            iterations++;
            double[] gradient = function.gradient(x);
            double[][] hessian = function.hessian(x);
            profileMatrix = new ProfileMatrix(hessian);
            double[] d = profileMatrix.solveByLU(baseOperations.negateVector(gradient));
            // TODO:: а что с выбором границ?
            double r = goldenSectionSearch.getOptimization(-20, 20, epsilon, function, x, d);
            double[] s = baseOperations.multiplyVectorOnNumber(d, r);
            x = baseOperations.vectorSum(x, s);
            if (baseOperations.euclideanNorm(s) <= epsilon) {
                return new Result(x, iterations);
            }
        }
    }
}
