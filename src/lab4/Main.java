package lab4;

import lab4.functions.FirstFunction;
import lab4.functions.NotQuadraticFunction;
import lab4.optimizations.NewtonDescentDirectionOptimization;
import lab4.optimizations.NewtonOptimization;
import lab4.functions.FunctionInfo;
import lab4.functions.ZeroFunction;
import lab4.optimizations.NewtonWithUnidimensionalOptimization;
import lab4.optimizations.Optimization;
import lab4.utils.Result;

public class Main {
    public static void main(String[] args) {
        runFunction(new ZeroFunction(), new double[]{4, 1}, "(x_1)^2 + (x_2)^2 - 1.2 * x_1 * x_2");
        runFunction(new FirstFunction(), new double[]{-1.2, 1}, "");
        runFunction(new NotQuadraticFunction(), new double[]{4, -2, 3},
                "x1*x1*x1*x1 + x2*x2 + x3 * x3 - 12*x2*x3+17");
    }

    private static void runFunction(final FunctionInfo function, final double[] startPoint, final String functionName) {
        System.out.printf("============ TEST %s ============\n", functionName);
        test(function, new NewtonOptimization(), startPoint);
        test(function, new NewtonWithUnidimensionalOptimization(), startPoint);
        test(function, new NewtonDescentDirectionOptimization(), startPoint);
    }

    private static void test(final FunctionInfo function, final Optimization optimization, final double[] startPoint) {
        final Result result = optimization.solve(startPoint, function, 0.0000001);
        System.out.print("Vector x: ");
        for (double v : result.getX()) {
            System.out.print(v + " ");
        }
        System.out.println();
        System.out.println("y = " + function.apply(result.getX()));
        System.out.println("Iterations = " + result.getIterations());
        System.out.println();
    }
}
