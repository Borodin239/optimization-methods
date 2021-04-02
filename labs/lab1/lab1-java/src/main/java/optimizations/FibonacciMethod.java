package optimizations;

import tools.Iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class FibonacciMethod implements UnaryOptimization {
    private final List<Double> fibonacciNumbers = new ArrayList<>();

    private void getFibonacciNumbers(double l, double r, double epsilon) {
        fibonacciNumbers.add(1.0);
        fibonacciNumbers.add(1.0);
        int pos = 2;
        while (fibonacciNumbers.get(pos - 1) < (r - l) / epsilon) {
            fibonacciNumbers.add(fibonacciNumbers.get(pos - 1) + fibonacciNumbers.get(pos - 2));
            pos++;
        }
    }

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> optimizationResult = new ArrayList<>();
        optimizationResult.add(new Iteration(l, r));

        fibonacciNumbers.clear();
        getFibonacciNumbers(l, r, epsilon);
        final int n = fibonacciNumbers.size() - 3;
        final double l_0 = l;
        final double r_0 = r;
        double x_1 = 0, x_2 = 0, y_1 = 0, y_2 = 0;
        boolean isX_1Set = false;
        boolean isX_2Set = false;

        for (int k = 1; k <= n; k++) {
            x_1 = isX_1Set ? x_1 : l + fibonacciNumbers.get(n - k + 1) / fibonacciNumbers.get(n + 2) * (r_0 - l_0);
            x_2 = isX_2Set ? x_2 : l + fibonacciNumbers.get(n - k + 2) / fibonacciNumbers.get(n + 2) * (r_0 - l_0);
            y_1 = isX_1Set ? y_1 : formula.apply(x_1);
            y_2 = isX_2Set ? y_2 : formula.apply(x_2);

            if (y_1 <= y_2) {
                r = x_2;
                x_2 = x_1;
                y_2 = y_1;
                isX_2Set = true;
                isX_1Set = false;
            } else {
                l = x_1;
                x_1 = x_2;
                y_1 = y_2;
                isX_1Set = true;
                isX_2Set = false;
            }
            optimizationResult.add(new Iteration(l, r));
        }
        return optimizationResult;
    }
}
