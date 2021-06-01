package lab4;

import lab4.functions.ZeroFunction;

public class Main {
    public static void main(String[] args) {
        double[] start = new double[]{4, 1};
        NewtonOptimization newtonOptimization = new NewtonOptimization();
        ZeroFunction zeroFunction = new ZeroFunction();
        final double[] x = newtonOptimization.solve(start, zeroFunction);
        for (double v : x) {
            System.out.print(v + " ");
        }
        System.out.println();
        System.out.println("y = " + zeroFunction.apply(x));
    }
}
