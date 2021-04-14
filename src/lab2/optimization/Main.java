package lab2.optimization;

import lab2.optimization.gradient.*;
import org.la4j.LinearAlgebra;
import org.la4j.Matrices;
import org.la4j.vector.dense.BasicVector;

import java.util.List;

public class Main {

    private static void printI(List<Iteration> res, int i, double x) {
        System.out.print("[" + x + ",");
        for (int j = 0; j < res.size() - 1; j++) {
            System.out.print(res.get(j).getX().get(i) + ",");
        }
        System.out.print(res.get(res.size() - 1).getX().get(i));
        System.out.println("];");
    }

    public static void main(String[] args) {
        QuadraticForm form = new QuadraticForm(new double[][]{
                {1, 0},
                {0, 4},
        }, new double[] {4, 2});
        double x = 10, y = -10;
        Gradient opt = new ConjugateGradient();
        List<Iteration> res = opt.getOptimization(form, 0.001, x, y);

        /// 22222222222222222222222222222222222
        System.out.print("a = ");
        printI(res, 0, x);
        System.out.print("b = ");
        printI(res, 1, y);
//
//        System.out.println("x" + "\t\t\t\t" + "y");
//        System.out.format(0 + "\t%03f\t\t%03f\n", 10.0, 10.0);
//        for (int i = 0; i < res.size(); i++) {
//            System.out.format(i + 1 + "\t%03f\t\t%03f\n", res.get(i).getX().get(0),
//                    res.get(i).getX().get(1));
//        }
//        System.out.println(form.getLevel(new BasicVector(new double[]{1, 2}), 0.01, 3000));
    }


}
