package lab01.optimizations;

import java.util.Collections;
import java.util.List;

public class Iteration {
    private double l, r;
    private List<Dot> dots = Collections.emptyList();

    public Iteration(double l, double r) {
        this.l = l;
        this.r = r;
    }

    public Iteration(double l, double r, List<Dot> dots) {
        this.l = l;
        this.r = r;
        this.dots = dots;
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

    public List<Dot> getDots() {
        return dots;
    }

    public void setDots(List<Dot> dots) {
        this.dots = dots;
    }
}
