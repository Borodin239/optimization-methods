package lab3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Generator {

    private static int generateElement(boolean withZero) {
        int res = (int) (Math.random() * 5) * -1;
        return withZero ? res : res == 0 ? -1 : res;
    }

    private int[] generateIa(int n) {
        final int[] ia = new int[n + 1];
        ia[0] = 0;
        ia[1] = 0;
        for (int i = 2; i < n + 1; i++) {
            ia[i] = ia[i - 1] + (int) (Math.random() * (i));
        }
        return ia;
    }

    private int[] generateThirdIa(int n) {
        final int[] ia = new int[n + 1];
        ia[0] = 0;
        ia[1] = 0;
        for (int i = 2; i < n + 1; i++) {
            ia[i] = ia[i - 1] + i - 1;
        }
        return ia;
    }

    private CRSMatrix generateFifthMatrix(int n) {
        final int[] ia = generateIa(n);
        int length = ia[ia.length - 1];
        final int[] ja = new int[length];
        final double[] al = new double[length];
        final double[] au = new double[length];
        for (int i = 0; i < length; i++) {
            al[i] = generateElement(false);
            au[i] = generateElement(false);
        }
        int row = 0;
        int ind = 0;
        List<Integer> positions = new ArrayList<>();
        // Generate positions of non-empty elements
        for (int i = 0; i < n; i++) {
            while (row < ia.length && ia[row] < i) {
                row++;
            }
            int rowSize = ia[i + 1] - ia[i];
            Collections.shuffle(positions);
            for (int j = 0; j < rowSize; j++, ind++) {
                ja[ind] = positions.get(j);
            }
            positions.add(row);
        }
        final double[] di = new double[n];
        CRSMatrix matrix = new CRSMatrix(di, al, au, ja, ia);
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double element = matrix.get(i, j);
                    sum -= element;
                }
            }
            if (i == 0) {
                sum += 1;
            }
            di[i] = sum;
        }
        checkDi(di);
        matrix.di = di;
        return matrix;
    }

    private ProfileMatrix generateThirdMatrix(int n) {
        final int[] ia = generateThirdIa(n);
        int length = ia[ia.length - 1];
        final double[] al = new double[length];
        final double[] au = new double[length];
        final double[] di = new double[n];
        ProfileMatrix matrix = new ProfileMatrix(di, al, au, ia);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix.set(i, j, 1 / (double) (i + j + 1));
            }
        }
        return matrix;
    }

    private ProfileMatrix generateSecondMatrix(int n) {
        final int[] ia = generateIa(n);
        int length = ia[ia.length - 1];
        final double[] al = new double[length];
        final double[] au = new double[length];
        for (int i = 0; i < length; i++) {
            al[i] = generateElement(true);
            au[i] = generateElement(true);
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

    private double[] multiplyMatrixOnX(Matrix matrix) {
        int n = matrix.size();
        double[] f = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                f[i] += (j + 1) * matrix.get(i, j);
            }
        }
        return f;
    }

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
        writeDoubleElements(matrix.di, diPath);
        writeDoubleElements(matrix.al, alPath);
        writeDoubleElements(matrix.au, auPath);
        writeDoubleElements(f, fPath);
    }

    private void printCRSMatrix(CRSMatrix matrix, int n) {
        final String pathPrefix = "src/lab3/matrices/fifthTask/n_" + n + "/";
        double[] f = multiplyMatrixOnX(matrix);
        final Path diPath = Paths.get(pathPrefix + "di.txt");
        final Path alPath = Paths.get(pathPrefix + "al.txt");
        final Path auPath = Paths.get(pathPrefix + "au.txt");
        final Path jaPath = Paths.get(pathPrefix + "ja.txt");
        final Path iaPath = Paths.get(pathPrefix + "ia.txt");
        final Path fPath = Paths.get(pathPrefix + "f.txt");
        try {
            Files.createDirectories(diPath.getParent());
            Files.createFile(diPath);
            Files.createFile(alPath);
            Files.createFile(auPath);
            Files.createFile(jaPath);
            Files.createFile(iaPath);
            Files.createFile(fPath);
        } catch (final IOException exception) {
            System.err.println(exception.getMessage());
        }

        writeIntegerElements(matrix.ia, iaPath);
        writeIntegerElements(matrix.ja, jaPath);
        writeDoubleElements(matrix.di, diPath);
        writeDoubleElements(matrix.al, alPath);
        writeDoubleElements(matrix.au, auPath);
        writeDoubleElements(f, fPath);
    }

    private void writeDoubleElements(double[] elements, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (double element : elements) {
                writer.write(element + " ");
            }
        } catch (final IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    private void writeIntegerElements(int[] elements, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int element : elements) {
                writer.write(element + " ");
            }
        } catch (final IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    void generateAll(final int number) {
        // Перебор различных размерностей
        ProfileMatrix matrix;
        for (int n = 15; n < 1000; n += 50) {
            switch (number) {
                case (2):
                    // Перебор точности числа double
                    double add = 1;
                    matrix = generateSecondMatrix(n);
                    for (int k = 0; k <= 10; k++) {
                        matrix.di[0] += add;
                        printMatrix(matrix, true, n, k);
                        matrix.di[0] -= add;
                        add *= 0.1;
                    }
                    break;
                case (3):
                    matrix = generateThirdMatrix(n);
                    printMatrix(matrix, false, n, -1);
                    break;
                case (5):
                    CRSMatrix crsMatrix = generateFifthMatrix(n);
                    printCRSMatrix(crsMatrix, n);
                    break;
                default:
                    throw new IllegalArgumentException("Number of task should be 2, 3 or 5, but you" +
                            " print " + number);
            }
        }
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        generator.generateAll(2);
        //generator.generateAll(3);
        //generator.generateAll(5);
    }
}
