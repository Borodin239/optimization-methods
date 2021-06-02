package lab4.optimizations;

import lab4.BaseOperations;
import lab4.functions.FunctionInfo;

public interface Optimization {
    BaseOperations baseOperations = new BaseOperations();

    double[] solve (double[] values, FunctionInfo function, double epsilon);
}
