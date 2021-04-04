import optimization.*;

public class Main {

    private static final UnaryOperatorCnt formula = new UnaryOperatorCnt();
//    private static final UnaryOperator<Double> formula5 = (x) -> 10 * x * Math.log(x) - (x * x) / 2;

    public static void main(String[] args) {
        for (int d = 1; d < 15; d++) {
            UnaryOptimization up = new DichotomyMethod();
            up.getOptimization(5, 10, Math.pow(0.1, d), formula);
            System.out.print(d + ";" + formula.opNum + ";");
            formula.opNum = 0;
            up = new FibonacciMethod();
            up.getOptimization(5, 10, Math.pow(0.1, d), formula);
            System.out.print(formula.opNum + ";");
            formula.opNum = 0;
            up = new GoldenSectionSearch();
            up.getOptimization(5, 10, Math.pow(0.1, d), formula);
            System.out.print(formula.opNum + ";");
            formula.opNum = 0;
            up = new ParabolicMethod();
            up.getOptimization(5, 10, Math.pow(0.1, d), formula);
            System.out.print(formula.opNum + ";");
            formula.opNum = 0;
            up = new BrandOptimization();
            up.getOptimization(5, 10, Math.pow(0.1, d), formula);
            System.out.println(d * 13 / 3);
            formula.opNum = 0;
        }
    }
}
