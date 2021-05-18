package lab3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Generator {

    private static int generateElement() {
        return (int) (Math.random() * 5) * -1;
    }

    private ProfileMatrix generateThirdMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double zn = i + j + 1;
                matrix[i][j] = 1 / zn;
            }
        }
        // TODO:: сделать норм
        return new ProfileMatrix(new double[1], new double[1], new double[1], new int[1]);
    }

    private ProfileMatrix generateSecondMatrix(int n, int k) {
        final int[] ia = new int[n + 1];
        ia[0] = 0;
        for (int i = 1; i < n + 1; i++) {
            ia[i] = ia[i - 1] + (int) (Math.random() * (i + 1));
        }
        int length = ia[ia.length - 1];
        final double[] al = new double[length];
        final double[] au = new double[length];
        for (int i = 0; i < length; i++) {
            al[i] = generateElement();
            au[i] = generateElement();
        }
        final double[] di = new double[n];
        ProfileMatrix matrix = new ProfileMatrix(di, al, au, ia);
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double element = matrix.get(i, j);
                    sum -= element;
                }
            }
            if (i == 0) {
                double res = 1;
                for (int j = 0; j < k; j++) {
                    res *= 0.1;
                }
                sum += res;
            }
            di[i] = sum;
        }
        checkDi(di);
        matrix.di = di;
        return matrix;
    }

    private void checkDi(double[] di) {
        for (int i = 0; i < di.length; i++) {
            if (di[i] == 0) {
                di[i] = 1;
            }
        }
    }

    private double[] multiplyMatrixOnX(ProfileMatrix matrix) {
        int n = matrix.di.length;
        double[] f = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                f[i] += (j + 1) * matrix.get(i, j);
            }
        }
        return f;
    }

    // di -- главная диагональ матрицы
    // al -- элементы нижнего треугольника
    // au -- элементы верхнего треугольника
    // ia -- профиль матрицы (одинаков для столбцов и строк)
    private void printMatrix(ProfileMatrix matrix, boolean isSecond, int n, int k) {
        final String pathPrefix = isSecond ? "src/lab3/matrices/secondTask/n_" + n + "/k_" + k + "/" :
                "src/lab3/matrices/thirdTask/n_" + n + "/";
        double[] f = multiplyMatrixOnX(matrix);
        final Path diPath = Paths.get(pathPrefix + "di.txt");
        final Path alPath = Paths.get(pathPrefix + "al.txt");
        final Path auPath = Paths.get(pathPrefix + "au.txt");
        final Path iaPath = Paths.get(pathPrefix + "ia.txt");
        final Path fPath = Paths.get(pathPrefix + "f.txt");
        try {
            Files.createDirectories(diPath.getParent());
            Files.createFile(diPath);
            Files.createFile(alPath);
            Files.createFile(auPath);
            Files.createFile(iaPath);
            Files.createFile(fPath);
        } catch (final IOException exception) {
            System.err.println(exception.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(iaPath)) {
            for (int element : matrix.ial) {
                writer.write(element + " ");
            }
        } catch (final IOException exception) {
            System.err.println(exception.getMessage());
        }
        writeElements(matrix.di, diPath);
        writeElements(matrix.al, alPath);
        writeElements(matrix.au, auPath);
        writeElements(f, fPath);
    }

    private void writeElements(double[] elements, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (double element : elements) {
                writer.write(element + " ");
            }
        } catch (final IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    void generateAll(boolean isSecond) {
        // Перебор различных размерностей
        for (int n = 15; n < 800; n += 50) {
            // Перебор точности числа double
            if (isSecond) {
                for (int k = 0; k <= 15; k++) {
                    ProfileMatrix matrix = generateSecondMatrix(n, k);
                    printMatrix(matrix, true, n, k);
                }
            } else {
                ProfileMatrix matrix = generateThirdMatrix(n);
                printMatrix(matrix, false, n, -1);
            }
        }
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        generator.generateAll(true);
        //generator.generateAll(false);
    }
}
