package lab01.optimizations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class FibonacciMethod implements UnaryOptimization {

    private List<Double> getFibonacciNumbers(double l, double r, double epsilon) {
        List<Double> fibonacciNumbers = new ArrayList<>();
        fibonacciNumbers.add(1.0);
        fibonacciNumbers.add(1.0);
        int pos = 2;
        while (fibonacciNumbers.get(pos - 1) < (r - l) / epsilon) {
            fibonacciNumbers.add(fibonacciNumbers.get(pos - 1) + fibonacciNumbers.get(pos - 2));
            pos++;
        }
        return fibonacciNumbers;
    }

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> iterations = new ArrayList<>();
        final List<Double> fibonacciNumbers = getFibonacciNumbers(l, r, epsilon);
        final int n = fibonacciNumbers.size() - 2;

        for (int k = 1; k < n; k++) {
            double x_1 = l + fibonacciNumbers.get(n - k - 1) / fibonacciNumbers.get(n - k + 1) * (r - l);
            double x_2 = l + fibonacciNumbers.get(n - k) / fibonacciNumbers.get(n - k + 2) * (r - l);

            if (formula.apply(x_1) <= formula.apply(x_2)) {
                r = x_2;
            } else  {
                l = x_1;
            }

            iterations.add(new Iteration(l, r));
        }

        return iterations;
    }
}
