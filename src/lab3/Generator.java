package lab3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Generator {

    private static int generateElement() {
        return (int) (Math.random() * 5) * -1;
    }

    private double[][] generateThirdMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double zn = i + j + 1;
                matrix[i][j] = 1 / zn;
            }
        }
        return matrix;
    }

    private double[][] generateSecondMatrix(int n, int k) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int element = generateElement();
                    sum += element;
                    matrix[i][j] = element;
                }
            }
            if (i == 0) {
                matrix[i][i] = -sum;
                double pow = 1;
                for (int p = 0; p < k; p++) {
                    pow *= 0.1;
                }
                matrix[i][i] -= pow * (n - 1);
            } else {
                matrix[i][i] = -sum;
            }
        }
        return matrix;
    }

    private double[] multiplyMatrixOnX(double[][] matrix) {
        int n = matrix.length;
        double[] f = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                f[i] += (j + 1) * matrix[i][j];
            }
        }
        return f;
    }

    private void printMatrix(double[][] matrix, boolean isSecond, int n, int k) {
        double[] f = multiplyMatrixOnX(matrix);
        String sPath = isSecond ? "src/lab3/matrices" + "/secondTask" + "/k" + k + "/n" + n + ".txt" :
                                   "src/lab3/matrices" + "/thirdTask" + "/n" + n + ".txt";
        Path path = Paths.get(sPath);
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
           System.err.println(e);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(String.valueOf(n));
            writer.newLine();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.write("\n");
            }
            writer.write("\n");
            for (int i = 0; i < n; i++) {
                writer.write(f[i] + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void generateAll(boolean isSecond) {
        // Перебор различных размерностей
        for (int n = 15; n < 800; n += 50) {
            // Перебор точности числа double
            if (isSecond) {
                for (int k = 0; k <= 15; k ++) {
                    double[][] matrix = generateSecondMatrix(n, k);
                    printMatrix(matrix, true, n, k);
                }
            } else {
                double[][] matrix = generateThirdMatrix(n);
                printMatrix(matrix, false, n, -1);
            }
        }
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        generator.generateAll(true);
        generator.generateAll(false);
    }
}
