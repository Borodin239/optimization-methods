package lab4;

import lab4.functions.FirstFunction;
import lab4.optimizations.NewtonDescentDirectionOptimization;
import lab4.optimizations.NewtonOptimization;
import lab4.functions.FunctionInfo;
import lab4.functions.ZeroFunction;
import lab4.optimizations.NewtonWithUnidimensionalOptimization;
import lab4.optimizations.Optimization;

public class Main {
    public static void main(String[] args) {
        runFunction(new ZeroFunction(), new double[]{4, 1}, "(x_1)^2 + (x_2)^2 - 1.2 * x_1 * x_2");
        runFunction(new FirstFunction(), new double[]{-1.2, 1}, "");
    }

    private static void runFunction(final FunctionInfo function, final double[] startPoint, final String functionName) {
        System.out.printf("============ TEST %s ============\n", functionName);
        test(function, new NewtonOptimization(), startPoint);
        test(function, new NewtonWithUnidimensionalOptimization(), startPoint);
        test(function, new NewtonDescentDirectionOptimization(), startPoint);
    }

    private static void test(final FunctionInfo function, final Optimization optimization, final double[] startPoint) {
        final double[] x = optimization.solve(startPoint, function, 0.0000001);
        System.out.print("Vector x: ");
        for (double v : x) {
            System.out.print(v + " ");
        }
        System.out.println();
        System.out.println("y = " + function.apply(x));
    }
}
