package tools;

import java.util.function.UnaryOperator;

public class Iteration {
    private double l, r;
    private UnaryOperator<Double> parabola;

    public Iteration(double l, double r) {
        this.l = l;
        this.r = r;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public UnaryOperator<Double> getParabola() {
        return parabola;
    }

    public void setParabola(UnaryOperator<Double> parabola) {
        this.parabola = parabola;
    }
}
