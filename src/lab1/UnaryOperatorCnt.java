package lab1;

import java.util.function.UnaryOperator;

public class UnaryOperatorCnt implements UnaryOperator<Double> {
    public int opNum = 0;
    @Override
    public Double apply(Double x) {
        opNum++;
        return (20 * x - 2.3) * (20 * x + 2.3) * (2 * x - 17) * (3 * x + 17);
    }
}
