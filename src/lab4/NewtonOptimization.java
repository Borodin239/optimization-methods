package lab4;

import lab3.ProfileMatrix;
import lab4.functions.FunctionInfo;

public class NewtonOptimization {
    double EPSILON = 0.0000001;
    BaseOperations baseOperations = new BaseOperations();

    public double[] solve(double[] values, FunctionInfo function) {
        double[] x = values;
        while (true) {
            double[] gradient = function.gradient(x);
            double[][] hessian = function.hessian(x);
            ProfileMatrix profileMatrix = new ProfileMatrix(hessian);
            double[] s = profileMatrix.solveByLU(baseOperations.negateVector(gradient));
            x = baseOperations.vectorSum(x, s);
            if (baseOperations.euclideanNorm(s) <= EPSILON) {
                return x;
            }
        }
    }
}
