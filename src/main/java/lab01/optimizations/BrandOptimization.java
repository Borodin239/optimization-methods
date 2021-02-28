package lab01.optimizations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static java.lang.Math.*;

public class BrandOptimization extends BasicOptimization {

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
        optimizationResult = new ArrayList<>();
        double K = (3 - sqrt(5)) / 2;
        double x, w, v, u = 0, fx, fw, fv;
        x = w = v = (l + r) / 2;
        fx = fv = fw = formula.apply(x);
        double step, prevStep; // длины текущего и предыдущего шагов
        step = prevStep = r - l;
        double prevR = r, prevL = l;
        while (abs(r - l) > epsilon && checkIteration(prevL, prevR)) {
            prevL = l;
            prevR = r;
            double g = prevStep;
            prevStep = step;
            boolean check = true;
            if (notEquals(x, w, v) && notEquals(fx, fw, fv)) {
                u = ParabolicMethod.makeOneIteration(x, w, v, formula);
                if (u >= (l + epsilon) && u <= (r - epsilon) && abs(u - x) < g / 2) {
                    step = abs(u - x);
                    check = false;
                }
            }
            if (check) {
                if (x < (r - l) / 2) {
                    u = x + K * (r - x);
                    step = r - x;
                } else {
                    u = x - K * (x - l);
                    step = x - l;
                }
            }
            if (abs(u - x) < epsilon) {
                u = x + sign(u - x) * epsilon;
            }
            double fu = formula.apply(u);
            if (fu < fx) {
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
                } else if (fu < fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }
            optimizationResult.add(new Iteration(l, r));
        }
        return optimizationResult;
    }
}
