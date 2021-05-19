package lab3;

import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

public interface Matrix {

    double get(int i, int j);

    int size();

    void set(int i, int j, double val);

    default Vector mulOnVec(Vector vec) {
        double[] res = new double[size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                res[i] += get(i, j) * vec.get(j) / 2;
            }
        }
        return new BasicVector(res);
    }
}
