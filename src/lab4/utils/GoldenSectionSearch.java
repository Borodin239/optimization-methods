package lab4.utils;

import lab4.functions.FunctionInfo;

public class GoldenSectionSearch {
    private final double GOLDEN_SECTION = (1 + Math.sqrt(5)) / 2.0;
    private final BaseOperations base = new BaseOperations();

    public double getOptimization(double l, double r, double epsilon, FunctionInfo formula, double[] x, double[] d) {
        double lastIteration;
        double x1 = l + (r - l) / (GOLDEN_SECTION + 1);
        double x2 = r - (r - l) / (GOLDEN_SECTION + 1);
        double f1 = formula.apply(base.vectorSum(x, base.multiplyVectorOnNumber(d, x1)));
        double f2 = formula.apply(base.vectorSum(x, base.multiplyVectorOnNumber(d, x2)));
        do {
            if (f1 < f2) {
                r = x2;
                x2 = x1;
                f2 = f1;
                x1 = l + (r - l) / (GOLDEN_SECTION + 1);
                f1 = formula.apply(base.vectorSum(x, base.multiplyVectorOnNumber(d, x1)));
            } else {
                l = x1;
                x1 = x2;
                f1 = f2;
                x2 = r - (r - l) / (GOLDEN_SECTION + 1);
                f2 = formula.apply(base.vectorSum(x, base.multiplyVectorOnNumber(d, x2)));
            }
            lastIteration = (l + r) / 2;
        } while (Math.abs(r - l) > epsilon);
        return lastIteration;
    }
}
