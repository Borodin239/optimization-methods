package lab3;

public class Main {
    public static void main(String[] args) {
        double[][] m = new double[][]{
                {1, 1, 1, 0},
                {1, 2, 3, 4},
                {0, 2, 1, 2},
                {0, 2, 2, 2}
        };
        ProfileMatrix profileMatrix = new ProfileMatrix(m);
        double[] b = profileMatrix.solveByLU(new double[]{1, 2, 3, 4});
        for (double num : b) {
            System.out.print(num + " ");
        }
        Generator generator = new Generator();
        generator.generateAll();
    }
}
