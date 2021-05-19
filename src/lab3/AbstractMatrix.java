package lab3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public abstract class AbstractMatrix implements Matrix {
    /**
     * Matrix elements on the main diagonal.
     */
    double[] di;

    /**
     * Lower and upper triangles of the matrix.
     */
    double[] al, au;

    /**
     * Index of the first element of the k-th row in the array {@code al}
     * or {@code au}.
     */
    int[] ia;

    /**
     * Reads a double array of length n from the passed file.
     * @param size the length of the array that we want to count.
     * @param path file path.
     * @return double array with the result.
     */
    public double[] readDoubleArrayFromFile(int size, String path) {
        double[] res = new double[size];
        try (Scanner in = new Scanner(Files.newBufferedReader(Paths.get(path)))){
            for (int i = 0; i < size; i++) {
                String number = in.next();
                res[i] = Double.parseDouble(number);
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
        return res;
    }

    /**
     * Reads an integer array of length n from the passed file.
     * @param size the length of the array that we want to count.
     * @param path file path.
     * @return integer array with the result.
     */
    public int[] readIntArrayFromFile(int size, String path) {
        int[] res = new int[size];
        try (Scanner in = new Scanner(Files.newBufferedReader(Paths.get(path)))){
            for (int i = 0; i < size; i++) {
                res[i] = in.nextInt();
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
        return res;
    }
}
