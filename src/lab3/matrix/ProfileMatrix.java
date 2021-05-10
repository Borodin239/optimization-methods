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

    private boolean isInProfile(int i, int j) {
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

    private int firstInProfileL(int i) {
        return i - (ial[i + 1] - ial[i]);
    }

    private int firstInProfileU(int i) {
        return i - (iau[i + 1] - iau[i]);
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

    @Override
    public int size() {
        return di.length;
    }

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

        return new Matrix[] {L, U};
    }

    double[] solveByLU(double[] b) {
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
}
