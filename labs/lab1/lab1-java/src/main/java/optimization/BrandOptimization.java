package optimization;

import tools.Iteration;
import tools.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class BrandOptimization implements UnaryOptimization {

    private boolean notEquals(double first, double second, double third) {
        return first != second && third != first && third != second;
    }

    private int sign(double number) {
        if (number >= 0) {
            return 1;
        }
        return -1;
    }

    @Override
    public List<Iteration> getOptimization(double l, double r, double epsilon, UnaryOperator<Double> formula) {
        List<Iteration> optimizationResult = new ArrayList<>();
        optimizationResult.add(new Iteration(l, r));
        double K = (3 - sqrt(5)) / 2;
        double x, w, v, u = 0, fx, fw, fv;
        x = w = v = K * (r - l) + l;
        fx = fv = fw = formula.apply(x);
        double step, prevStep; // длины текущего и предыдущего шагов
        step = prevStep = r - l;
        while (abs(r - l) > epsilon) {
            double g = prevStep;
            prevStep = step;
            double delta = epsilon * abs(x) + epsilon / 10;
            if (abs(x - (l + r) / 2) + (r - l) / 2 <= 2 * delta) {
                break;
            }
            boolean check = true;
            if (notEquals(x, w, v) && notEquals(fx, fw, fv)) {
                u = ParabolicMethod.makeOneIteration(new Triple(x, w, v), formula).getX_2();
                if (u >= l && u <= r && abs(u - x) < g / 2) {
                    if (u - l < 2 * delta || r - u < 2 * delta) {
                        u = x - sign(x - (l + r) / 2) * delta;
                    }
                    check = false;
                }
            }
            if (check) {
                if (x < (r + l) / 2) {
                    u = x + K * (r - x);
                    prevStep = r - x;
                } else {
                    u = x - K * (x - l);
                    prevStep = x - l;
                }
            }
            if (abs(u - x) < delta) {
                u = x + sign(u - x) * delta;
            }
            step = abs(u - x);
            double fu = formula.apply(u);
            if (fu <= fx) {
                if (u >= x) {
                    l = x;
                } else {
                    r = x;
                }
                v = w;
                w = x;
                x = u;
                fv = fw;
                fw = fx;
                fx = fu;
            } else {
                if (u >= x) {
                    r = u;
                } else {
                    l = u;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    w = u;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }
            optimizationResult.add(new Iteration(l, r));
        }
        return optimizationResult;
    }
}
