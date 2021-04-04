package lab2.optimization.gradient;

import lab2.optimization.Iteration;
import lab2.optimization.QuadraticForm;

import java.util.List;

public interface Gradient {

    List<Iteration> getOptimization(QuadraticForm form, double epsilon, double ... start);
}
