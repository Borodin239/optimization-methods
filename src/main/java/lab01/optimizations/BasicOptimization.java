package lab01.optimizations;

import java.util.ArrayList;

public abstract class BasicOptimization implements UnaryOptimization{
    ArrayList<Iteration> optimizationResult = new ArrayList<>();

    boolean checkIteration(double l, double r) {
        if (optimizationResult.isEmpty()) {
            return true;
        }
        Iteration lastIteration = optimizationResult.get(optimizationResult.size() - 1);
        return lastIteration.getL() != l || lastIteration.getR() != r;
    }
}
