package gradient;

public interface Gradient {

    Iteration[] getGradient(QuadraticForm form, double ... start);
}
