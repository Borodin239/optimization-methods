package lab3;

public class GaussSolver {

    /**
     * Solves a system of linear equations using the Gauss method.
     * @param matrix our matrix.
     * @param b the vector obtained by multiplying
     *          the matrix by the vector x.
     * @return resulting vector.
     */
    public double[] solve(Matrix matrix, double[] b) {
        if (b.length != matrix.size()) {
            throw new IllegalArgumentException("Matrix size and b length don't match");
        }
        int size = matrix.size();

        double[][] arr = getMatrixArray(matrix, size);

        return solve(arr, b.clone(), size);
    }

    // find solvation x of equation arr * x = b
    public double[] solve(double[][] arr, double[] b, int size) {
        for (int k = 0; k < size; k++) {
            findLineWithMainElement(arr, size, k, b);
            if (Math.abs(arr[k][k]) == 0) {
                return null;
            }
            for (int i = k + 1; i < size; i++) {
                double scale = arr[i][k] / arr[k][k];
                for (int j = k; j < size; j++) {
                    arr[i][j] -= arr[k][j] * scale;
                }
                b[i] -= b[k] * scale;
            }
        }

        double[] x = new double[size];
        for (int i = size - 1; i > -1; i--) {
            x[i] = b[i];
            for (int j = i + 1; j < size; j++) {
                x[i] -= x[j] * arr[i][j];
            }
            x[i] /= arr[i][i];
        }
        return x;
    }

    // find line with max element on position k and replace this line with k-th line
    private void findLineWithMainElement(double[][] arr, int size, int k, double[] b) {
        int mainElemPos = k;
        for (int i = k; i < size; i++) {
            if (arr[i][k] > arr[mainElemPos][k]) {
                mainElemPos = i;
            }
        }
        {
            double tmp = b[k];
            b[k] = b[mainElemPos];
            b[mainElemPos] = tmp;
        }
        for (int i = 0; i < size; i++) {
            double tmp = arr[mainElemPos][i];
            arr[mainElemPos][i] = arr[k][i];
            arr[k][i] = tmp;
        }
    }

    // get array from matrix
    private double[][] getMatrixArray(Matrix matrix, int size) {
        double[][] arr = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = matrix.get(i, j);
            }
        }
        return arr;
    }
}
