package lab3;

/**
 * Compressed Row Storage Matrix realisation.
 */
public class CRSMatrix extends AbstractMatrix {
    /**
     * Номера столбцов (строк) элементов нижнего (верхнего) треугольника матрицы.
     */
    int[] ja;

    CRSMatrix(double[] di, double[] al, double[] au, int[] ja, int[] ia) {
        this.di = di;
        this.al = al;
        this.au = au;
        this.ja = ja;
        this.ia = ia;
    }

    CRSMatrix(int n, int num) {
        readFromFile(n, num);
    }

    private void readFromFile(int n, int num) {
        String path = "src/lab3/matrices/fifthTask" + num + "/n_" + n + "/";
        di = readDoubleArrayFromFile(n, path + "di.txt");
        ia = readIntArrayFromFile(n + 1, path + "ia.txt");
        ja = readIntArrayFromFile(ia[n], path + "ja.txt");
        au = readDoubleArrayFromFile(ia[n], path + "au.txt");
        al = readDoubleArrayFromFile(ia[n], path + "al.txt");
    }

    private int findIndex(int pos, int key) {
        int l = ia[pos] - 1;
        int r = ia[pos + 1];
        while (l < r - 1) {
            int m = (l + r) / 2;
            if (ja[m] < key) {
                l = m;
            } else {
                r = m;
            }
        }
        if (r == ia[pos + 1] || ja[r] != key) {
            return -1;
        }
        return r;
    }

    @Override
    public double get(int i, int j) {
        if (i == j) {
            return di[i];
        }
        int r = findIndex(Math.max(i, j), Math.min(i, j));
        if (r == -1) {
            return 0;
        }
        return al[r];
    }

    @Override
    public int size() {
        return di.length;
    }

    @Override
    public void set(int i, int j, double val) {
        if (i == j) {
            di[i] = val;
        }
        int r = findIndex(Math.max(i, j), Math.min(i, j));
        if (r != -1) {
            if (i > j) {
                al[r] = val;
            } else {
                au[r] = val;
            }
        }
    }
}
