package lab4.optimizations;

import lab4.utils.BaseOperations;
import lab4.functions.FunctionInfo;
import lab4.utils.Result;

public interface Optimization {
    BaseOperations baseOperations = new BaseOperations();

    Result solve (double[] values, FunctionInfo function, double epsilon);
}
