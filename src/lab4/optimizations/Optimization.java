package lab4.optimizations;

import lab4.functions.FunctionInfo;

public interface Optimization {
    double[] solve (double[] values, FunctionInfo function);
}
