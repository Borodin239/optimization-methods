package optimization.gradient;

import optimization.Iteration;
import optimization.QuadraticForm;

import java.util.List;

public interface Gradient {

    List<Iteration> getOptimization(QuadraticForm form, double epsilon, double ... start);
}
