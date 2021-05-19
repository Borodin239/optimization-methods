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

    CRSMatrix(int n) {
        readFromFile(n);
    }

    private void readFromFile(int n) {
        String path = "src/lab3/matrices/fifthTask/n_" + n + "/";
        di = readDoubleArrayFromFile(n, path + "di.txt");
        ia = readIntArrayFromFile(n + 1, path + "ia.txt");
        ja = readIntArrayFromFile(ia[n], path + "ja.txt");
        au = readDoubleArrayFromFile(ia[n], path + "au.txt");
        al = readDoubleArrayFromFile(ia[n], path + "al.txt");
    }

    @Override
    public double get(int i, int j) {
        /*f (i == j) {
            return di[i];
        }
        int rowSize = ia[i + 1] - ia[i];
        if (i > j) {
            int start = i - rowSize;
            for (start) {

            }
            if (j < start) {
                return 0;
            }
            return al[ia[i] + j - start];
        }
        int start = j - (ia[j + 1] - ia[j]);
        if (i < start) {
            return 0;
        }*/
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void set(int i, int j, double val) {

    }
}
