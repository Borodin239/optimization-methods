package lab3;


import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ProfileMatrix extends AbstractMatrix {
    /**
     * Elements of the lower and upper triangular matrices.
     */
    int[] ial, iau;


    /**
     * Creates matrix by reading all the necessary information
     * for 2 tasks from the files. It is enough to pass the
     * matrix dimension n and the power k.
     */
    public ProfileMatrix(int n, int k) {
        readFromFile(n, k);
    }

    /**
     * Creates matrix by reading all the necessary information
     * for third tasks from the files. It is enough to pass the
     * matrix dimension n.
     */
    public ProfileMatrix(int n) {
        readFromFile(n);
    }

    /**
     * Generates profile matrix from full matrix.
     *
     * @param m full matrix
     */
    public ProfileMatrix(double[][] m) {
        checkSize(m);
        int size = m.length;

        buildDi(m, size);
        buildIal(m, size);
        buildAl(m, size);
        buildIau(m, size);
        buildAu(m, size);
    }

    /**
     * Creates profile matrix with information from these arrays.
     *
     * @param di is diagonal elements of the matrix.
     * @param al is the elements of the lower triangle
     * @param au is the elements of the upper triangle.
     * @param ia is the matrix profile.
     */
    public ProfileMatrix(double[] di, double[] al, double[] au, int[] ia) {
        this.di = di;
        this.al = al;
        this.au = au;
        this.ial = ia;
        this.iau = ia;
    }

    /**
     * Reads matrix from files for third task.
     *
     * @param n matrix size.
     */
    public void readFromFile(int n) {
        readFromFile(
                n, "src/lab3/matrices/thirdTask/n_" + n + "/"
        );
    }

    /**
     * Reads matrix from files for second task.
     *
     * @param n matrix size.
     * @param k power of constant.
     */
    public void readFromFile(int n, int k) {
        readFromFile(
                n, "src/lab3/matrices/secondTask/n_" + n + "/k_" + k + "/"
        );
    }

    /**
     * Reads matrix from file.
     * @param n matrix size
     * @param path path where corresponding files lays
     */
    private void readFromFile(int n, String path) {
        di = readDoubleArrayFromFile(n, path + "di.txt");
        ial = readIntArrayFromFile(n + 1, path + "ia.txt");
        iau = ial;
        au = readDoubleArrayFromFile(ial[n], path + "au.txt");
        al = readDoubleArrayFromFile(ial[n], path + "al.txt");
    }

    /**
     * Returns is the selected element in matrix or not.
     *
     * @param i number of the row.
     * @param j number of the column.
     * @return is element in {@code al} or {@code au} arrays.
     */
    public boolean isInProfile(int i, int j) {
        if (i == j) {
            return true;
        } else if (i > j) {
            int start = i - (ial[i + 1] - ial[i]);
            return j >= start;
        } else {
            int start = j - (iau[j + 1] - iau[j]);
            return i >= start;
        }
    }

    /**
     * Returns first element in profile in line.
     * @param i line number
     * @return position of element
     */
    private int firstInProfileL(int i) {
        return i - (ial[i + 1] - ial[i]);
    }

    /**
     * Returns first element in profile in row.
     * @param i row number
     * @return position of element
     */
    private int firstInProfileU(int i) {
        return i - (iau[i + 1] - iau[i]);
    }

    /**
     * Check if matrix square.
     * @param m matrix
     * @throws IllegalArgumentException if not square
     */
    private void checkSize(double[][] m) {
        for (double[] arr : m) {
            if (m.length != arr.length) {
                throw new IllegalArgumentException("Not square matrix");
            }
        }
    }

    // build al from matrix
    private void buildAl(double[][] m, int size) {
        al = new double[ial[size]];
        for (int i = 0; i < size; i++) {
            int start = ial[i] + i - ial[i + 1];
            for (int j = 0; j < ial[i + 1] - ial[i]; j++) {
                al[ial[i] + j] = m[i][start + j];
            }
        }
    }

    // build au from matrix
    private void buildAu(double[][] m, int size) {
        au = new double[iau[size]];
        for (int i = 0; i < size; i++) {
            int start = iau[i] + i - iau[i + 1];
            for (int j = 0; j < iau[i + 1] - iau[i]; j++) {
                au[iau[i] + j] = m[start + j][i];
            }
        }
    }

    // build di from matrix
    private void buildDi(double[][] m, int size) {
        di = new double[size];
        for (int i = 0; i < size; i++) {
            di[i] = m[i][i];
        }
    }

    //build ail or aiu which specified by isBuildIal
    private int[] abstractAiBuild(double[][] m, int size, boolean isBuildIal) {
        int[] res = new int[size + 1];
        res[0] = 0;
        res[1] = 0;
        for (int i = 1; i < size; i++) {
            int start = 0;
            while (start < i && (isBuildIal && m[i][start] == 0 || !isBuildIal && m[start][i] == 0)) {
                start++;
            }
            res[i + 1] = res[i] + (i - start);
        }
        return res;
    }

    //build ial
    private void buildIal(double[][] m, int size) {
        ial = abstractAiBuild(m, size, true);
    }

    //build iau
    private void buildIau(double[][] m, int size) {
        iau = abstractAiBuild(m, size, false);
    }

    //get element (i, j)
    @Override
    public double get(int i, int j) {
        if (i == j) {
            return di[i];
        } else if (i > j) {
            int start = i - (ial[i + 1] - ial[i]);
            if (j < start) {
                return 0;
            } else {
                return al[ial[i] + j - start];
            }
        }
        int start = j - (iau[j + 1] - iau[j]);
        if (i < start) {
            return 0;
        } else {
            return au[iau[j] + i - start];
        }
    }

    //set element (i, j)
    @Override
    public void set(int i, int j, double val) {
        if (i == j) {
            di[i] = val;
        } else if (i > j) {
            int start = i - (ial[i + 1] - ial[i]);
            if (j < start) {
                throw new IllegalArgumentException("Out of profile");
            } else {
                al[ial[i] + j - start] = val;
            }
        } else {
            int start = j - (iau[j + 1] - iau[j]);
            if (i < start) {
                throw new IllegalArgumentException("Out of profile");
            } else {
                au[iau[j] + i - start] = val;
            }
        }
    }

    // return size of matrix
    @Override
    public int size() {
        return di.length;
    }

    // return two matrices L and U, which lays in this matrix memory.
    // After this method you CAN'T use this matrix
    public Matrix[] getLU() {
        for (int i = 1; i < size(); i++) {
            for (int j = firstInProfileL(i); j < i; j++) {
                double tmp = get(i, j);
                for (int k = 0; k < j; k++) {
                    tmp -= get(i, k) * get(k, j);
                }
                if (tmp != 0) {
                    tmp /= get(j, j);
                }
                set(i, j, tmp);
            }
            for (int j = firstInProfileU(i); j < i; j++) {
                double tmp = get(j, i);
                for (int k = 0; k < j; k++) {
                    tmp -= get(j, k) * get(k, i);
                }
                set(j, i, tmp);
            }
            double tmp = get(i, i);
            for (int k = 0; k < i; k++) {
                tmp -= get(i, k) * get(k, i);
            }
            set(i, i, tmp);
        }
        // building L and R as anonymous classes
        Matrix L = new Matrix() {
            @Override
            public double get(int i, int j) {
                if (i < j) return 0;
                if (i == j) return 1;
                return ProfileMatrix.this.get(i, j);
            }

            @Override
            public int size() {
                return ProfileMatrix.this.size();
            }

            @Override
            public void set(int i, int j, double val) {
                throw new IllegalArgumentException();
            }
        };

        Matrix U = new Matrix() {
            @Override
            public double get(int i, int j) {
                if (i > j) return 0;
                return ProfileMatrix.this.get(i, j);
            }

            @Override
            public int size() {
                return ProfileMatrix.this.size();
            }

            @Override
            public void set(int i, int j, double val) {
                throw new IllegalArgumentException();
            }
        };

        return new Matrix[]{L, U};
    }

    // print matrix
    private static void print(Matrix m) {
        for (int i = 0; i < m.size(); i++) {
            System.out.format("%d\t\t", i);
            for (int j = 0; j < m.size(); j++) {
                System.out.format("%.3f\t\t", m.get(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Solves a system of linear equations using
     * LU decomposition.
     * @param b the vector obtained by multiplying
     *         the matrix by the vector x.
     * @return resulting vector x.
     */
    public double[] solveByLU(double[] b) {
        if (b.length != size()) {
            throw new IllegalArgumentException("Wrong b argument size");
        }
        Matrix[] LU = getLU();
        Matrix L = LU[0];
        Matrix U = LU[1];

        for (int i = 0; i < size(); i++) {
            if (U.get(i, i) == 0) {
                return null;
            }
        }
        double[] y = new double[size()];

        for (int i = 0; i < size(); i++) {
            y[i] = b[i];
            for (int j = firstInProfileL(i); j < i; j++) {
                y[i] -= L.get(i, j) * y[j];
            }
        }

        double[] x = new double[size()];
        for (int i = size() - 1; i > -1; i--) {
            x[i] = y[i];
            for (int j = size() - 1; j > i; j--) {
                x[i] -= U.get(i, j) * x[j];
            }
            x[i] /= U.get(i, i);
        }
        return x;
    }

    // test main
    public static void main(String[] args) {
        for (int k = 1; k <= 15; k += 1) {
            Path second = Paths.get("src/lab3/matrices/secondTask/k" + k + "/n515.txt");
            Path third = Paths.get("src/lab3/matrices/thirdTask/n" + k + ".txt");
            try (Scanner sc = new Scanner(Files.newBufferedReader(second))) {
                int n = sc.nextInt();
                double[][] doubles = new double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        doubles[i][j] = sc.nextDouble();
                    }
                }

                double[] b = new double[n];
                for (int i = 0; i < n; i++) {
                    b[i] = sc.nextDouble();
                }

                Vector x = new BasicVector(IntStream
                        .range(1, n + 1)
                        .mapToDouble(i -> i).toArray());


                ProfileMatrix matrix = new ProfileMatrix(doubles);
                Instant before = Instant.now();
                Vector resGauss = new BasicVector(new GaussSolver().solve(matrix, b));
                Instant afterGauss = Instant.now();
                Vector resLU = new BasicVector(matrix.solveByLU(b));
                Instant afterLU = Instant.now();

                System.out.println(k + "\t\t" + ChronoUnit.MILLIS.between(before, afterGauss) + "\t\t" +
                        ChronoUnit.MILLIS.between(afterGauss, afterLU));
                for (int i = 0; i < n; i++) {
                    resGauss.set(i, Math.abs(resGauss.get(i) - (i + 1)));
                }
                for (int i = 0; i < n; i++) {
                    resLU.set(i, Math.abs(resLU.get(i) - (i + 1)));
                }
                System.out.println(resGauss.norm() / x.norm());
//                System.out.format(k + "\t\t%.2f\t\t", resGauss.norm() / x.norm());
                System.out.println(resLU.norm() / x.norm());
//                System.out.format("%.2f\n", resLU.norm() / x.norm());
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
