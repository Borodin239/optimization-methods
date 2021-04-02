package optimizations;

import tools.Iteration;
import tools.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ParabolicMethod implements UnaryOptimization {

    public static Triple makeOneIteration(Triple res, UnaryOperator<Double> formula) {
        double x_1 = res.getX_1();
        double x_2 = res.getX_2();
        double x_3 = res.getX_3();

        Triple coefs = getCoeffs(res, formula);
        double currentIterationResult = - 0.5 * coefs.getX_2() / coefs.getX_1();

        if (x_1 < currentIterationResult && currentIterationResult < x_2 && x_2 < x_3) {
            if (formula.apply(currentIterationResult) >= formula.apply(x_2)) {
                x_1 = currentIterationResult;
            } else {
                x_3 = x_2;
                x_2 = currentIterationResult;
            }
        } else if (x_1 < x_2 && x_2 < currentIterationResult && currentIterationResult < x_3) {
            if (formula.apply(currentIterationResult) <= formula.apply(x_2)) {
                x_1 = x_2;
                x_2 = currentIterationResult;
            } else {
                x_3 = currentIterationResult;
            }
        }

        return new Triple(x_1, x_2, x_3);
    }

    private static Triple getCoeffs(Triple points, UnaryOperator<Double> formula) {
        double x_1 = points.getX_1();
        double x_2 = points.getX_2();
        double x_3 = points.getX_3();
        double y_1 = formula.apply(x_1);
        double y_2 = formula.apply(x_2);
        double y_3 = formula.apply(x_3);

        double a = (x_3 * (y_2 - y_1) + x_2 * (y_1 - y_3) + x_1 * (y_3 - y_2)) / ((x_1 - x_2) * (x_1 - x_3) * (x_2 - x_3));
        double b = (y_2 - y_1 - a * (x_2 * x_2 - x_1 * x_1)) / (x_2 - x_1);
        double c = y_1 - a * x_1 * x_1 - b * x_1;

        return new Triple(a, b, c);
    }

    private UnaryOperator<Double> parabolaFunction(Triple points, UnaryOperator<Double> formula) {
        Triple coeffs = getCoeffs(points, formula);

        return (x) -> coeffs.getX_1() * x * x + coeffs.getX_2() * x + coeffs.getX_3();
    }

    private double getMiddlePoint(double l, double r, UnaryOperator<Double> formula) {
        double y_l = formula.apply(l);
        double y_r = formula.apply(r);
        for (double i = l; i <= r; i += (r - l) / 100) {
            if (y_l > formula.apply(i) && formula.apply(i) < y_r) {
                return i;
            }
        }
        return (l + r) / 2;
    }

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> optimizationResult = new ArrayList<>();
        optimizationResult.add(new Iteration(l, r));

        Triple lastIterationResult = new Triple(l, getMiddlePoint(l ,r, formula), r);

        while (true) {
            Triple currentIterationResult = makeOneIteration(lastIterationResult, formula);

            optimizationResult.add(new Iteration(currentIterationResult.getX_1(), currentIterationResult.getX_3()));
            optimizationResult.get(optimizationResult.size() - 1).setParabola(parabolaFunction(currentIterationResult, formula));

            if (Math.abs(currentIterationResult.getX_2() - lastIterationResult.getX_2()) < epsilon) {
                optimizationResult.add(new Iteration(currentIterationResult.getX_2() - epsilon,
                                                     currentIterationResult.getX_2() + epsilon));
                break;
            }
            lastIterationResult = currentIterationResult;
        }

        return optimizationResult;
    }
}
