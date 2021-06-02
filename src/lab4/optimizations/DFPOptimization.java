package lab4.optimizations;

import lab4.functions.FunctionInfo;
import lab4.utils.GoldenSectionSearch;
import lab4.utils.Result;

import java.util.Arrays;

public class DFPOptimization implements Optimization {

    @Override
    public Result solve(final double[] values, final FunctionInfo function, final double epsilon) {
        int iterations = 0;
        double[] x1 = values;
        double[][] G = baseOperations.identityMatrix(values.length);
        double[] w1 = baseOperations.negateVector(function.gradient(x1));
        double[] p = Arrays.copyOf(w1, w1.length);
        GoldenSectionSearch goldenSectionSearch = new GoldenSectionSearch();
        double r = goldenSectionSearch.getOptimization(-20, 20, epsilon, function, x1, p);
        double[] x2 = baseOperations.vectorSum(x1, baseOperations.multiplyVectorOnNumber(p, r));
        double[] deltaX = baseOperations.vectorSub(x2, x1);
        x1 = Arrays.copyOf(x2, x2.length);
        while (true) {
            iterations++;
            double[] w2 = baseOperations.negateVector(function.gradient(x1));
            double[] deltaW = baseOperations.vectorSub(w2, w1);
            w1 = Arrays.copyOf(w2, w2.length);
            double[] vk = baseOperations.multiplyMatrixOnVector(G, deltaW);
            G = baseOperations.diagonalMinus(G, baseOperations.scalar(deltaX, deltaX) /
                    baseOperations.scalar(deltaX, deltaW) + baseOperations.scalar(vk, vk) /
                    baseOperations.scalar(vk, deltaW));
            p = baseOperations.multiplyMatrixOnVector(G, w2);
            r = goldenSectionSearch.getOptimization(-20, 20, epsilon, function, x1, p);
            x2 = baseOperations.vectorSum(x1, baseOperations.multiplyVectorOnNumber(p, r));
            deltaX = baseOperations.vectorSub(x2, x1);
            x1 = Arrays.copyOf(x2, x2.length);
            if (baseOperations.euclideanNorm(deltaX) <= epsilon) {
                return new Result(x2, iterations);
            }
        }
    }
}
