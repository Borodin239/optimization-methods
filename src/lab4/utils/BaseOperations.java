package lab4.utils;

public class BaseOperations {

    public double[] negateVector(double[] vector) {
        double[] res = new double[vector.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = -vector[i];
        }
        return res;
    }

    /**
     * Operation values:
     * 1 -- addition
     * 2 -- subtraction
     * 3 -- multiplication
     */
    public double[] vectorSamePart(int operation, final double[] first, final double[] second) {
        if (first.length != second.length) {
            System.err.println("Vectors should have the same size");
            return null;
        }

        double[] res = new double[first.length];
        for (int i = 0; i < first.length; i++) {
            res[i] = first[i];
            switch (operation) {
                case 1:
                    res[i] += second[i];
                    break;
                case 2:
                    res[i] -= second[i];
                    break;
                case 3:
                    res[i] *= second[i];
            }
        }
        return res;
    }

    public double[] vectorSum(final double[] first, final double[] second) {
        return vectorSamePart(1, first, second);
    }

    public double[] vectorSub(final double[] first, final double[] second) {
        return vectorSamePart(2, first, second);
    }

    public double scalar(final double[] first, final double[] second) {
        double[] sum = vectorSamePart(3, first, second);
        double res = 0;
        for (double s : sum) {
            res += s;
        }
        return res;
    }

    public double[] multiplyVectorOnNumber(final double[] vector, final double number) {
        double[] res = new double[vector.length];
        for (int index = 0; index < vector.length; index++) {
            res[index] = vector[index] * number;
        }
        return res;
    }

    public double euclideanNorm(final double[] vector) {
        double sum = 0;
        for (double num : vector) {
            sum += num * num;
        }
        return Math.sqrt(sum);
    }

    public double[][] identityMatrix(final int size) {
        return diagonalMatrix(size, 1);
    }

    public double[][] diagonalMatrix(final int size, final int value) {
        double[][] res = new double[size][size];
        for (int i = 0; i < size; i++) {
            res[i][i] = value;
        }
        return res;
    }

    public double[][] transpose(final double[][] matrix) {
        double[][] res = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = matrix[j][i];
            }
        }
        return res;
    }

    public double[][] multiplyMatrixOnNumber(double[][] matrix, double number) {
        double[][] res = new double[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            res[i] = multiplyVectorOnNumber(matrix[i], number);
        }
        return res;
    }

    public double[][] multiplyMatrixOnMatrix(final double[][] first, final double[][] second) {
        double[][] ans = new double[first.length][second[0].length];
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second[0].length; j++) {
                for (int k = 0; k < second.length; k++) {
                    ans[i][j] += first[i][k] * second[k][j];
                }
            }
        }
        return ans;
    }

    public double[][] matrixSum(final double[][] first, final double[][] second) {
        double[][] res = new double[first.length][];
        for (int i = 0; i < first.length; i++) {
            res[i] = vectorSum(first[i], second[i]);
        }
        return res;
    }

    public double[][] matrixSub(final double[][] first, final double[][] second) {
        double[][] res = new double[first.length][];
        for (int i = 0; i < first.length; i++) {
            res[i] = vectorSub(first[i], second[i]);
        }
        return res;
    }

    public double[] multiplyMatrixOnVector(final double[][] matrix, final double[] vector) {
        double[] ans = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                ans[i] += vector[j] * matrix[i][j];
            }
        }
        return ans;
    }
}
