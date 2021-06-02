package lab4;

import lab4.optimizations.NewtonOptimization;
import lab4.functions.FunctionInfo;
import lab4.functions.ZeroFunction;
import lab4.optimizations.NewtonWithUnidimensionalOptimization;
import lab4.optimizations.Optimization;

public class Main {
    public static void main(String[] args) {
        test(new ZeroFunction(), new NewtonOptimization(), new double[]{4, 1});
        test(new ZeroFunction(), new NewtonWithUnidimensionalOptimization(), new double[]{4, 1});
    }

    private static void test(FunctionInfo function, Optimization optimization, double[] startPoint) {
        final double[] x = optimization.solve(startPoint, function, 0.0000001);
        System.out.print("Vector x: ");
        for (double v : x) {
            System.out.print(v + " ");
        }
        System.out.println();
        System.out.println("y = " + function.apply(x));
    }
}
