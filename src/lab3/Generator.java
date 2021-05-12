package lab3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Generator {

    private static int generateElement() {
        return (int) (Math.random() * 5) * -1;
    }

    private double[][] generateThirdMatrix(int n, int k) {
        double[][] matrix = new double[n][n];

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

    void generateAll(boolean isSecond) {

        // Перебор различных размерностей
        for (int n = 15; n < 1000; n += 50) {
            // Перебор точности числа double
            for (int k = 0; k < 7; k++) {
                double[][] matrix;
                if (isSecond) {
                    matrix = generateSecondMatrix(n, k);
                } else {
                    matrix = generateThirdMatrix(n, k);
                }
                double[] f = multiplyMatrixOnX(matrix);
                String directory = isSecond ? "/secondTask" : "/HilbertMatrices";
                Path path = Paths.get("src/lab3/matrices" + directory + "/k" + k + "_n" + n + ".txt");
                try {
                    Files.createFile(path);
                } catch (IOException ignored) {
                    // do nothing
                }
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            writer.write(matrix[i][j] + " ");
                        }
                        writer.write("\n");
                    }
                    for (int i = 0; i < n; i++) {
                        writer.write(f[i] + " ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
