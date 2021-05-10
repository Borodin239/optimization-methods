package lab3.matrix;

public class ProfileMatrix implements Matrix {
    private double[] di, al, au;
    private int[] ial, iau;

    public ProfileMatrix(double[][] m) {
        checkSize(m);
        int size = m.length;

        buildDi(m, size);
        buildIal(m, size);
        buildAl(m, size);
        buildIau(m, size);
        buildAu(m, size);
    }

    private void checkSize(double[][] m) {
        for (double[] arr : m) {
            if (m.length != arr.length) {
                throw new IllegalArgumentException("Not square matrix");
            }
        }
    }

    private void buildAl(double[][] m, int size) {
        al = new double[ial[size]];
        for (int i = 0; i < size; i++) {
            int start = ial[i] + i - ial[i + 1];
            for (int j = 0; j < ial[i + 1] - ial[i]; j++) {
                al[ial[i] + j] = m[i][start + j];
            }
        }
    }

    private void buildAu(double[][] m, int size) {
        au = new double[iau[size]];
        for (int i = 0; i < size; i++) {
            int start = iau[i] + i - iau[i + 1];
            for (int j = 0; j < iau[i + 1] - iau[i]; j++) {
                au[iau[i] + j] = m[start + j][i];
            }
        }
    }

    private void buildDi(double[][] m, int size) {
        di = new double[size];
        for (int i = 0; i < size; i++) {
            di[i] = m[i][i];
        }
    }

    private void buildIal(double[][] m, int size) {
        ial = new int[size + 1];
        ial[0] = 0;
        ial[1] = 0;
        for (int i = 1; i < size; i++) {
            int start = 0;
            while (start < i && m[i][start] == 0) {
                start++;
            }
            ial[i + 1] = ial[i] + (i - start);
        }
    }

    private void buildIau(double[][] m, int size) {
        iau = new int[size + 1];
        iau[0] = 0;
        iau[1] = 0;
        for (int i = 1; i < size; i++) {
            int start = 0;
            while (start < i && m[start][i] == 0) {
                start++;
            }
            iau[i + 1] = iau[i] + (i - start);
        }
    }

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
        } else {
            int start = j - (iau[j + 1] - iau[j]);
            if (i < start) {
                return 0;
            } else {
                return au[iau[j] + i - start];
            }
        }
    }

    @Override
    public int size() {
        return di.length;
    }
}
